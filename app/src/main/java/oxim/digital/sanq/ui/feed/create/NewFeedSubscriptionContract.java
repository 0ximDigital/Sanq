package oxim.digital.sanq.ui.feed.create;

import io.reactivex.Flowable;
import oxim.digital.sanq.base.BaseView;
import oxim.digital.sanq.base.ViewPresenter;

public interface NewFeedSubscriptionContract {

    interface View extends BaseView {

        Flowable<NewFeedRequest> newFeedRequest();
    }

    interface Presenter extends ViewPresenter<NewFeedSubscriptionContract.View, NewFeedViewState> {

        void subscribeToFeed(String feedUrl);
    }
}
