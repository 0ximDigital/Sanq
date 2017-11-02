package oxim.digital.sanq.data.feed.db;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import oxim.digital.sanq.data.feed.service.model.ApiArticle;
import oxim.digital.sanq.data.feed.service.model.ApiFeed;
import oxim.digital.sanq.domain.model.Article;
import oxim.digital.sanq.domain.model.Feed;

public interface FeedCrudder {

    Single<List<Feed>> getAllFeeds();

    Completable insertFeed(ApiFeed apiFeed);

    Completable updateFeed(int feedId, List<ApiArticle> apiArticles);

    Single<List<Article>> getArticlesForFeed(int feedId);

    Single<Boolean> doesFeedExist(String feedUrl);

    Completable deleteFeed(int feedId);

    Completable markArticlesAsRead(final int articleId);

    Completable favouriteArticle(final int articleId);

    Completable unFavouriteArticle(final int articleId);

    Single<Long> getUnreadArticlesCount();

    Single<List<Article>> getFavouriteArticles();
}
