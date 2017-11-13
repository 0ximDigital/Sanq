package oxim.digital.sanq.data.util;

import java.util.UUID;

public final class FeedIdGeneratorImpl implements FeedIdGenerator {

    @Override
    public String nextId() {
        return UUID.randomUUID().toString();
    }
}
