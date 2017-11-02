package oxim.digital.sanq.data.feed.service.parser;

import java.io.InputStream;

import io.reactivex.Single;
import oxim.digital.sanq.data.feed.service.model.ApiFeed;

public interface FeedParser {

    Single<ApiFeed> parseFeed(InputStream inputStream, String feedUrl);
}
