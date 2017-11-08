package oxim.digital.sanq.ui.feed.subscription;

import android.arch.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import oxim.digital.sanq.base.ViewState;
import oxim.digital.sanq.ui.model.FeedViewModel;

public final class UserSubscriptionsViewModel extends ViewModel implements ViewState {

    private FlowableProcessor<List<FeedViewModel>> feedViewModelsProcessor = BehaviorProcessor.<List<FeedViewModel>> createDefault(Collections.emptyList()).toSerialized();
    private FlowableProcessor<Boolean> isLoadingProcessor = BehaviorProcessor.createDefault(false).toSerialized();

    public void setFeedViewModels(final List<FeedViewModel> viewModels) {
        feedViewModelsProcessor.onNext(viewModels);
    }

    public Flowable<List<FeedViewModel>> feedViewModels() {
        return feedViewModelsProcessor.observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<Boolean> isLoading() {
        return isLoadingProcessor.observeOn(AndroidSchedulers.mainThread());
    }

    public void setIsLoading(final boolean isLoading) {
        isLoadingProcessor.onNext(isLoading);
    }

    public void setMessage(final String message) {

    }
}
