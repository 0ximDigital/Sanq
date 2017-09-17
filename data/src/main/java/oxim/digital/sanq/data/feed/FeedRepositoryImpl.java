package oxim.digital.sanq.data.feed;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;
import oxim.digital.sanq.domain.model.Feed;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class FeedRepositoryImpl implements FeedRepository {

    private final List<Feed> feeds = new ArrayList<>();

    private final BehaviorProcessor<List<Feed>> feedsProcessor = BehaviorProcessor.createDefault(feeds);

    @Override
    public Flowable<List<Feed>> getUserFeeds() {
        return feedsProcessor;
    }

    @Override
    public Completable createNewFeed(final String feedUrl) {
        return Completable.fromAction(() -> {
            createFeedInternal(feedUrl);
            publishUserFeedsChange();
        });
    }

    private void createFeedInternal(final String feedUrl) {
        feeds.add(new Feed(0,
                           "Feed",
                           "Image",
                           "link",
                           "User feed",
                           feedUrl));
    }

    private void publishUserFeedsChange() {
        feedsProcessor.onNext(feeds);
    }
}
