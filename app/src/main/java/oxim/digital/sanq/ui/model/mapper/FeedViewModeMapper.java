package oxim.digital.sanq.ui.model.mapper;

import java.util.List;

import oxim.digital.sanq.domain.model.article.Article;
import oxim.digital.sanq.domain.model.feed.Feed;
import oxim.digital.sanq.ui.model.ArticleViewModel;
import oxim.digital.sanq.ui.model.FeedViewModel;

public interface FeedViewModeMapper {

    FeedViewModel mapFeedToViewModel(Feed feed);

    List<FeedViewModel> mapFeedsToViewModels(List<Feed> feeds);

    ArticleViewModel mapArticleToViewModel(Article article);

    List<ArticleViewModel> mapArticlesToViewModels(List<Article> articles);
}
