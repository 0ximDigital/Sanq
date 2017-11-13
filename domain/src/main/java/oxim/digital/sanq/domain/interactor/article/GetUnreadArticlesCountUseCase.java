package oxim.digital.sanq.domain.interactor.article;

import io.reactivex.Flowable;
import oxim.digital.sanq.domain.interactor.type.QueryUseCase;
import oxim.digital.sanq.domain.model.article.UnreadArticles;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class GetUnreadArticlesCountUseCase implements QueryUseCase<UnreadArticles> {

    private final FeedRepository feedRepository;

    public GetUnreadArticlesCountUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Flowable<UnreadArticles> execute() {
        return feedRepository.getUnreadArticlesCount();
    }
}
