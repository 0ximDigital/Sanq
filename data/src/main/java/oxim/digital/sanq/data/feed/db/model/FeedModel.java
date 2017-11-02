package oxim.digital.sanq.data.feed.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "feeds",
        indices = {@Index(value = "url", unique = true)})
public final class FeedModel {

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "image_url")
    String imageUrl;

    @ColumnInfo(name = "page_link")
    String pageLink;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "url")
    String url;

    @Ignore
    public FeedModel(final String title, final String imageUrl, final String pageLink, final String description, final String url) {
        this(0, title, imageUrl, pageLink, description, url);
    }

    public FeedModel(final int id, final String title, final String imageUrl, final String pageLink, final String description, final String url) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.pageLink = pageLink;
        this.description = description;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPageLink() {
        return pageLink;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPageLink(final String pageLink) {
        this.pageLink = pageLink;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final FeedModel feedModel = (FeedModel) o;

        if (id != feedModel.id) {
            return false;
        }
        if (title != null ? !title.equals(feedModel.title) : feedModel.title != null) {
            return false;
        }
        if (imageUrl != null ? !imageUrl.equals(feedModel.imageUrl) : feedModel.imageUrl != null) {
            return false;
        }
        if (pageLink != null ? !pageLink.equals(feedModel.pageLink) : feedModel.pageLink != null) {
            return false;
        }
        if (description != null ? !description.equals(feedModel.description) : feedModel.description != null) {
            return false;
        }
        return url != null ? url.equals(feedModel.url) : feedModel.url == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (pageLink != null ? pageLink.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
