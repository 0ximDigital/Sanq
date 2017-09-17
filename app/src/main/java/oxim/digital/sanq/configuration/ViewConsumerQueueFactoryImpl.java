package oxim.digital.sanq.configuration;

public final class ViewConsumerQueueFactoryImpl implements ViewConsumerQueueFactory {

    @Override
    public <View> ViewConsumerQueue<View> getViewConsumerQueue() {
        return new ViewConsumerQueueImpl<>();
    }
}
