package oxim.digital.sanq.ui.feed.subscription;

import oxim.digital.sanq.base.BaseView;
import oxim.digital.sanq.base.ViewPresenter;

public interface UserSubscriptionsContract {

    interface View extends BaseView {

    }

    interface Presenter extends ViewPresenter<UserSubscriptionsViewModel> {

        void subscribeToTheNewFeed(String feedUrl);
    }
}
