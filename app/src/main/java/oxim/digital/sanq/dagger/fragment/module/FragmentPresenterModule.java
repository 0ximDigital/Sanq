package oxim.digital.sanq.dagger.fragment.module;

import dagger.Module;
import oxim.digital.sanq.dagger.fragment.DaggerFragment;
import oxim.digital.sanq.dagger.fragment.FragmentComponent;

@Module
public final class FragmentPresenterModule {

    private final DaggerFragment daggerFragment;

    public FragmentPresenterModule(final DaggerFragment daggerFragment) {
        this.daggerFragment = daggerFragment;
    }

    private FragmentComponent getFragmentComponent() {
        return daggerFragment.getFragmentComponent();
    }

    // @Provides
    //@FragmentScope
    //public UserSubscriptionsContract.ScopedPresenter provideUserSubscriptionsPresenter() {
    //  final UserSubscriptionsPresenter userSubscriptionsPresenter = new UserSubscriptionsPresenter((UserSubscriptionsContract.View) daggerFragment);
    // getFragmentComponent().inject(userSubscriptionsPresenter);
    // return userSubscriptionsPresenter;
    //   }
}
