package oxim.digital.sanq.data.feed.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import oxim.digital.sanq.data.feed.db.model.FeedModel;

@Dao
public interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    long insertFeed(FeedModel feedModel);

    @Query("SELECT * FROM feeds")
    List<FeedModel> getAllFeeds();

    @Query("SELECT * FROM feeds WHERE feeds.url = (:feedUrl)")
    boolean doesFeedExist(String feedUrl);

    @Query("DELETE FROM feeds WHERE feeds.id = (:feedId)")
    void deleteFeed(int feedId);
}
