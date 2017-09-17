package oxim.digital.sanq.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.sanq.data.feed.FeedRepositoryImpl;
import oxim.digital.sanq.domain.repository.FeedRepository;

@Module
public final class DataModule {

    @Provides
    @Singleton
    FeedRepository provideFeedRepository() {
        return new FeedRepositoryImpl();
    }

    public interface Exposes {

        FeedRepository feedRepository();
    }
}
