package oxim.digital.sanq.data.reactive;

import android.annotation.SuppressLint;
import android.util.Log;

import com.annimon.stream.Stream;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseObservableDataSource implements ObservableDataSource {

    private static final int LAST_ITEM = 1;

    private final Scheduler backgroundScheduler;

    @SuppressLint("UseSparseArrays")
    private final Map<Integer, Flowable> flowableMap = new HashMap<>();

    private final List<DataSourceInvalidationObserver> dataSourceInvalidationObservers = new LinkedList<>();

    public BaseObservableDataSource() {
        this(Schedulers.io());
    }

    public BaseObservableDataSource(final Scheduler backgroundScheduler) {
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

    protected Completable command(final Action commandAction) {
        return Completable.fromAction(commandAction)
                          .andThen(notifySourceInvalidated());
    }

    private Completable notifySourceInvalidated() {
        return Completable.fromAction(() -> Stream.of(dataSourceInvalidationObservers)
                                                  .forEach(DataSourceInvalidationObserver::onDataSourceInvalidated))
                          .subscribeOn(backgroundScheduler);
    }

    protected <T> Flowable<T> query(final Callable<T> callable) {
        return Flowable.defer(() -> RxModel.createFlowable(this, callable));
    }

    protected <T> Flowable<T> query(final Callable<T> callable, final int queryId) {
        final Flowable<T> cachedDataFlowable = flowableMap.get(queryId);
        if (cachedDataFlowable != null) {
            return cachedDataFlowable;
        }

        final Flowable<T> dataFlowable = this.query(callable)
                                             .doOnCancel(() -> flowableMap.remove(queryId))
                                             .distinctUntilChanged()
                                             .replay(LAST_ITEM)
                                             .refCount()
                                             .subscribeOn(backgroundScheduler);
        flowableMap.put(queryId, dataFlowable);

        return dataFlowable;
    }
}
