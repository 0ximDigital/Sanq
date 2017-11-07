package oxim.digital.sanq.data.reactive;

import android.annotation.SuppressLint;
import android.util.Log;

import com.annimon.stream.Stream;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public abstract class ObservableDataSource<DataSource> implements InvalidatingDataSource {

    private static final int LAST_ITEM = 1;

    @SuppressLint("UseSparseArrays")
    private final Map<Class, Flowable> flowableMap = new HashMap<>();
    private final List<DataSourceInvalidationObserver> dataSourceInvalidationObservers = new LinkedList<>();

    private final Scheduler backgroundScheduler;

    private final DataSource dataSource;

    public ObservableDataSource(final DataSource dataSource) {
        this(dataSource, Schedulers.io());
    }

    public ObservableDataSource(final DataSource dataSource, final Scheduler backgroundScheduler) {
        this.dataSource = dataSource;
        this.backgroundScheduler = backgroundScheduler;
    }

    @Override
    public void addObserver(final DataSourceInvalidationObserver dataSourceObserver) {
        dataSourceInvalidationObservers.add(dataSourceObserver);
        Log.w("WAT", "Observer count -> " + dataSourceInvalidationObservers.size());
    }

    @Override
    public void removeObserver(final DataSourceInvalidationObserver dataSourceObserver) {
        dataSourceInvalidationObservers.remove(dataSourceObserver);
        Log.w("WAT", "Observer count -> " + dataSourceInvalidationObservers.size());
    }

    protected Completable command(final Consumer<DataSource> commandAction) {
        return Completable.fromAction(() -> commandAction.accept(dataSource))
                          .andThen(notifyDataSourceInvalidated());
    }

    private Completable notifyDataSourceInvalidated() {
        return Completable.fromAction(() -> Stream.of(dataSourceInvalidationObservers)
                                                  .forEach(DataSourceInvalidationObserver::onDataSourceInvalidated))
                          .subscribeOn(backgroundScheduler);
    }

    protected <T> Flowable<T> query(final Function<DataSource, Callable<T>> dataQueryProvider) {
        return Flowable.defer(() -> RxModel.createFlowable(this, dataQueryProvider.apply(dataSource)));
    }

    protected <T> Flowable<T> query(final Function<DataSource, Callable<T>> dataQueryProvider, final Class<T> clazz) {
        final Flowable<T> cachedDataFlowable = flowableMap.get(clazz);
        if (cachedDataFlowable != null) {
            return cachedDataFlowable;
        }

        final Flowable<T> dataFlowable = this.query(dataQueryProvider)
                                             .doOnCancel(() -> flowableMap.remove(clazz))
                                             .distinctUntilChanged()
                                             .replay(LAST_ITEM)
                                             .refCount()
                                             .subscribeOn(backgroundScheduler);

        flowableMap.put(clazz, dataFlowable);

        return dataFlowable;
    }
}
