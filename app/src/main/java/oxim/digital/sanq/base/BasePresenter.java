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
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import oxim.digital.sanq.dagger.application.module.ThreadingModule;
import oxim.digital.sanq.router.Router;

public abstract class BasePresenter<ViewState> implements ViewPresenter<ViewState> {

    @Inject
    @Named(ThreadingModule.MAIN_SCHEDULER)
    Scheduler observeScheduler;

    @Inject
    @Named(ThreadingModule.BACKGROUND_SCHEDULER)
    protected Scheduler backgroundScheduler;

    @Inject
    protected Router router;

    private final FlowableProcessor<ViewState> viewStateFlowable = BehaviorProcessor.<ViewState> create().toSerialized();

    private ViewState viewState;
    private CompositeDisposable disposables = new CompositeDisposable();

    public BasePresenter() {
        viewState = initialViewState();
        viewStateFlowable.onNext(viewState);
    }

    protected abstract ViewState initialViewState();

    @Override
    @CallSuper
    public void start() {
        // Template
    }

    @Override
    public Flowable<ViewState> viewState() {
        return viewStateFlowable.distinct().observeOn(observeScheduler);
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

    public void query(final Flowable<Consumer<ViewState>> flowable) {
        buildQuery(flowable).assemble();
    }

    public void runCommand(final Completable completable) {
        buildCommand(completable).assemble();
    }

    public QueryBuilder buildQuery(final Flowable<Consumer<ViewState>> flowable) {
        return new QueryBuilder(flowable);
    }

    public QueryBuilder buildCommand(final Completable completable) {
        return new QueryBuilder(completable);
    }

    protected void addDisposable(final Disposable disposable) {
        disposables.add(disposable);
    }

    protected void viewStateAction(final Consumer<ViewState> viewStateAction) {
        try {
            viewStateAction.accept(viewState);
            viewStateFlowable.onNext(viewState);
        } catch (final Exception e) {
            logError(e);
        }
    }

    public final void logError(final Throwable throwable) {
        if (!TextUtils.isEmpty(throwable.getMessage())) {
            // Error reporting, Crashlytics in example
            Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
        }
    }

    protected final class QueryBuilder {

        private Flowable<Consumer<ViewState>> flowable;
        private Completable completable;

        private Consumer<Throwable> errorAction = BasePresenter.this::logError;
        private Consumer<ViewState> completionAction = viewStateFlowable::onNext;

        public QueryBuilder(final Flowable<Consumer<ViewState>> flowable) {
            this.flowable = flowable;
        }

        public QueryBuilder(final Completable completable) {
            this.completable = completable;
        }

        public QueryBuilder flowable(final Flowable<Consumer<ViewState>> flowable) {
            this.flowable = flowable;
            return this;
        }

        public QueryBuilder completable(final Completable completable) {
            this.completable = completable;
            return this;
        }

        public QueryBuilder onError(final Consumer<Throwable> errorAction) {
            this.errorAction = new ChainedConsumer<>(this.errorAction, errorAction);
            return this;
        }

        public QueryBuilder onCompleted(final Consumer<ViewState> completionAction) {
            this.completionAction = new ChainedConsumer<>(this.completionAction, completionAction);
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
                    .subscribe(BasePresenter.this::viewStateAction,
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

    private static final class ChainedConsumer<T> implements Consumer<T> {

        private final Consumer<T> actual;
        private final Consumer<T> next;

        public ChainedConsumer(final Consumer<T> actual, final Consumer<T> next) {
            this.actual = actual;
            this.next = next;
        }

        @Override
        public void accept(final T t) throws Exception {
            actual.accept(t);
            next.accept(t);
        }
    }
}
