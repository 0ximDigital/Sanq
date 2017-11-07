package oxim.digital.sanq.data.feed;

import com.annimon.stream.Stream;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import oxim.digital.sanq.data.feed.converter.ModelConverter;
import oxim.digital.sanq.data.feed.db.crudder.ArticleDataSource;
import oxim.digital.sanq.data.feed.db.crudder.FeedDataSource;
import oxim.digital.sanq.data.feed.service.FeedService;
import oxim.digital.sanq.data.feed.service.model.ApiArticle;
import oxim.digital.sanq.data.feed.service.model.ApiFeed;
import oxim.digital.sanq.data.util.FeedIdGenerator;
import oxim.digital.sanq.domain.model.article.FavouriteArticles;
import oxim.digital.sanq.domain.model.feed.Feed;
import oxim.digital.sanq.domain.model.article.FeedArticles;
import oxim.digital.sanq.domain.model.article.UnreadArticles;
import oxim.digital.sanq.domain.model.feed.UserFeeds;
import oxim.digital.sanq.domain.repository.FeedRepository;

public final class FeedRepositoryImpl implements FeedRepository {

    private final ArticleDataSource articleDataSource;
    private final FeedDataSource feedDataSource;

    private final FeedService feedService;

    private final ModelConverter modelConverter;
    private final FeedIdGenerator feedIdGenerator;

    public FeedRepositoryImpl(final ArticleDataSource articleDataSource, final FeedDataSource feedDataSource, final FeedService feedService,
                              final ModelConverter modelConverter, final FeedIdGenerator feedIdGenerator) {
        this.articleDataSource = articleDataSource;
        this.feedDataSource = feedDataSource;
        this.feedService = feedService;
        this.modelConverter = modelConverter;
        this.feedIdGenerator = feedIdGenerator;
    }

    @Override
    public Flowable<UserFeeds> getUserFeeds() {
        return feedDataSource.getUserFeeds();
    }

    @Override
    public Completable createNewFeed(final String feedUrl) {
        return Completable.defer(() -> feedService.fetchFeed(feedUrl)
                                                  .flatMapCompletable(this::createNewFeedInternal));
    }

    private Completable createNewFeedInternal(final ApiFeed apiFeed) {
        final String newFeedId = feedIdGenerator.nextId();
        return Completable.mergeArray(feedDataSource.insertFeed(modelConverter.apiToDomain(apiFeed, newFeedId)),
                                      updateArticles(apiFeed.articles, newFeedId));
    }

    @Override
    public Flowable<FeedArticles> getArticles(final int feedId) {
        return articleDataSource.getFeedArticles(feedId);
    }

    @Override
    public Flowable<Boolean> feedExists(final String feedUrl) {
        return feedDataSource.doesFeedExist(feedUrl);
    }

    @Override
    public Completable deleteFeed(final int feedId) {
        return feedDataSource.deleteFeed(feedId);
    }

    @Override
    public Completable refreshFeedArticles(final Feed feed) {
        return Completable.defer(() -> feedService.fetchFeed(feed.url)
                                                  .flatMapCompletable(apiFeed -> updateArticles(apiFeed.articles, feed.id)));
    }

    private Completable updateArticles(final List<ApiArticle> apiArticles, final String feedId) {
        return Completable.fromAction(() -> articleDataSource.insertArticles(Stream.of(apiArticles).map(apiArticle -> modelConverter.apiToDomain(apiArticle, feedId)).toList()));
    }

    @Override
    public Completable markArticleAsRead(final int articleId) {
        return articleDataSource.markArticleAsRead(articleId);
    }

    @Override
    public Completable favouriteArticle(final int articleId) {
        return articleDataSource.favouriteArticle(articleId);
    }

    @Override
    public Completable unFavouriteArticle(final int articleId) {
        return articleDataSource.unfavouriteArticle(articleId);
    }

    @Override
    public Flowable<UnreadArticles> getUnreadArticlesCount() {
        return articleDataSource.getUnreadArticlesCount();
    }

    @Override
    public Flowable<FavouriteArticles> getFavouriteArticles() {
        return articleDataSource.getFavouriteArticles();
    }

    @Override
    public Flowable<Boolean> shouldUpdateFeedsInBackground() {
        return Flowable.just(false);    // TODO
    }

    @Override
    public Completable setShouldUpdateFeedsInBackground(final boolean shouldUpdate) {
        return Completable.complete();      // TODO
    }
}
