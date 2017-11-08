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
import oxim.digital.sanq.dagger.application.module.ThreadingModule;
import oxim.digital.sanq.router.Router;

public abstract class BasePresenter<View extends BaseView, State extends ViewState> implements ScopedPresenter {

    @Inject
    @Named(ThreadingModule.MAIN_SCHEDULER)
    Scheduler observeScheduler;

    @Inject
    @Named(ThreadingModule.BACKGROUND_SCHEDULER)
    protected Scheduler backgroundScheduler;

    @Inject
    protected Router router;

    private final View view;
    private final State viewState;

    private CompositeDisposable disposables = new CompositeDisposable();

    public BasePresenter(final View view, final State viewState) {
        this.view = view;
        this.viewState = viewState;
    }

    @Override
    @CallSuper
    public void start() {
        // Template
    }

    @Override
    @CallSuper
    public void destroy() {
        disposables.clear();
    }

    @Override
    public void back() {
        router.goBack();
    }

    public void query(final Flowable<Consumer<State>> flowable) {
        buildQuery(flowable).assemble();
    }

    public void runCommand(final Completable completable) {
        buildCommand(completable).assemble();
    }

    public QueryBuilder buildQuery(final Flowable<Consumer<State>> flowable) {
        return new QueryBuilder(flowable);
    }

    public QueryBuilder buildCommand(final Completable completable) {
        return new QueryBuilder(completable);
    }

    private void addDisposable(final Disposable disposable) {
        disposables.add(disposable);
    }

    protected void runViewAction(final Consumer<State> viewStateAction) throws Exception {
        viewStateAction.accept(viewState);
    }

    public final void logError(final Throwable throwable) {
        if (!TextUtils.isEmpty(throwable.getMessage())) {
            // Error reporting, Crashlytics in example
            Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
        }
    }

    protected final class QueryBuilder {

        private Flowable<Consumer<State>> flowable;
        private Completable completable;

        private Consumer<Throwable> errorAction = BasePresenter.this::logError;
        private Consumer<State> completionAction;

        public QueryBuilder(final Flowable<Consumer<State>> flowable) {
            this.flowable = flowable;
        }

        public QueryBuilder(final Completable completable) {
            this.completable = completable;
        }

        public QueryBuilder flowable(final Flowable<Consumer<State>> flowable) {
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

        public QueryBuilder onCompleted(final Consumer<State> completionAction) {
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
                               completionAction != null ? () -> completionAction.accept(viewState) : Functions.EMPTY_ACTION);
        }

        private Disposable assembleCompletable() {
            return completable
                    .observeOn(observeScheduler)
                    .subscribe(completionAction != null ? () -> completionAction.accept(viewState) : Functions.EMPTY_ACTION,
                               errorAction);
        }
    }
}
