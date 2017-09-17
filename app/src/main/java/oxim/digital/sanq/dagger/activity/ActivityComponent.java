package oxim.digital.sanq.dagger.activity;

import dagger.Component;
import oxim.digital.sanq.dagger.activity.module.ActivityModule;
import oxim.digital.sanq.dagger.activity.module.ActivityPresenterModule;
import oxim.digital.sanq.dagger.application.ApplicationComponent;

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                ActivityModule.class,
                ActivityPresenterModule.class
        }
)
public interface ActivityComponent extends ActivityComponentInjects, ActivityComponentExposes {

    final class Initializer {

        static public ActivityComponent init(final DaggerActivity daggerActivity, final ApplicationComponent applicationComponent) {
            return DaggerActivityComponent.builder()
                                          .applicationComponent(applicationComponent)
                                          .activityModule(new ActivityModule(daggerActivity))
                                          .activityPresenterModule(new ActivityPresenterModule(daggerActivity))
                                          .build();
        }

        private Initializer() {
        }
    }
}
