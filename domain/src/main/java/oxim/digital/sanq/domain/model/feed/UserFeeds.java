package oxim.digital.sanq.domain.model.feed;

import java.util.List;

public final class UserFeeds {

    public final List<Feed> userFeeds;

    public static UserFeeds from(final List<Feed> userFeeds) {
        return new UserFeeds(userFeeds);
    }

    public UserFeeds(final List<Feed> userFeeds) {
        this.userFeeds = userFeeds;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UserFeeds userFeeds1 = (UserFeeds) o;

        return userFeeds != null ? userFeeds.equals(userFeeds1.userFeeds) : userFeeds1.userFeeds == null;
    }

    @Override
    public int hashCode() {
        return userFeeds != null ? userFeeds.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserFeeds{" +
                "userFeeds=" + userFeeds +
                '}';
    }
}
