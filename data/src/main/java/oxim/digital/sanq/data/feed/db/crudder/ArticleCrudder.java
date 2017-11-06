package oxim.digital.sanq.data.feed.db.crudder;

import com.annimon.stream.Stream;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import oxim.digital.sanq.data.feed.converter.ModelConverter;
import oxim.digital.sanq.data.feed.db.dao.ArticleDao;
import oxim.digital.sanq.data.feed.service.model.ApiArticle;
import oxim.digital.sanq.data.reactive.ObservableDataSource;
import oxim.digital.sanq.domain.model.AllArticles;
import oxim.digital.sanq.domain.model.FavouriteArticles;

public final class ArticleCrudder extends ObservableDataSource<ArticleDao> {

    private final ModelConverter modelConverter;

    public ArticleCrudder(final ArticleDao articleDao, final ModelConverter modelConverter) {
        super(articleDao);
        this.modelConverter = modelConverter;
    }

    public Completable insertArticle(final ApiArticle article) {
        return command(articleDao -> articleDao.insertArticle(modelConverter.apiToModel(article, 0)));
    }

    public Completable insertArticles(final List<ApiArticle> articles) {
        return command(articleDao -> insertArticlesInternal(articleDao, articles));
    }

    private void insertArticlesInternal(final ArticleDao articleDao, final List<ApiArticle> articles) {
        Stream.of(articles)
              .map(article -> modelConverter.apiToModel(article, 0))
              .forEach(articleDao::insertArticle);
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