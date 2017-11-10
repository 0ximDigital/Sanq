package oxim.digital.sanq.dagger.fragment;

import oxim.digital.sanq.ui.feed.create.NewFeedSubscriptionFragment;
import oxim.digital.sanq.ui.feed.create.NewFeedSubscriptionPresenter;
import oxim.digital.sanq.ui.feed.subscription.UserSubscriptionsFragment;
import oxim.digital.sanq.ui.feed.subscription.UserSubscriptionsPresenter;

public interface FragmentComponentInjects {

    void inject(UserSubscriptionsFragment fragment);
    void inject(UserSubscriptionsPresenter presenter);

    void inject(NewFeedSubscriptionFragment newFeedSubscriptionFragment);
    void inject(NewFeedSubscriptionPresenter newFeedSubscriptionPresenter);
}
