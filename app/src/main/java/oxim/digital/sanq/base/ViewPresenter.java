package oxim.digital.sanq.base;

import io.reactivex.Flowable;

public interface ViewPresenter<View, ViewState> {

    void start();

    void onViewAttached(View view);

    Flowable<ViewState> viewState();

    void onViewDetached();

    void destroy();

    void back();
}
