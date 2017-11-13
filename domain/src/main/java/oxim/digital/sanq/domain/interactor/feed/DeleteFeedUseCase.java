package oxim.digital.sanq.domain.interactor.feed;

import io.reactivex.Completable;
import oxim.digital.sanq.domain.interactor.type.CommandUseCaseWithParams;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class DeleteFeedUseCase implements CommandUseCaseWithParams<Integer> {

    private final FeedRepository feedRepository;

    public DeleteFeedUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Integer feedId) {
        return feedRepository.deleteFeed(feedId);
    }
}
