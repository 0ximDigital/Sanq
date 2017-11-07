package oxim.digital.sanq.domain.interactor.feed;

import io.reactivex.Flowable;
import oxim.digital.sanq.domain.interactor.type.QueryUseCaseWithRequest;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class IsUserSubscribedToFeedUseCase implements QueryUseCaseWithRequest<String, Boolean> {

    private final FeedRepository feedRepository;

    public IsUserSubscribedToFeedUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Flowable<Boolean> execute(final String feedUrl) {
        return feedRepository.feedExists(feedUrl);
    }
}
