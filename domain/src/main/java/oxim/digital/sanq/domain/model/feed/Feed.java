package oxim.digital.sanq.domain.model.feed;

public final class Feed {

    public final String id;
    public final String title;
    public final String imageUrl;
    public final String pageLink;
    public final String description;
    public final String url;

    public Feed(final String id, final String title, final String imageUrl, final String pageLink, final String description, final String url) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.pageLink = pageLink;
        this.description = description;
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

        final Feed feed = (Feed) o;

        if (id != null ? !id.equals(feed.id) : feed.id != null) {
            return false;
        }
        if (title != null ? !title.equals(feed.title) : feed.title != null) {
            return false;
        }
        if (imageUrl != null ? !imageUrl.equals(feed.imageUrl) : feed.imageUrl != null) {
            return false;
        }
        if (pageLink != null ? !pageLink.equals(feed.pageLink) : feed.pageLink != null) {
            return false;
        }
        if (description != null ? !description.equals(feed.description) : feed.description != null) {
            return false;
        }
        return url != null ? url.equals(feed.url) : feed.url == null;
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

    @Override
    public String toString() {
        return "Feed{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", pageLink='" + pageLink + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
