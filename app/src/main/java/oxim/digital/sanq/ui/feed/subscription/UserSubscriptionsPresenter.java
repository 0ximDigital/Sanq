package oxim.digital.sanq.ui.feed.subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.functions.Consumer;
import oxim.digital.sanq.base.BasePresenter;
import oxim.digital.sanq.domain.interactor.GetUserSubscriptionFeedsUseCase;
import oxim.digital.sanq.domain.interactor.SubscribeUserToFeedUseCase;
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
        subscribeTo(getUserSubscriptionFeedsUseCase.execute()
                                                   .subscribeOn(backgroundScheduler)
                                                   .map(feedViewModeMapper::mapFeedsToViewModels)
                                                   .map(this::toViewConsumer),
                    this::noOp,
                    this::logError);
    }

    private Consumer<UserSubscriptionsContract.View> toViewConsumer(final List<FeedViewModel> feedViewModels) {
        return view -> view.showFeedSubscriptions(feedViewModels);
    }

    @Override

    public void subscribeToTheNewFeed(final String feedUrl) {
        subscribeTo(Completable.timer(3, TimeUnit.SECONDS)
                               .andThen(subscribeUserToFeedUseCase.execute(feedUrl))
                               .subscribeOn(backgroundScheduler),
                    this::noOp,
                    this::logError);
    }
}
