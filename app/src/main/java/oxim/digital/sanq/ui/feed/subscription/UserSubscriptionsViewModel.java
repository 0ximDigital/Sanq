package oxim.digital.sanq.ui.feed.subscription;

import java.util.Collections;
import java.util.List;

import oxim.digital.sanq.ui.model.FeedViewModel;

public final class UserSubscriptionsViewModel {

    private List<FeedViewModel> feedViewModels = Collections.emptyList();
    private boolean isLoading;

    public List<FeedViewModel> getFeedViewModels() {
        return feedViewModels;
    }

    public void setFeedViewModels(final List<FeedViewModel> feedViewModels) {
        this.feedViewModels = feedViewModels;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(final boolean loading) {
        isLoading = loading;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UserSubscriptionsViewModel that = (UserSubscriptionsViewModel) o;

        if (isLoading != that.isLoading) {
            return false;
        }
        return feedViewModels != null ? feedViewModels.equals(that.feedViewModels) : that.feedViewModels == null;
    }

    @Override
    public int hashCode() {
        int result = feedViewModels != null ? feedViewModels.hashCode() : 0;
        result = 31 * result + (isLoading ? 1 : 0);
        return result;
    }
}
