package oxim.digital.sanq.domain.model.article;

import java.util.List;

public final class FeedArticles {

    public final int feedId;
    public final List<Article> feedArticles;

    public static FeedArticles from(final int feedId, final List<Article> articles) {
        return new FeedArticles(feedId, articles);
    }

    public FeedArticles(final int feedId, final List<Article> feedArticles) {
        this.feedId = feedId;
        this.feedArticles = feedArticles;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final FeedArticles that = (FeedArticles) o;

        if (feedId != that.feedId) {
            return false;
        }
        return feedArticles != null ? feedArticles.equals(that.feedArticles) : that.feedArticles == null;
    }

    @Override
    public int hashCode() {
        int result = feedId;
        result = 31 * result + (feedArticles != null ? feedArticles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FeedArticles{" +
                "feedId=" + feedId +
                ", feedArticles=" + feedArticles +
                '}';
    }
}
