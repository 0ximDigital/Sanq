package oxim.digital.sanq.domain.interactor.article;

import io.reactivex.Flowable;
import oxim.digital.sanq.domain.interactor.type.QueryUseCaseWithRequest;
import oxim.digital.sanq.domain.model.article.FeedArticles;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class GetArticlesUseCase implements QueryUseCaseWithRequest<Integer, FeedArticles> {

    private final FeedRepository feedRepository;

    public GetArticlesUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Flowable<FeedArticles> execute(final Integer feedId) {
        return feedRepository.getArticles(feedId);
    }
}
