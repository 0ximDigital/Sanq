package oxim.digital.sanq.domain.interactor.feed;

import io.reactivex.Completable;
import oxim.digital.sanq.domain.interactor.type.CommandUseCaseWithParams;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class SubscribeUserToFeedUseCase implements CommandUseCaseWithParams<String> {

    private final FeedRepository feedRepository;

    public SubscribeUserToFeedUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final String feedUrl) {
        return feedRepository.createNewFeed(feedUrl);
    }
}
