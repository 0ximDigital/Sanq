package oxim.digital.sanq.data.reactive;

import com.annimon.stream.Optional;

import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public final class RxModel {

    private static final Object INVALIDATE_EVENT = new Object();

    public static <T> Flowable<T> createFlowable(final ObservableDataSource observableDataSource, final Callable<T> dataCallable) {
        return createFlowable(observableDataSource)
                .observeOn(Schedulers.io())
                .map(invalidationEvent -> Optional.ofNullable(dataCallable.call()))
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    private static Flowable<Object> createFlowable(final ObservableDataSource observableDataSource) {
        return Flowable.create(flowableEmitter -> {
            if (!flowableEmitter.isCancelled()) {
                final DataSourceInvalidationObserver observer = () -> emitInvalidateEvent(flowableEmitter);

                observableDataSource.addObserver(observer);
                flowableEmitter.setDisposable(Disposables.fromAction(() -> observableDataSource.removeObserver(observer)));

                emitInvalidateEvent(flowableEmitter);
            }
        }, BackpressureStrategy.LATEST);
    }

    private static void emitInvalidateEvent(final FlowableEmitter<Object> flowableEmitter) {
        if (!flowableEmitter.isCancelled()) {
            flowableEmitter.onNext(INVALIDATE_EVENT);
        }
    }
}
