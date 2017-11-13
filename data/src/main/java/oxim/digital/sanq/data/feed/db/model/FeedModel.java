package oxim.digital.sanq.data.feed.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "feeds",
        indices = {@Index(value = "url", unique = true)})
public final class FeedModel {

    @ColumnInfo
    @NonNull
    @PrimaryKey
    String id;

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

    public FeedModel(@NonNull final String id, final String title, final String imageUrl, final String pageLink, final String description, final String url) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.pageLink = pageLink;
        this.description = description;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(@NonNull final String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPageLink() {
        return pageLink;
    }

    public void setPageLink(final String pageLink) {
        this.pageLink = pageLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
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

        if (id != null ? !id.equals(feedModel.id) : feedModel.id != null) {
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
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (pageLink != null ? pageLink.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
