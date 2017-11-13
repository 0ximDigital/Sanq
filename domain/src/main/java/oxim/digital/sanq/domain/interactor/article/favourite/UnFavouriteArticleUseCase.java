package oxim.digital.sanq.domain.interactor.article.favourite;

import io.reactivex.Completable;
import oxim.digital.sanq.domain.interactor.type.CommandUseCaseWithParams;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class UnFavouriteArticleUseCase implements CommandUseCaseWithParams<Integer> {

    private final FeedRepository feedRepository;

    public UnFavouriteArticleUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Integer articleId) {
        return feedRepository.unFavouriteArticle(articleId);
    }
}
