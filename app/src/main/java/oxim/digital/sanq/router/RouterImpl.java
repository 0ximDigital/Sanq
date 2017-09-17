package oxim.digital.sanq.router;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;

import com.annimon.stream.Optional;

import oxim.digital.sanq.R;
import oxim.digital.sanq.dagger.activity.DaggerActivity;
import oxim.digital.sanq.ui.feed.subscription.UserSubscriptionsFragment;

public final class RouterImpl implements Router {

    @IdRes
    private static final int CONTAINER_ID = R.id.activity_content_container;

    private static final int LAST_FRAGMENT = 0;

    private final DaggerActivity activity;
    private final FragmentManager fragmentManager;

    public RouterImpl(final DaggerActivity activity, final FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void showUserSubscriptionsScreen() {
        Optional.ofNullable(fragmentManager.findFragmentByTag(UserSubscriptionsFragment.TAG))
                .executeIfAbsent(this::showUserSubscriptionScreenInternal);
    }

    private void showUserSubscriptionScreenInternal() {
        fragmentManager.beginTransaction()
                       .add(CONTAINER_ID, UserSubscriptionsFragment.newInstance(), UserSubscriptionsFragment.TAG)
                       .commit();
    }

    @Override
    public void showAddNewFeedScreen() {
        // To be implemented
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
