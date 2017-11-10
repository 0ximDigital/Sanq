package oxim.digital.sanq.domain.util;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.FlowableOperator;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public final class OperatorVariableThrottleFirst<T> implements FlowableOperator<T, T>, ThrottleController {

    private final Scheduler scheduler;

    private long timeInMilliseconds;
    private long newTimeInMilliseconds = 0L;

    public OperatorVariableThrottleFirst(final long windowDuration, final TimeUnit unit) {
        this(windowDuration, unit, Schedulers.computation());
    }

    public OperatorVariableThrottleFirst(final long windowDuration, final TimeUnit unit, final Scheduler scheduler) {
        this.scheduler = scheduler;
        setThrottleWindow(windowDuration, unit);
    }

    @Override
    public Subscriber<? super T> apply(final Subscriber<? super T> observer) throws Exception {
        return new Subscriber<T>() {

            private long lastOnNext = 0;

            @Override
            public void onSubscribe(final Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(final T t) {
                final long now = scheduler.now(TimeUnit.MILLISECONDS);
                if (lastOnNext == 0 || now - lastOnNext >= timeInMilliseconds) {
                    lastOnNext = now;
                    if (newTimeInMilliseconds != 0L) {
                        timeInMilliseconds = newTimeInMilliseconds;
                    }
                    observer.onNext(t);
                }
            }

            @Override
            public void onError(final Throwable t) {
                observer.onError(t);
            }

            @Override
            public void onComplete() {
                observer.onComplete();
            }
        };
    }

    @Override
    public void setThrottleWindow(final long windowDuration, final TimeUnit unit) {
        this.timeInMilliseconds = unit.toMillis(windowDuration);
    }
}
