package oxim.digital.sanq.data.util;

public class CurrentTimeProviderImpl implements CurrentTimeProvider {

    @Override
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
