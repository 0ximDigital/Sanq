package oxim.digital.sanq.domain.util;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.processors.PublishProcessor;

public final class ActionRouterImpl implements ActionRouter {

    private static final long DEFAULT_WINDOW_DURATION_MILLIS = 500L;

    private PublishProcessor<Runnable> router = PublishProcessor.create();
    private Disposable actionsDisposable = Disposables.disposed();

    private boolean actionsBlocked;

    private long primaryWindowDuration = DEFAULT_WINDOW_DURATION_MILLIS;
    private TimeUnit primaryTimeUnit = TimeUnit.MILLISECONDS;

    private ThrottleController throttleController;

    public ActionRouterImpl() {
        initRouter();
        listenRouter();
    }

    private void initRouter() {
        router = PublishProcessor.create();
    }

    private void listenRouter() {
        final OperatorVariableThrottleFirst<Runnable> operatorVariableThrottleFirst = new OperatorVariableThrottleFirst<>(primaryWindowDuration, primaryTimeUnit);
        actionsDisposable = router.lift(operatorVariableThrottleFirst)
                                  .subscribe(Runnable::run);

        throttleController = operatorVariableThrottleFirst;
    }

    @Override
    public void setTiming(final long windowDuration, final TimeUnit timeUnit) {
        unsubscribe();
        primaryWindowDuration = windowDuration;
        primaryTimeUnit = timeUnit;
        listenRouter();
    }

    @Override
    public void throttle(final Runnable action) {
        throttle(primaryWindowDuration, primaryTimeUnit, action);
    }

    @Override
    public void throttle(final long windowDuration, final Runnable action) {
        throttle(windowDuration, primaryTimeUnit, action);
    }

    @Override
    public void throttle(final long windowDuration, final TimeUnit timeUnit, final Runnable action) {
        if (!canThrottle()) {
            return;
        }

        try {
            throttleController.setThrottleWindow(windowDuration, timeUnit);
            router.onNext(action);
        } catch (final Throwable throwable) {
            System.err.println(throwable.getMessage());
            unsubscribe();
            initRouter();
            listenRouter();
            throw throwable;
        }
    }

    private boolean canThrottle() {
        return router != null && throttleController != null && !actionsBlocked;
    }

    @Override
    public void blockActions() {
        System.out.println("Blocking actions");
        actionsBlocked = true;
    }

    @Override
    public void unblockActions() {
        System.out.println("Unblocking actions");
        actionsBlocked = false;
    }

    private void unsubscribe() {
        actionsDisposable.dispose();
    }
}
