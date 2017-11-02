package oxim.digital.sanq.data.feed.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import oxim.digital.sanq.data.feed.db.model.ArticleModel;

@Dao
public interface ArticleDao {

    @Insert
    public long insertArticle(ArticleModel articleModel);

    @Query("SELECT * FROM articles")
    public List<ArticleModel> getAllArticles();

    @Query("SELECT * FROM articles WHERE articles.is_favourite = 1")
    public List<ArticleModel> getFavouriteArticles();

    @Query("UPDATE articles SET is_favourite = 1 WHERE articles.id = (:articleId)")
    public void favouriteArticle(int articleId);

    @Query("UPDATE articles SET is_favourite = 0 WHERE articles.id = (:articleId)")
    public void unfavouriteArticle(int articleId);
}