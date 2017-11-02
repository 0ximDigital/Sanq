package oxim.digital.sanq.data.feed.converter;

import oxim.digital.sanq.data.feed.db.model.ArticleModel;
import oxim.digital.sanq.data.feed.db.model.FeedModel;
import oxim.digital.sanq.data.feed.service.model.ApiArticle;
import oxim.digital.sanq.data.feed.service.model.ApiFeed;
import oxim.digital.sanq.domain.model.Article;
import oxim.digital.sanq.domain.model.Feed;

public interface ModelConverter {

    Feed modelToDomain(FeedModel feedModel);

    Article modelToDomain(ArticleModel articleModel);

    FeedModel apiToModel(ApiFeed apiFeed);

    ArticleModel apiToModel(ApiArticle apiArticle, int feedId);
}
