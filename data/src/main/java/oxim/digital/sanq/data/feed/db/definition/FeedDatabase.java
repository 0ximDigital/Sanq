package oxim.digital.sanq.data.feed.db.definition;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import oxim.digital.sanq.data.feed.db.dao.ArticleDao;
import oxim.digital.sanq.data.feed.db.model.ArticleModel;
import oxim.digital.sanq.data.feed.db.model.FeedModel;

@Database(
        entities = {FeedModel.class,
                    ArticleModel.class},
        version = FeedDatabase.VERSION)
public abstract class FeedDatabase extends RoomDatabase {

    public static final String NAME = "FeedDatabase";

    public static final int VERSION = 1;

    public abstract ArticleDao articleDao();
}
