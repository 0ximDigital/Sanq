package oxim.digital.sanq.router;

import android.support.v4.app.FragmentManager;

import oxim.digital.sanq.dagger.activity.DaggerActivity;

public final class RouterImpl implements Router {

    private static final int LAST_FRAGMENT = 0;

    private final DaggerActivity activity;
    private final FragmentManager fragmentManager;

    public RouterImpl(final DaggerActivity activity, final FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void showUserSubscriptionsScreen() {

    }

    @Override
    public void showAddNewFeedScreen() {

    }

    @Override
    public void closeScreen() {
        activity.finish();
    }

    @Override
    public void goBack() {
        if (fragmentManager.getBackStackEntryCount() == LAST_FRAGMENT) {
            closeScreen();
        } else {
            fragmentManager.popBackStack();
        }
    }
}
