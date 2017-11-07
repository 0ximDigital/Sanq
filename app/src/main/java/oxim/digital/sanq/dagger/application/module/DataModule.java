package oxim.digital.sanq.dagger.application.module;

import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.sanq.dagger.application.SanqApplication;
import oxim.digital.sanq.data.feed.FeedRepositoryImpl;
import oxim.digital.sanq.data.feed.converter.FeedModelConverterImpl;
import oxim.digital.sanq.data.feed.converter.ModelConverter;
import oxim.digital.sanq.data.feed.db.crudder.ArticleDataSource;
import oxim.digital.sanq.data.feed.db.crudder.FeedDataSource;
import oxim.digital.sanq.data.feed.db.dao.ArticleDao;
import oxim.digital.sanq.data.feed.db.dao.FeedDao;
import oxim.digital.sanq.data.feed.db.definition.FeedDatabase;
import oxim.digital.sanq.data.feed.service.FeedService;
import oxim.digital.sanq.data.feed.service.FeedServiceImpl;
import oxim.digital.sanq.data.feed.service.parser.FeedParser;
import oxim.digital.sanq.data.feed.service.parser.FeedParserImpl;
import oxim.digital.sanq.data.util.CurrentTimeProviderImpl;
import oxim.digital.sanq.data.util.FeedIdGenerator;
import oxim.digital.sanq.data.util.FeedIdGeneratorImpl;
import oxim.digital.sanq.domain.repository.FeedRepository;

@Module
public final class DataModule {

    private final SanqApplication sanqApplication;

    public DataModule(final SanqApplication sanqApplication) {
        this.sanqApplication = sanqApplication;
    }

    @Provides
    @Singleton
    FeedRepository provideFeedRepository(final ArticleDataSource articleDataSource, final FeedDataSource feedDataSource, final FeedService feedService,
                                         final FeedIdGenerator feedIdGenerator, final ModelConverter modelConverter) {
        return new FeedRepositoryImpl(articleDataSource, feedDataSource, feedService, modelConverter, feedIdGenerator);
    }

    @Provides
    @Singleton
    FeedDatabase provideFeedDatabase() {
        return Room.databaseBuilder(sanqApplication, FeedDatabase.class, FeedDatabase.NAME).build();
    }

    @Provides
    @Singleton
    ArticleDao provideArticleDao(final FeedDatabase feedDatabase) {
        return feedDatabase.articleDao();
    }

    @Provides
    @Singleton
    FeedDao provideFeedDao(final FeedDatabase feedDatabase) {
        return feedDatabase.feedDao();
    }

    @Provides
    @Singleton
    ArticleDataSource provideArticleDataSource(final ModelConverter modelConverter, final ArticleDao articleDao) {
        return new ArticleDataSource(articleDao, modelConverter);
    }

    @Provides
    @Singleton
    FeedDataSource provideFeedDataSource(final FeedDao feedDao, final ModelConverter modelConverter) {
        return new FeedDataSource(feedDao, modelConverter);
    }

    @Provides
    @Singleton
    FeedService provideFeedService(final FeedParser feedParser) {
        return new FeedServiceImpl(feedParser);
    }

    @Provides
    @Singleton
    FeedParser provideFeedParser() {
        return new FeedParserImpl(new CurrentTimeProviderImpl());
    }

    @Provides
    @Singleton
    ModelConverter provideModelConverter() {
        return new FeedModelConverterImpl();
    }

    @Provides
    @Singleton
    FeedIdGenerator provideFeedIdGenerator() {
        return new FeedIdGeneratorImpl();
    }

    public interface Exposes {

        FeedRepository feedRepository();
    }
}
