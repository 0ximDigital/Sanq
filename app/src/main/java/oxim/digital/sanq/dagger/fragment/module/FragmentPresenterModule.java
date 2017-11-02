package oxim.digital.sanq.dagger.fragment.module;

import dagger.Module;
import dagger.Provides;
import oxim.digital.sanq.dagger.fragment.DaggerFragment;
import oxim.digital.sanq.dagger.fragment.FragmentComponent;
import oxim.digital.sanq.dagger.fragment.FragmentScope;
import oxim.digital.sanq.ui.feed.subscription.UserSubscriptionsContract;
import oxim.digital.sanq.ui.feed.subscription.UserSubscriptionsPresenter;

@Module
public final class FragmentPresenterModule {

    private final DaggerFragment daggerFragment;

    public FragmentPresenterModule(final DaggerFragment daggerFragment) {
        this.daggerFragment = daggerFragment;
    }

    private FragmentComponent getFragmentComponent() {
        return daggerFragment.getFragmentComponent();
    }

    @Provides
    @FragmentScope
    public UserSubscriptionsContract.Presenter provideUserSubscriptionsPresenter() {
        final UserSubscriptionsPresenter userSubscriptionsPresenter = new UserSubscriptionsPresenter((UserSubscriptionsContract.View) daggerFragment);
        getFragmentComponent().inject(userSubscriptionsPresenter);
        return userSubscriptionsPresenter;
    }
}