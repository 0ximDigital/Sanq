package oxim.digital.sanq.configuration;

public interface ViewConsumerQueueFactory {

    <View> ViewConsumerQueue<View> getViewConsumerQueue();
}
