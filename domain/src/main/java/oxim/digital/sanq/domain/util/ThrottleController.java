package oxim.digital.sanq.domain.util;

import java.util.concurrent.TimeUnit;

public interface ThrottleController {

    void setThrottleWindow(long windowDuration, TimeUnit unit);
}
