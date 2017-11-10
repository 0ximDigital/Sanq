package oxim.digital.sanq.dagger.fragment.module;

import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import oxim.digital.sanq.configuration.PresenterRetainer;
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
        final PresenterRetainer presenterRetainer = ViewModelProviders.of(daggerFragment).get(PresenterRetainer.class);
        if (presenterRetainer.hasPresenter()) {
            final UserSubscriptionsPresenter presenter = (UserSubscriptionsPresenter) presenterRetainer.getPresenter().get();
            getFragmentComponent().inject(presenter);
            return presenter;
        }

        final UserSubscriptionsPresenter presenter = new UserSubscriptionsPresenter();
        getFragmentComponent().inject(presenter);
        presenterRetainer.setViewPresenter(presenter);
        presenter.start();
        return presenter;
    }
}
