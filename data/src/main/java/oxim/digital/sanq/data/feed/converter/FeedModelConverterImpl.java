package oxim.digital.sanq.data.feed.converter;

import oxim.digital.sanq.data.feed.db.model.ArticleModel;
import oxim.digital.sanq.data.feed.db.model.FeedModel;
import oxim.digital.sanq.data.feed.service.model.ApiArticle;
import oxim.digital.sanq.data.feed.service.model.ApiFeed;
import oxim.digital.sanq.domain.model.Article;
import oxim.digital.sanq.domain.model.Feed;

public final class FeedModelConverterImpl implements ModelConverter {

    private static final String FORWARD_SLASH = "/";

    @Override
    public Feed modelToDomain(final FeedModel model) {
        return new Feed(model.getId(), model.getTitle(), model.getImageUrl(), model.getPageLink(), model.getDescription(), model.getUrl());
    }

    @Override
    public Article modelToDomain(final ArticleModel model) {
        return new Article(model.getId(), model.getFeedId(), model.getTitle(), model.getLink(), model.getPublicationDate(), model.isNew(), model.isFavourite());
    }

    @Override
    public FeedModel apiToModel(final ApiFeed apiFeed) {
        return new FeedModel(apiFeed.title, clearImageUrl(apiFeed.imageUrl), apiFeed.pageLink, apiFeed.description, apiFeed.url);
    }

    @Override
    public ArticleModel apiToModel(final ApiArticle apiArticle, final int feedId) {
        return new ArticleModel(feedId, apiArticle.title, apiArticle.link, apiArticle.publicationDate);
    }

    private String clearImageUrl(final String imageUrl) {
        if (imageUrl != null && imageUrl.endsWith(FORWARD_SLASH)) {
            return imageUrl.substring(0, imageUrl.length() - 1);
        }
        return imageUrl;
    }
}
