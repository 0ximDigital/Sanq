package oxim.digital.sanq.ui.feed.create;

import android.content.res.Resources;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import oxim.digital.sanq.base.BasePresenter;
import oxim.digital.sanq.domain.interactor.feed.SubscribeUserToFeedUseCase;

public final class NewFeedSubscriptionPresenter extends BasePresenter<NewFeedSubscriptionContract.View, NewFeedViewState> implements NewFeedSubscriptionContract.Presenter {

    @Inject
    SubscribeUserToFeedUseCase subscribeUserToFeedUseCase;

    @Inject
    Resources resources;

    @Override
    protected NewFeedViewState initialViewState() {
        return new NewFeedViewState();
    }

    @Override
    protected Disposable observeView(final NewFeedSubscriptionContract.View view) {
        return view.newFeedRequest()
                   .subscribe(this::onNewFeedRequestData);
    }

    private void onNewFeedRequestData(final NewFeedRequest newFeedRequest) {
        viewStateAction(newFeedViewState -> newFeedViewState.setMessage(""));
    }

    @Override
    public void subscribeToFeed(final String feedUrl) {
        runCommand(subscribeUserToFeedUseCase.execute(feedUrl)
                                             .startWith(Completable.fromAction(() -> viewStateAction(newFeedViewState -> newFeedViewState.setLoading(true))))
                                             .doOnEvent(event -> viewStateAction(newFeedViewState -> newFeedViewState.setLoading(false)))
                                             .doOnError(this::showErrorMessage)
                                             .doOnComplete(this::back));
    }

    private void showErrorMessage(final Throwable throwable) {
        viewStateAction(newFeedViewState -> newFeedViewState.setMessage("Invalid feed"));
    }
}

