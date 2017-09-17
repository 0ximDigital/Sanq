package oxim.digital.sanq.base;

import android.support.annotation.CallSuper;
import android.text.TextUtils;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import oxim.digital.sanq.configuration.ViewConsumerQueue;
import oxim.digital.sanq.configuration.ViewConsumerQueueFactory;
import oxim.digital.sanq.dagger.application.module.ThreadingModule;
import oxim.digital.sanq.router.Router;

public abstract class BasePresenter<View extends BaseView> implements ScopedPresenter {

    @Inject
    protected Router router;

    @Inject
    protected ViewConsumerQueueFactory viewConsumerQueueFactory;

    @Inject
    @Named(ThreadingModule.MAIN_SCHEDULER)
    Scheduler mainThreadScheduler;

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
                                       .observeOn(mainThreadScheduler)
                                       .subscribe(this::onViewConsumer, this::logError));
    }

    private void onViewConsumer(final Consumer<View> viewConsumer) throws Exception {
        viewConsumer.accept(view);
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

    protected void addDisposable(final Disposable disposable) {
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
}
