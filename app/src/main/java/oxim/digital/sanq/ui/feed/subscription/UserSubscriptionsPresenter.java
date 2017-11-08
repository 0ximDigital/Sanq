package oxim.digital.sanq.ui.feed.subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.functions.Consumer;
import oxim.digital.sanq.base.BasePresenter;
import oxim.digital.sanq.domain.interactor.feed.GetUserSubscriptionFeedsUseCase;
import oxim.digital.sanq.domain.interactor.feed.SubscribeUserToFeedUseCase;
import oxim.digital.sanq.ui.model.FeedViewModel;
import oxim.digital.sanq.ui.model.mapper.FeedViewModeMapper;

public final class UserSubscriptionsPresenter extends BasePresenter<UserSubscriptionsContract.View, UserSubscriptionsViewModel> implements UserSubscriptionsContract.Presenter {

    @Inject
    GetUserSubscriptionFeedsUseCase getUserSubscriptionFeedsUseCase;

    @Inject
    SubscribeUserToFeedUseCase subscribeUserToFeedUseCase;

    @Inject
    FeedViewModeMapper feedViewModeMapper;

    public UserSubscriptionsPresenter(final UserSubscriptionsContract.View view, final UserSubscriptionsViewModel viewState) {
        super(view, viewState);
    }

    @Override
    public void start() {
        super.start();
        observeUserFeedSubscriptions();
    }

    private void observeUserFeedSubscriptions() {
        query(getUserSubscriptionFeedsUseCase.execute()
                                             .subscribeOn(backgroundScheduler)
                                             .map(userFeeds -> userFeeds.userFeeds)
                                             .map(feedViewModeMapper::mapFeedsToViewModels)
                                             .map(this::toViewConsumer));
    }

    private Consumer<UserSubscriptionsViewModel> toViewConsumer(final List<FeedViewModel> feedViewModels) {
        return viseState -> viseState.setFeedViewModels(feedViewModels);
    }

    @Override
    public void subscribeToTheNewFeed(final String feedUrl) {
        buildCommand(Completable.timer(4, TimeUnit.SECONDS)
                                .andThen(subscribeUserToFeedUseCase.execute(feedUrl))
                                .startWith(Completable.fromAction(() -> runViewAction(viewState -> viewState.setIsLoading(true))))
                                .doOnTerminate(() -> runViewAction(viewState -> viewState.setIsLoading(false)))
                                .subscribeOn(backgroundScheduler))
                .onError(this::onNewFeedError)
                .subscribe();
    }

    private void onNewFeedError(final Throwable throwable) {
        logError(throwable);
        buildCommand(Completable.fromAction(() -> runViewAction(viewState -> viewState.setMessage("Invalid feed"))));
    }
}
