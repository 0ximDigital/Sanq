package oxim.digital.sanq.data.feed.db.crudder;

import com.annimon.stream.Stream;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import oxim.digital.sanq.data.feed.converter.ModelConverter;
import oxim.digital.sanq.data.feed.db.dao.FeedDao;
import oxim.digital.sanq.data.reactive.ObservableDataSource;
import oxim.digital.sanq.domain.model.feed.Feed;
import oxim.digital.sanq.domain.model.feed.UserFeeds;

public final class FeedDataSource extends ObservableDataSource<FeedDao> {

    private final ModelConverter modelConverter;

    public FeedDataSource(final FeedDao feedDao, final ModelConverter modelConverter) {
        super(feedDao);
        this.modelConverter = modelConverter;
    }

    public Flowable<UserFeeds> getUserFeeds() {
        return query(feedDao -> () -> UserFeeds.from(Stream.of(feedDao.getAllFeeds())
                                                           .map(modelConverter::modelToDomain)
                                                           .toList()), UserFeeds.class);
    }

    public Completable insertFeed(final Feed feed) {
        return command(feedDao -> feedDao.insertFeed(modelConverter.domainToModel(feed)));
    }

    public Completable deleteFeed(final int feedId) {
        return command(feedDao -> feedDao.deleteFeed(feedId));
    }

    public Flowable<Boolean> doesFeedExist(final String feedUrl) {
        return query(feedDao -> () -> feedDao.doesFeedExist(feedUrl));
    }
}
