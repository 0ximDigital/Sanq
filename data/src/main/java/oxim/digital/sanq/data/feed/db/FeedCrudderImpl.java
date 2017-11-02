package oxim.digital.sanq.data.feed.db;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import oxim.digital.sanq.data.feed.converter.ModelConverter;
import oxim.digital.sanq.data.feed.service.model.ApiArticle;
import oxim.digital.sanq.data.feed.service.model.ApiFeed;
import oxim.digital.sanq.domain.model.Article;
import oxim.digital.sanq.domain.model.Feed;

public class FeedCrudderImpl implements FeedCrudder {

    private final ModelConverter feedModelConverter;

    public FeedCrudderImpl(final ModelConverter feedModelConverter) {
        this.feedModelConverter = feedModelConverter;
    }

    @Override
    public Single<List<Feed>> getAllFeeds() {
        return null;
    }

    @Override
    public Completable insertFeed(final ApiFeed apiFeed) {
        return null;
    }

    @Override
    public Completable updateFeed(final int feedId, final List<ApiArticle> apiArticles) {
        return null;
    }

    @Override
    public Single<List<Article>> getArticlesForFeed(final int feedId) {
        return null;
    }

    @Override
    public Single<Boolean> doesFeedExist(final String feedUrl) {
        return null;
    }

    @Override
    public Completable deleteFeed(final int feedId) {
        return null;
    }

    @Override
    public Completable markArticlesAsRead(final int articleId) {
        return null;
    }

    @Override
    public Completable favouriteArticle(final int articleId) {
        return null;
    }

    @Override
    public Completable unFavouriteArticle(final int articleId) {
        return null;
    }

    @Override
    public Single<Long> getUnreadArticlesCount() {
        return null;
    }

    @Override
    public Single<List<Article>> getFavouriteArticles() {
        return null;
    }
}
