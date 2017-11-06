package oxim.digital.sanq.dagger.application.module;

import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.sanq.dagger.application.SanqApplication;
import oxim.digital.sanq.data.feed.FeedRepositoryImpl;
import oxim.digital.sanq.data.feed.converter.FeedModelConverterImpl;
import oxim.digital.sanq.data.feed.converter.ModelConverter;
import oxim.digital.sanq.data.feed.db.crudder.ArticleCrudder;
import oxim.digital.sanq.data.feed.db.dao.ArticleDao;
import oxim.digital.sanq.data.feed.db.definition.FeedDatabase;
import oxim.digital.sanq.domain.repository.FeedRepository;

@Module
public final class DataModule {

    private final SanqApplication sanqApplication;

    public DataModule(final SanqApplication sanqApplication) {
        this.sanqApplication = sanqApplication;
    }

    @Provides
    @Singleton
    FeedRepository provideFeedRepository() {
        return new FeedRepositoryImpl();
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
    ArticleCrudder provideArticleCrudder(final ModelConverter modelConverter, final ArticleDao articleDao) {
        return new ArticleCrudder(articleDao, modelConverter);
    }

    @Provides
    @Singleton
    ModelConverter provideModelConverter() {
        return new FeedModelConverterImpl();
    }

    public interface Exposes {

        FeedRepository feedRepository();

        FeedDatabase feedDatabase();

        ArticleCrudder articleCrudder();
    }
}
