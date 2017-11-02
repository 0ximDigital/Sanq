package oxim.digital.sanq.dagger.application;

import javax.inject.Singleton;

import dagger.Component;
import oxim.digital.sanq.dagger.application.module.ApplicationModule;
import oxim.digital.sanq.dagger.application.module.DataModule;
import oxim.digital.sanq.dagger.application.module.MappersModule;
import oxim.digital.sanq.dagger.application.module.ServiceModule;
import oxim.digital.sanq.dagger.application.module.ThreadingModule;
import oxim.digital.sanq.dagger.application.module.UseCaseModule;
import oxim.digital.sanq.dagger.application.module.UtilsModule;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                ThreadingModule.class,
                UtilsModule.class,
                UseCaseModule.class,
                DataModule.class,
                MappersModule.class,
                ServiceModule.class
        }
)

public interface ApplicationComponent extends ApplicationComponentInjects, ApplicationComponentExposes {

    final class Initializer {

        static public ApplicationComponent init(final SanqApplication sanqApplication) {
            return DaggerApplicationComponent.builder()
                                             .applicationModule(new ApplicationModule(sanqApplication))
                                             .threadingModule(new ThreadingModule())
                                             .utilsModule(new UtilsModule())
                                             .useCaseModule(new UseCaseModule())
                                             .dataModule(new DataModule(sanqApplication))
                                             .mappersModule(new MappersModule())
                                             .serviceModule(new ServiceModule())
                                             .build();
        }

        private Initializer() {
        }
    }
}
