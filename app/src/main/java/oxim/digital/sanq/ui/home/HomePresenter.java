package oxim.digital.sanq.ui.home;

import oxim.digital.sanq.base.BasePresenter;

public final class HomePresenter extends BasePresenter<HomeContract.View, HomeViewModel> implements HomeContract.Presenter {

    public HomePresenter(final HomeContract.View view, final HomeViewModel viewState) {
        super(view, viewState);
    }

    @Override
    public void start() {
        super.start();
        router.showUserSubscriptionsScreen();
    }
}
