package oxim.digital.sanq.data.feed.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import oxim.digital.sanq.data.feed.db.model.ArticleModel;

@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertArticle(ArticleModel articleModel);

    @Query("SELECT * FROM articles")
    List<ArticleModel> getAllArticles();

    @Query("SELECT * FROM articles WHERE articles.is_favourite = 1")
    List<ArticleModel> getFavouriteArticles();

    @Query("UPDATE articles SET is_favourite = 1 WHERE articles.id = (:articleId)")
    void favouriteArticle(int articleId);

    @Query("UPDATE articles SET is_favourite = 0 WHERE articles.id = (:articleId)")
    void unfavouriteArticle(int articleId);

    @Query("SELECT * FROM articles WHERE articles.feed_id = (:feedId)")
    List<ArticleModel> getArticlesForFeed(int feedId);

    @Query("UPDATE articles SET is_new = 0 WHERE id = (:articleId)")
    void markArticleAsRead(final int articleId);

    @Query("SELECT count(*) FROM articles where is_new = 1")
    long getUnreadArticlesCount();
}
