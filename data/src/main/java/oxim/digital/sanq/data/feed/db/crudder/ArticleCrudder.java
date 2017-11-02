package oxim.digital.sanq.data.feed.db.crudder;

import com.annimon.stream.Stream;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import oxim.digital.sanq.data.feed.converter.ModelConverter;
import oxim.digital.sanq.data.feed.db.dao.ArticleDao;
import oxim.digital.sanq.data.feed.service.model.ApiArticle;
import oxim.digital.sanq.data.reactive.BaseObservableDataSource;
import oxim.digital.sanq.domain.model.Article;

public final class ArticleCrudder extends BaseObservableDataSource {

    private final ModelConverter modelConverter;
    private final ArticleDao articleDao;

    public ArticleCrudder(final ModelConverter modelConverter, final ArticleDao articleDao) {
        this.modelConverter = modelConverter;
        this.articleDao = articleDao;
    }

    public Completable insertArticle(final ApiArticle article) {
        return Completable.fromAction(() -> articleDao.insertArticle(modelConverter.apiToModel(article, 0)))
                          .andThen(notifySourceInvalidated());
    }

    public Completable insertArticles(final List<ApiArticle> articles) {
        return Completable.fromAction(() -> insertArticlesInternal(articles))
                          .andThen(notifySourceInvalidated());
    }

    private void insertArticlesInternal(final List<ApiArticle> articles) {
        Stream.of(articles)
              .map(article -> modelConverter.apiToModel(article, 0))
              .forEach(articleDao::insertArticle);
    }

    public Flowable<List<Article>> getAllArticles() {
        return query(() -> Stream.of(articleDao.getAllArticles())
                                 .map(modelConverter::modelToDomain)
                                 .toList());
    }

    public Flowable<List<Article>> getFavouriteArticles() {
        return query(() -> Stream.of(articleDao.getFavouriteArticles())
                                 .map(modelConverter::modelToDomain)
                                 .toList());
    }

    public Completable favouriteArticle(int articleId) {
        return Completable.fromAction(() -> articleDao.favouriteArticle(articleId))
                          .andThen(notifySourceInvalidated());
    }

    public Completable unfavouriteArticle(int articleId) {
        return Completable.fromAction(() -> articleDao.unfavouriteArticle(articleId))
                          .andThen(notifySourceInvalidated());
    }
}
