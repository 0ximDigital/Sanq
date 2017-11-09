package oxim.digital.sanq.base;

import io.reactivex.Flowable;

public interface ViewPresenter<ViewState> {

    void start();

    Flowable<ViewState> viewState();

    void destroy();

    void back();
}
