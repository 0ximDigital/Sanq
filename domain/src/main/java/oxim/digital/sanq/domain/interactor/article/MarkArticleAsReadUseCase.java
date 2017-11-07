package oxim.digital.sanq.domain.interactor.article;

import io.reactivex.Completable;
import oxim.digital.sanq.domain.interactor.type.CommandUseCaseWithParams;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class MarkArticleAsReadUseCase implements CommandUseCaseWithParams<Integer> {

    private final FeedRepository feedRepository;

    public MarkArticleAsReadUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Integer articleId) {
        return feedRepository.markArticleAsRead(articleId);
    }
}
