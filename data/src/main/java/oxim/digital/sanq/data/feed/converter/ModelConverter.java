package oxim.digital.sanq.data.feed.converter;

import oxim.digital.sanq.data.feed.db.model.ArticleModel;
import oxim.digital.sanq.data.feed.db.model.FeedModel;
import oxim.digital.sanq.data.feed.service.model.ApiArticle;
import oxim.digital.sanq.data.feed.service.model.ApiFeed;
import oxim.digital.sanq.domain.model.article.Article;
import oxim.digital.sanq.domain.model.feed.Feed;

public interface ModelConverter {

    Feed modelToDomain(FeedModel feedModel);

    Article modelToDomain(ArticleModel articleModel);

    Feed apiToDomain(ApiFeed apiFeed, String feedId);

    Article apiToDomain(ApiArticle apiArticle, String feedId);

    FeedModel domainToModel(Feed feed);

    ArticleModel domainToModel(Article article);
}
