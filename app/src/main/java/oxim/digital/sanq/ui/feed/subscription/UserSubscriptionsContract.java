package oxim.digital.sanq.ui.feed.subscription;

import java.util.List;

import oxim.digital.sanq.base.BaseView;
import oxim.digital.sanq.base.ScopedPresenter;
import oxim.digital.sanq.ui.model.FeedViewModel;

public interface UserSubscriptionsContract {

    interface View extends BaseView {

        void showFeedSubscriptions(List<FeedViewModel> feedSubscriptions);

        void showMessage(String message);
    }

    interface Presenter extends ScopedPresenter {

        void subscribeToTheNewFeed(String feedUrl);
    }
}
