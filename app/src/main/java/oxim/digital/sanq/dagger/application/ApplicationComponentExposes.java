package oxim.digital.sanq.dagger.application;

import oxim.digital.sanq.dagger.application.module.ApplicationModule;
import oxim.digital.sanq.dagger.application.module.DataModule;
import oxim.digital.sanq.dagger.application.module.MappersModule;
import oxim.digital.sanq.dagger.application.module.ServiceModule;
import oxim.digital.sanq.dagger.application.module.ThreadingModule;
import oxim.digital.sanq.dagger.application.module.UseCaseModule;
import oxim.digital.sanq.dagger.application.module.UtilsModule;

public interface ApplicationComponentExposes extends ApplicationModule.Exposes,
                                                     ThreadingModule.Exposes,
                                                     UtilsModule.Exposes,
                                                     UseCaseModule.Exposes,
                                                     DataModule.Exposes,
                                                     MappersModule.Exposes,
                                                     ServiceModule.Exposes {

}
