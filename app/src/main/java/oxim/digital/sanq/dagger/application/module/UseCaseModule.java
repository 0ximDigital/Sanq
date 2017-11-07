package oxim.digital.sanq.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.sanq.domain.interactor.feed.GetUserSubscriptionFeedsUseCase;
import oxim.digital.sanq.domain.interactor.feed.SubscribeUserToFeedUseCase;
import oxim.digital.sanq.domain.repository.FeedRepository;

@Module
public final class UseCaseModule {

    @Provides
    @Singleton
    GetUserSubscriptionFeedsUseCase provideGetUserSubscriptionFeedsUseCase(final FeedRepository feedRepository) {
        return new GetUserSubscriptionFeedsUseCase(feedRepository);
    }

    @Provides
    @Singleton
    SubscribeUserToFeedUseCase provideSubscribeUserToFeedUseCase(final FeedRepository feedRepository) {
        return new SubscribeUserToFeedUseCase(feedRepository);
    }

    public interface Exposes {

        GetUserSubscriptionFeedsUseCase getUserSubscriptionFeedsUseCase();

        SubscribeUserToFeedUseCase subscribeUserToFeedUseCase();
    }
}
