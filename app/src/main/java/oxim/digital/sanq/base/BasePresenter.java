package oxim.digital.sanq.base;

import android.support.annotation.CallSuper;
import android.text.TextUtils;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import oxim.digital.sanq.configuration.ViewActionQueue;
import oxim.digital.sanq.configuration.ViewActionQueueImpl;
import oxim.digital.sanq.dagger.application.module.ThreadingModule;
import oxim.digital.sanq.router.Router;

public abstract class BasePresenter<View extends BaseView> implements ScopedPresenter {

    @Inject
    @Named(ThreadingModule.MAIN_SCHEDULER)
    Scheduler observeScheduler;

    @Inject
    @Named(ThreadingModule.BACKGROUND_SCHEDULER)
    protected Scheduler backgroundScheduler;

    @Inject
    protected Router router;

    private final View view;
    private ViewActionQueue<View> viewActionQueue;

    private CompositeDisposable disposables = new CompositeDisposable();

    public BasePresenter(final View view) {
        this.view = view;
    }

    @Override
    @CallSuper
    public void start() {
        this.viewActionQueue = new ViewActionQueueImpl<>(); // TODO - inject or inject factory

        addDisposable(viewActionQueue.viewActions()
                                     .observeOn(observeScheduler)
                                     .subscribe(this::onViewAction, this::logError));
    }

    private void onViewAction(final Consumer<View> viewAction) throws Exception {
        viewAction.accept(view);
    }

    @Override
    public final void activate() {
        viewActionQueue.resume();
    }

    @Override
    @CallSuper
    public final void deactivate() {
        viewActionQueue.pause();
    }

    @Override
    @CallSuper
    public void destroy() {
        disposables.clear();
        viewActionQueue.destroy();
    }

    @Override
    public void back() {
        router.goBack();
    }

    public void query(final Flowable<Consumer<View>> flowable) {
        buildQuery(flowable).assemble();
    }

    public void runCommand(final Completable completable) {
        buildCommand(completable).assemble();
    }

    public QueryBuilder buildQuery(final Flowable<Consumer<View>> flowable) {
        return new QueryBuilder(flowable);
    }

    public QueryBuilder buildCommand(final Completable completable) {
        return new QueryBuilder(completable);
    }

    private void addDisposable(final Disposable disposable) {
        disposables.add(disposable);
    }

    protected void runViewAction(final Consumer<View> viewAction) {
        viewActionQueue.enqueueViewAction(viewAction);
    }

    public final void logError(final Throwable throwable) {
        if (!TextUtils.isEmpty(throwable.getMessage())) {
            // Error reporting, Crashlytics in example
            Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
        }
    }

    protected final class QueryBuilder {

        private Flowable<Consumer<View>> flowable;
        private Completable completable;

        private Consumer<Throwable> errorAction = BasePresenter.this::logError;
        private Consumer<View> completionAction;

        public QueryBuilder(final Flowable<Consumer<View>> flowable) {
            this.flowable = flowable;
        }

        public QueryBuilder(final Completable completable) {
            this.completable = completable;
        }

        public QueryBuilder flowable(final Flowable<Consumer<View>> flowable) {
            this.flowable = flowable;
            return this;
        }

        public QueryBuilder completable(final Completable completable) {
            this.completable = completable;
            return this;
        }

        public QueryBuilder onError(final Consumer<Throwable> errorAction) {
            this.errorAction = errorAction;
            return this;
        }

        public QueryBuilder onCompleted(final Consumer<View> completionAction) {
            this.completionAction = completionAction;
            return this;
        }

        public void assemble() {
            disposables.add(subscribe());
        }

        public Disposable subscribe() {
            return flowable != null ? assembleFlowable() : assembleCompletable();
        }

        private Disposable assembleFlowable() {
            return flowable
                    .observeOn(observeScheduler)
                    .subscribe(BasePresenter.this::runViewAction,
                               errorAction,
                               completionAction != null ? () -> completionAction.accept(view) : Functions.EMPTY_ACTION);
        }

        private Disposable assembleCompletable() {
            return completable
                    .observeOn(observeScheduler)
                    .subscribe(completionAction != null ? () -> completionAction.accept(view) : Functions.EMPTY_ACTION,
                               errorAction);
        }
    }
}
