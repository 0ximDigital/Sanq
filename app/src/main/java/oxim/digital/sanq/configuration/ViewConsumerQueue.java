package oxim.digital.sanq.configuration;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public interface ViewConsumerQueue<View> {

    void resume();

    void enqueueViewConsumer(Consumer<View> viewConsumer);

    void pause();

    void destroy();

    Flowable<Consumer<View>> viewConsumersFlowable();
}
