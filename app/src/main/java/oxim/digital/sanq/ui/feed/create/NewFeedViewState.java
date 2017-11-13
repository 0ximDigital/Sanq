package oxim.digital.sanq.ui.feed.create;

public final class NewFeedViewState {

    private String message = "";
    private boolean isLoading;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(final boolean loading) {
        isLoading = loading;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final NewFeedViewState that = (NewFeedViewState) o;

        if (isLoading != that.isLoading) {
            return false;
        }
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (isLoading ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewFeedViewState{" +
                "message='" + message + '\'' +
                ", isLoading=" + isLoading +
                '}';
    }
}
