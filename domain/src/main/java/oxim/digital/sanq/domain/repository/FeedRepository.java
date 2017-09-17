package oxim.digital.sanq.domain.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import oxim.digital.sanq.domain.model.Feed;

public interface FeedRepository {

    Flowable<List<Feed>> getUserFeeds();

    Completable createNewFeed(String feedUrl);
}
