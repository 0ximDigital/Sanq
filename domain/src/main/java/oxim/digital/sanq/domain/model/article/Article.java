package oxim.digital.sanq.domain.model.article;

import jdk.nashorn.internal.ir.annotations.Ignore;

public final class Article {

    public final int id;
    public final String feedId;
    public final String title;
    public final String link;
    public final long publicationDate;

    public final boolean isNew;
    public final boolean isFavourite;

    @Ignore
    public Article(final String feedId, final String title, final String link, final long publicationDate) {
        this(0, feedId, title, link, publicationDate, true, false);
    }

    public Article(final int id, final String feedId, final String title, final String link, final long publicationDate, final boolean isNew, final boolean isFavourite) {
        this.id = id;
        this.feedId = feedId;
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
        this.isNew = isNew;
        this.isFavourite = isFavourite;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Article article = (Article) o;

        if (id != article.id) {
            return false;
        }
        if (publicationDate != article.publicationDate) {
            return false;
        }
        if (isNew != article.isNew) {
            return false;
        }
        if (isFavourite != article.isFavourite) {
            return false;
        }
        if (feedId != null ? !feedId.equals(article.feedId) : article.feedId != null) {
            return false;
        }
        if (title != null ? !title.equals(article.title) : article.title != null) {
            return false;
        }
        return link != null ? link.equals(article.link) : article.link == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (feedId != null ? feedId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (int) (publicationDate ^ (publicationDate >>> 32));
        result = 31 * result + (isNew ? 1 : 0);
        result = 31 * result + (isFavourite ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", feedId='" + feedId + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", publicationDate=" + publicationDate +
                ", isNew=" + isNew +
                ", isFavourite=" + isFavourite +
                '}';
    }
}
