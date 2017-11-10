package oxim.digital.sanq.dagger.fragment.module;

import android.arch.lifecycle.ViewModelProviders;

import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Supplier;

import dagger.Module;
import dagger.Provides;
import oxim.digital.sanq.base.ViewPresenter;
import oxim.digital.sanq.configuration.PresenterRetainer;
import oxim.digital.sanq.dagger.fragment.DaggerFragment;
import oxim.digital.sanq.dagger.fragment.FragmentComponent;
import oxim.digital.sanq.dagger.fragment.FragmentScope;
import oxim.digital.sanq.ui.feed.create.NewFeedSubscriptionContract;
import oxim.digital.sanq.ui.feed.create.NewFeedSubscriptionPresenter;
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
        return providePresenter(UserSubscriptionsPresenter::new, userSubscriptionsPresenter -> getFragmentComponent().inject(userSubscriptionsPresenter));
    }

    @Provides
    @FragmentScope
    public NewFeedSubscriptionContract.Presenter provideNewFeedSubscriptionPresenter() {
        return providePresenter(NewFeedSubscriptionPresenter::new, newFeedSubscriptionPresenter -> getFragmentComponent().inject(newFeedSubscriptionPresenter));
    }

    private <T extends ViewPresenter> T providePresenter(final Supplier<T> presenterSupplier, final Consumer<T> presenterInjection) {
        final PresenterRetainer presenterRetainer = ViewModelProviders.of(daggerFragment).get(PresenterRetainer.class);
        if (presenterRetainer.hasPresenter()) {
            final T presenter = (T) presenterRetainer.getPresenter().get();
            presenterInjection.accept(presenter);
            return presenter;
        }

        final T presenter = presenterSupplier.get();
        presenterInjection.accept(presenter);
        presenterRetainer.setViewPresenter(presenter);
        presenter.start();
        return presenter;
    }
}
