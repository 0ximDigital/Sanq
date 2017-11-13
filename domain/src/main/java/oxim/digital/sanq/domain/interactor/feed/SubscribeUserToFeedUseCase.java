package oxim.digital.sanq.domain.interactor.feed;

import java.util.concurrent.TimeUnit;

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
        return Completable.timer(4, TimeUnit.SECONDS).andThen(feedRepository.createNewFeed(feedUrl));
    }
}
