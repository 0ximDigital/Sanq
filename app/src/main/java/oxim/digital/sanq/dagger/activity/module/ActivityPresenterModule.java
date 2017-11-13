package oxim.digital.sanq.dagger.activity.module;

import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import oxim.digital.sanq.configuration.PresenterRetainer;
import oxim.digital.sanq.dagger.activity.ActivityScope;
import oxim.digital.sanq.dagger.activity.DaggerActivity;
import oxim.digital.sanq.ui.home.HomeContract;
import oxim.digital.sanq.ui.home.HomePresenter;

@Module
public final class ActivityPresenterModule {

    private final DaggerActivity activity;

    public ActivityPresenterModule(final DaggerActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    HomeContract.Presenter provideHomePresenter() {
        final PresenterRetainer presenterRetainer = ViewModelProviders.of(activity).get(PresenterRetainer.class);
        if (presenterRetainer.hasPresenter()) {
            final HomePresenter presenter = (HomePresenter) presenterRetainer.getPresenter().get();
            activity.getActivityComponent().inject(presenter);
            return presenter;
        }

        final HomePresenter presenter = new HomePresenter();
        activity.getActivityComponent().inject(presenter);
        presenter.start();
        presenterRetainer.setViewPresenter(presenter);
        return presenter;
    }
}
