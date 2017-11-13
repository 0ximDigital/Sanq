package oxim.digital.sanq.ui.feed.subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.functions.Consumer;
import oxim.digital.sanq.base.BasePresenter;
import oxim.digital.sanq.domain.interactor.feed.GetUserSubscriptionFeedsUseCase;
import oxim.digital.sanq.domain.interactor.feed.SubscribeUserToFeedUseCase;
import oxim.digital.sanq.ui.model.FeedViewModel;
import oxim.digital.sanq.ui.model.mapper.FeedViewModeMapper;

public final class UserSubscriptionsPresenter extends BasePresenter<UserSubscriptionsContract.View, UserSubscriptionsViewState> implements UserSubscriptionsContract.Presenter {

    @Inject
    GetUserSubscriptionFeedsUseCase getUserSubscriptionFeedsUseCase;

    @Inject
    SubscribeUserToFeedUseCase subscribeUserToFeedUseCase;

    @Inject
    FeedViewModeMapper feedViewModeMapper;

    @Override
    protected UserSubscriptionsViewState initialViewState() {
        return new UserSubscriptionsViewState();
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

    private Consumer<UserSubscriptionsViewState> toViewConsumer(final List<FeedViewModel> feedViewModels) {
        return viseState -> viseState.setFeedViewModels(feedViewModels);
    }

    @Override
    public void subscribeToTheNewFeed(final String feedUrl) {
        runCommand(subscribeUserToFeedUseCase.execute(feedUrl)
                                             .startWith(Completable.fromAction(() -> viewStateAction(viewState -> viewState.setLoading(true))))
                                             .doOnEvent(ignore -> viewStateAction(viewState -> viewState.setLoading(false))));
    }

    @Override
    public void showNewFeedSubscriptionScreen() {
        router.showAddNewFeedScreen();
    }
}
