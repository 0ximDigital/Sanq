package oxim.digital.sanq.data.feed.service;

import io.reactivex.Single;
import oxim.digital.sanq.data.feed.service.model.ApiFeed;

public interface FeedService {

    Single<ApiFeed> fetchFeed(final String feedUrl);
}
