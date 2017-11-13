package oxim.digital.sanq.domain.util;

import java.util.concurrent.TimeUnit;

public interface ActionRouter {

    void setTiming(long windowDuration, TimeUnit timeUnit);

    void throttle(Runnable action);

    void throttle(long windowDuration, Runnable action);

    void throttle(long windowDuration, TimeUnit timeUnit, Runnable action);

    void blockActions();

    void unblockActions();
}
