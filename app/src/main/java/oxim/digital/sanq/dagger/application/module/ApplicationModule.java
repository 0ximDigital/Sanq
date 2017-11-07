package oxim.digital.sanq.dagger.application.module;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.sanq.dagger.application.ForApplication;
import oxim.digital.sanq.dagger.application.SanqApplication;

@Module
public final class ApplicationModule {

    private final SanqApplication sanqApplication;

    public ApplicationModule(final SanqApplication sanqApplication) {
        this.sanqApplication = sanqApplication;
    }

    @Provides
    @Singleton
    SanqApplication provideSanqApplication() {
        return sanqApplication;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideContext() {
        return sanqApplication;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return sanqApplication.getResources();
    }

    public interface Exposes {

        SanqApplication reedlyApplication();

        @ForApplication
        Context context();

        Resources resources();
    }
}
