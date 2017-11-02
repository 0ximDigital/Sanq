package oxim.digital.sanq.data.feed.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "articles",
        indices = {@Index(value = {"feed_id", "link"}, unique = true)})
public final class ArticleModel {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "feed_id")
    int feedId;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "link")
    String link;

    @ColumnInfo(name = "publication_date")
    long publicationDate;

    @ColumnInfo(name = "is_new")
    boolean isNew;

    @ColumnInfo(name = "is_favourite")
    boolean isFavourite;

    @Ignore
    public ArticleModel(final int feedId, final String title, final String link, final long publicationDate) {
        this(feedId, title, link, publicationDate, true, false);
    }

    public ArticleModel(final int feedId, final String title, final String link, final long publicationDate, final boolean isNew, final boolean isFavourite) {
        this.feedId = feedId;
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
        this.isNew = isNew;
        this.isFavourite = isFavourite;
    }

    public int getId() {
        return id;
    }

    public int getFeedId() {
        return feedId;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public long getPublicationDate() {
        return publicationDate;
    }

    public boolean isNew() {
        return isNew;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setFeedId(final int feedId) {
        this.feedId = feedId;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public void setPublicationDate(final long publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setNew(final boolean aNew) {
        isNew = aNew;
    }

    public void setFavourite(final boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ArticleModel that = (ArticleModel) o;

        if (id != that.id) {
            return false;
        }
        if (feedId != that.feedId) {
            return false;
        }
        if (publicationDate != that.publicationDate) {
            return false;
        }
        if (isNew != that.isNew) {
            return false;
        }
        if (isFavourite != that.isFavourite) {
            return false;
        }
        if (title != null ? !title.equals(that.title) : that.title != null) {
            return false;
        }
        return link != null ? link.equals(that.link) : that.link == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + feedId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (int) (publicationDate ^ (publicationDate >>> 32));
        result = 31 * result + (isNew ? 1 : 0);
        result = 31 * result + (isFavourite ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\nArticleModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isFavourite=" + isFavourite +
                '}';
    }
}
