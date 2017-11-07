package oxim.digital.sanq.domain.model.article;

import java.util.List;

public final class AllArticles {

    public final List<Article> articles;

    public static AllArticles from(final List<Article> articles) {
        return new AllArticles(articles);
    }

    public AllArticles(final List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AllArticles that = (AllArticles) o;

        return articles != null ? articles.equals(that.articles) : that.articles == null;
    }

    @Override
    public int hashCode() {
        return articles != null ? articles.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AllArticles{" +
                "articles=" + articles +
                '}';
    }
}
