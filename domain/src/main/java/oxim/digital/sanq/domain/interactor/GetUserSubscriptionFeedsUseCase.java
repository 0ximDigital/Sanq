package oxim.digital.sanq.domain.interactor;

import java.util.List;

import io.reactivex.Flowable;
import oxim.digital.sanq.domain.interactor.type.QueryUseCase;
import oxim.digital.sanq.domain.model.Feed;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class GetUserSubscriptionFeedsUseCase implements QueryUseCase<List<Feed>> {

    private final FeedRepository feedRepository;

    public GetUserSubscriptionFeedsUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Flowable<List<Feed>> execute() {
        return feedRepository.getUserFeeds();
    }
}
