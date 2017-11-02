package oxim.digital.sanq.data.reactive;

import com.annimon.stream.Stream;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseObservableDataSource implements ObservableDataSource {

    private final List<DataSourceInvalidationObserver> dataSourceInvalidationObservers = new LinkedList<>();

    @Override
    public void addObserver(final DataSourceInvalidationObserver dataSourceObserver) {
        dataSourceInvalidationObservers.add(dataSourceObserver);
    }

    @Override
    public void removeObserver(final DataSourceInvalidationObserver dataSourceObserver) {
        dataSourceInvalidationObservers.remove(dataSourceObserver);
    }

    protected Completable notifySourceInvalidated() {
        return Completable.fromAction(() -> Stream.of(dataSourceInvalidationObservers)
                                                  .forEach(DataSourceInvalidationObserver::onDataSourceInvalidated))
                          .subscribeOn(Schedulers.io());
    }

    protected <T> Flowable<T> query(final Callable<T> callable) {
        return RxModel.createFlowable(this, callable);
    }
}
