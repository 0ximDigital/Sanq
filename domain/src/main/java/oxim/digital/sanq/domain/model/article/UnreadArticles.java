package oxim.digital.sanq.domain.model.article;

public final class UnreadArticles {

    public final long count;

    public static UnreadArticles from(final long count) {
        return new UnreadArticles(count);
    }

    public UnreadArticles(final long count) {
        this.count = count;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UnreadArticles that = (UnreadArticles) o;

        return count == that.count;
    }

    @Override
    public int hashCode() {
        return (int) (count ^ (count >>> 32));
    }

    @Override
    public String toString() {
        return "UnreadArticles{" +
                "count=" + count +
                '}';
    }
}
