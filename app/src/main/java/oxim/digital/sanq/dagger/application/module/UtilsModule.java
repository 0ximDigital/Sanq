package oxim.digital.sanq.dagger.application.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.sanq.dagger.application.ForApplication;
import oxim.digital.sanq.util.ActivityUtils;
import oxim.digital.sanq.util.ActivityUtilsImpl;
import oxim.digital.sanq.util.DateUtils;
import oxim.digital.sanq.util.DateUtilsImpl;
import oxim.digital.sanq.util.ImageLoader;
import oxim.digital.sanq.util.ImageLoaderImpl;

@Module
public final class UtilsModule {

    @Provides
    @Singleton
    ActivityUtils provideActivityUtils() {
        return new ActivityUtilsImpl();
    }

    @Provides
    @Singleton
    DateUtils provideDateUtils() {
        return new DateUtilsImpl();
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader(final @ForApplication Context context) {
        return new ImageLoaderImpl(context);
    }

    public interface Exposes {

        ActivityUtils activityUtils();

        DateUtils dateUtils();

        ImageLoader imageLoader();
    }
}
