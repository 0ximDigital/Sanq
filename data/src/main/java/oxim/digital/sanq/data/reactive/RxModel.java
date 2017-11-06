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

    public static <T> Flowable<T> createFlowable(final InvalidatingDataSource invalidatingDataSource, final Callable<T> dataCallable) {
        return createFlowable(invalidatingDataSource)
                .observeOn(Schedulers.io())
                .map(invalidationEvent -> Optional.ofNullable(dataCallable.call()))
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    private static Flowable<Object> createFlowable(final InvalidatingDataSource invalidatingDataSource) {
        return Flowable.create(flowableEmitter -> {
            if (!flowableEmitter.isCancelled()) {
                final DataSourceInvalidationObserver observer = () -> emitInvalidateEvent(flowableEmitter);

                invalidatingDataSource.addObserver(observer);
                flowableEmitter.setDisposable(Disposables.fromAction(() -> invalidatingDataSource.removeObserver(observer)));

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
