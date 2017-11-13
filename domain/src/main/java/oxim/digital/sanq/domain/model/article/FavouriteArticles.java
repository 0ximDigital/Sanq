package oxim.digital.sanq.domain.model.article;

import java.util.List;

public final class FavouriteArticles {

    public final List<Article> favouriteArticles;

    public static FavouriteArticles from(final List<Article> articles) {
        return new FavouriteArticles(articles);
    }

    public FavouriteArticles(final List<Article> favouriteArticles) {
        this.favouriteArticles = favouriteArticles;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final FavouriteArticles that = (FavouriteArticles) o;

        return favouriteArticles != null ? favouriteArticles.equals(that.favouriteArticles) : that.favouriteArticles == null;
    }

    @Override
    public int hashCode() {
        return favouriteArticles != null ? favouriteArticles.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "FavouriteArticles{" +
                "favouriteArticles=" + favouriteArticles +
                '}';
    }
}