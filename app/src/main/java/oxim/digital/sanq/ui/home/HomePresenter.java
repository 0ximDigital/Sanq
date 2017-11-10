package oxim.digital.sanq.ui.home;

import oxim.digital.sanq.base.BasePresenter;

public final class HomePresenter extends BasePresenter<Object> implements HomeContract.Presenter {

    @Override
    protected Object initialViewState() {
        return new Object();
    }

    @Override
    public void start() {
        super.start();
        router.showUserSubscriptionsScreen();
    }
}
