package oxim.digital.sanq.domain.interactor.article.favourite;

import io.reactivex.Flowable;
import oxim.digital.sanq.domain.interactor.type.QueryUseCase;
import oxim.digital.sanq.domain.model.article.FavouriteArticles;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class GetFavouriteArticlesUseCase implements QueryUseCase<FavouriteArticles> {

    private final FeedRepository feedRepository;

    public GetFavouriteArticlesUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Flowable<FavouriteArticles> execute() {
        return feedRepository.getFavouriteArticles();
    }
}
