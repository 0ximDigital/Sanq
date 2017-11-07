package oxim.digital.sanq.domain.interactor.feed;

import io.reactivex.Flowable;
import oxim.digital.sanq.domain.interactor.type.QueryUseCase;
import oxim.digital.sanq.domain.model.feed.UserFeeds;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class GetUserSubscriptionFeedsUseCase implements QueryUseCase<UserFeeds> {

    private final FeedRepository feedRepository;

    public GetUserSubscriptionFeedsUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Flowable<UserFeeds> execute() {
        return feedRepository.getUserFeeds();
    }
}
