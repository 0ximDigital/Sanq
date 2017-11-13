package oxim.digital.sanq.domain.interactor.feed.update;

import io.reactivex.Completable;
import oxim.digital.sanq.domain.interactor.type.CommandUseCaseWithParams;
import oxim.digital.sanq.domain.model.feed.Feed;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class UpdateFeedArticlesUseCase implements CommandUseCaseWithParams<Feed> {

    private final FeedRepository feedRepository;

    public UpdateFeedArticlesUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Feed feed) {
        return feedRepository.refreshFeedArticles(feed);
    }
}
