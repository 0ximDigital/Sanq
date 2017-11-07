package oxim.digital.sanq.data.feed.db.crudder;

import com.annimon.stream.Stream;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import oxim.digital.sanq.data.feed.converter.ModelConverter;
import oxim.digital.sanq.data.feed.db.dao.ArticleDao;
import oxim.digital.sanq.data.reactive.ObservableDataSource;
import oxim.digital.sanq.domain.model.article.AllArticles;
import oxim.digital.sanq.domain.model.article.Article;
import oxim.digital.sanq.domain.model.article.FavouriteArticles;
import oxim.digital.sanq.domain.model.article.FeedArticles;
import oxim.digital.sanq.domain.model.article.UnreadArticles;

public final class ArticleDataSource extends ObservableDataSource<ArticleDao> {

    private final ModelConverter modelConverter;

    public ArticleDataSource(final ArticleDao articleDao, final ModelConverter modelConverter) {
        super(articleDao);
        this.modelConverter = modelConverter;
    }

    public Completable insertArticle(final Article article) {
        return command(articleDao -> articleDao.insertArticle(modelConverter.domainToModel(article)));
    }

    public Completable insertArticles(final List<Article> articles) {
        return command(articleDao -> Stream.of(articles)
                                           .map(modelConverter::domainToModel)
                                           .forEach(articleDao::insertArticle));
    }

    public Flowable<FeedArticles> getFeedArticles(final int feedId) {
        return query(articleDao -> () -> FeedArticles.from(feedId, Stream.of(articleDao.getArticlesForFeed(feedId))
                                                                         .map(modelConverter::modelToDomain)
                                                                         .toList()), FeedArticles.class);
    }

    public Completable markArticleAsRead(final int articleId) {
        return command(articleDao -> articleDao.markArticleAsRead(articleId));
    }

    public Flowable<UnreadArticles> getUnreadArticlesCount() {
        return query(articleDao -> () -> UnreadArticles.from(articleDao.getUnreadArticlesCount()), UnreadArticles.class);
    }

    public Flowable<AllArticles> getAllArticles() {
        return query(articleDao -> () -> AllArticles.from(Stream.of(articleDao.getAllArticles())
                                                                .map(modelConverter::modelToDomain)
                                                                .toList()), AllArticles.class);
    }

    public Flowable<FavouriteArticles> getFavouriteArticles() {
        return query(articleDao -> () -> FavouriteArticles.from(Stream.of(articleDao.getFavouriteArticles())
                                                                      .map(modelConverter::modelToDomain)
                                                                      .toList()), FavouriteArticles.class);
    }

    public Completable favouriteArticle(int articleId) {
        return command(articleDao -> articleDao.favouriteArticle(articleId));
    }

    public Completable unfavouriteArticle(int articleId) {
        return command(articleDao -> articleDao.unfavouriteArticle(articleId));
    }
}