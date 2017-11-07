package oxim.digital.sanq.configuration;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public interface ViewActionQueue<View> {

    void resume();

    void enqueueViewAction(Consumer<View> viewAction);

    void pause();

    void destroy();

    Flowable<Consumer<View>> viewActions();
}
