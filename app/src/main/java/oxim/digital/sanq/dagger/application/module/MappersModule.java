package oxim.digital.sanq.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.sanq.ui.model.mapper.FeedViewModeMapper;
import oxim.digital.sanq.ui.model.mapper.FeedViewModelMapperImpl;
import oxim.digital.sanq.util.DateUtils;

@Module
public final class MappersModule {

    @Provides
    @Singleton
    FeedViewModeMapper provideFeedViewModeMapper(final DateUtils dateUtils) {
        return new FeedViewModelMapperImpl(dateUtils);
    }

    public interface Exposes {

        FeedViewModeMapper feedViewModeMapper();
    }
}
