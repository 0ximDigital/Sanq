package oxim.digital.sanq.base;

import android.support.annotation.CallSuper;
import android.text.TextUtils;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import oxim.digital.sanq.configuration.ViewConsumerQueue;
import oxim.digital.sanq.configuration.ViewConsumerQueueFactory;
import oxim.digital.sanq.dagger.application.module.ThreadingModule;
import oxim.digital.sanq.router.Router;

public abstract class BasePresenter<View extends BaseView> implements ScopedPresenter {

    @Inject
    ViewConsumerQueueFactory viewConsumerQueueFactory;

    @Inject
    @Named(ThreadingModule.MAIN_SCHEDULER)
    Scheduler observeScheduler;

    @Inject
    @Named(ThreadingModule.BACKGROUND_SCHEDULER)
    protected Scheduler backgroundScheduler;

    @Inject
    protected Router router;

    private final View view;
    private ViewConsumerQueue<View> viewConsumerQueue;

    private CompositeDisposable disposables = new CompositeDisposable();

    public BasePresenter(final View view) {
        this.view = view;
    }

    @Override
    @CallSuper
    public void start() {
        this.viewConsumerQueue = viewConsumerQueueFactory.getViewConsumerQueue();

        addDisposable(viewConsumerQueue.viewConsumersFlowable()
                                       .observeOn(observeScheduler)
                                       .subscribe(this::onViewAction, this::logError));
    }

    private void onViewAction(final Consumer<View> viewAction) throws Exception {
        viewAction.accept(view);
    }

    @Override
    public final void activate() {
        viewConsumerQueue.resume();
    }

    @Override
    @CallSuper
    public final void deactivate() {
        viewConsumerQueue.pause();
    }

    @Override
    @CallSuper
    public void destroy() {
        disposables.clear();
        viewConsumerQueue.destroy();
    }

    @Override
    public void back() {
        router.goBack();
    }

    // TODO - data source builder? Something line subscribeTo(source).onError(thr -> ).onCompletion(() -> ).onValue(val -> )

    public void subscribeTo(final Flowable<Consumer<View>> flowable, final Consumer<View> completionConsumer, final Consumer<Throwable> errorConsumer) {
        addDisposable(flowable.observeOn(observeScheduler).subscribe(this::consumeView, errorConsumer, () -> consumeView(completionConsumer)));
    }

    public void subscribeTo(final Single<Consumer<View>> single, final Consumer<Throwable> errorConsumer) {
        addDisposable(single.observeOn(observeScheduler).subscribe(this::consumeView, errorConsumer));
    }

    public void subscribeTo(final Completable completable, final Consumer<View> completionConsumer, final Consumer<Throwable> errorConsumer) {
        addDisposable(completable.observeOn(observeScheduler).subscribe(() -> consumeView(completionConsumer), errorConsumer));
    }

    private void addDisposable(final Disposable disposable) {
        disposables.add(disposable);
    }

    protected void consumeView(final Consumer<View> viewConsumer) {
        viewConsumerQueue.enqueueViewConsumer(viewConsumer);
    }

    public final void logError(final Throwable throwable) {
        if (!TextUtils.isEmpty(throwable.getMessage())) {
            // Error reporting, Crashlytics in example
            Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
        }
    }

    public final void noOp(final Object object) {
        // No-op
    }
}
