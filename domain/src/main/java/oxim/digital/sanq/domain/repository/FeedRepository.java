package oxim.digital.sanq.domain.repository;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import oxim.digital.sanq.domain.model.article.FavouriteArticles;
import oxim.digital.sanq.domain.model.feed.Feed;
import oxim.digital.sanq.domain.model.article.FeedArticles;
import oxim.digital.sanq.domain.model.article.UnreadArticles;
import oxim.digital.sanq.domain.model.feed.UserFeeds;

public interface FeedRepository {

    Flowable<UserFeeds> getUserFeeds();

    Completable createNewFeed(String feedUrl);

    Flowable<FeedArticles> getArticles(int feedId);

    Flowable<Boolean> feedExists(String feedUrl);

    Completable deleteFeed(int feedId);

    Completable refreshFeedArticles(Feed feed);

    Completable markArticleAsRead(int articleId);

    Completable favouriteArticle(int articleId);

    Completable unFavouriteArticle(int articleId);

    Flowable<UnreadArticles> getUnreadArticlesCount();

    Flowable<FavouriteArticles> getFavouriteArticles();

    Flowable<Boolean> shouldUpdateFeedsInBackground();

    Completable setShouldUpdateFeedsInBackground(boolean shouldUpdate);
}
