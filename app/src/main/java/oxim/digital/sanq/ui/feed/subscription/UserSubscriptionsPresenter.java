package oxim.digital.sanq.ui.feed.subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import oxim.digital.sanq.base.BasePresenter;
import oxim.digital.sanq.domain.interactor.feed.GetUserSubscriptionFeedsUseCase;
import oxim.digital.sanq.domain.interactor.feed.SubscribeUserToFeedUseCase;
import oxim.digital.sanq.ui.model.FeedViewModel;
import oxim.digital.sanq.ui.model.mapper.FeedViewModeMapper;

public final class UserSubscriptionsPresenter extends BasePresenter<UserSubscriptionsContract.View> implements UserSubscriptionsContract.Presenter {

    @Inject
    GetUserSubscriptionFeedsUseCase getUserSubscriptionFeedsUseCase;

    @Inject
    SubscribeUserToFeedUseCase subscribeUserToFeedUseCase;

    @Inject
    FeedViewModeMapper feedViewModeMapper;

    public UserSubscriptionsPresenter(final UserSubscriptionsContract.View view) {
        super(view);
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

    private Consumer<UserSubscriptionsContract.View> toViewConsumer(final List<FeedViewModel> feedViewModels) {
        return view -> view.showFeedSubscriptions(feedViewModels);
    }

    @Override
    public void subscribeToTheNewFeed(final String feedUrl) {
        buildCommand(subscribeUserToFeedUseCase.execute(feedUrl)
                                               .subscribeOn(backgroundScheduler))
                .onError(this::onNewFeedError)
                .assemble();
    }

    private void onNewFeedError(final Throwable throwable) {
        logError(throwable);
        runViewAction(view -> view.showMessage("Invalid feed"));
    }
}
