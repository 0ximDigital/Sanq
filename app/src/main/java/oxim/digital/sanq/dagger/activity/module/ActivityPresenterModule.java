package oxim.digital.sanq.dagger.activity.module;

import dagger.Module;
import dagger.Provides;
import oxim.digital.sanq.dagger.activity.ActivityScope;
import oxim.digital.sanq.dagger.activity.DaggerActivity;
import oxim.digital.sanq.ui.home.HomeContract;
import oxim.digital.sanq.ui.home.HomePresenter;

@Module
public class ActivityPresenterModule {

    private final DaggerActivity activity;

    public ActivityPresenterModule(final DaggerActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    HomeContract.Presenter provideHomePresenter() {
        final HomeContract.View view = (HomeContract.View) activity;
        final HomePresenter presenter = new HomePresenter(view, view.provideViewState());
        activity.getActivityComponent().inject(presenter);
        return presenter;
    }
}
