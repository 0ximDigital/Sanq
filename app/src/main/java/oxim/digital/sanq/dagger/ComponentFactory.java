package oxim.digital.sanq.dagger;

import oxim.digital.sanq.dagger.activity.ActivityComponent;
import oxim.digital.sanq.dagger.activity.DaggerActivity;
import oxim.digital.sanq.dagger.application.ApplicationComponent;
import oxim.digital.sanq.dagger.application.SanqApplication;
import oxim.digital.sanq.dagger.fragment.DaggerFragment;
import oxim.digital.sanq.dagger.fragment.FragmentComponent;

public final class ComponentFactory {

    private ComponentFactory() {
    }

    public static ApplicationComponent createApplicationComponent(final SanqApplication sanqApplication) {
        return ApplicationComponent.Initializer.init(sanqApplication);
    }

    public static ActivityComponent createActivityComponent(final DaggerActivity daggerActivity, final SanqApplication sanqApplication) {
        return ActivityComponent.Initializer.init(daggerActivity, sanqApplication.getApplicationComponent());
    }

    public static FragmentComponent createFragmentComponent(final DaggerFragment daggerFragment, final ActivityComponent activityComponent) {
        return FragmentComponent.Initializer.init(daggerFragment, activityComponent);
    }
}
