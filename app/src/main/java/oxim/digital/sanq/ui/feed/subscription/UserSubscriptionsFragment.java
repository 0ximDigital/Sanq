package oxim.digital.sanq.ui.feed.subscription;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;
import oxim.digital.sanq.R;
import oxim.digital.sanq.base.BaseFragment;
import oxim.digital.sanq.base.ScopedPresenter;
import oxim.digital.sanq.dagger.fragment.FragmentComponent;
import oxim.digital.sanq.data.feed.db.crudder.ArticleCrudder;
import oxim.digital.sanq.domain.model.Article;
import oxim.digital.sanq.ui.model.FeedViewModel;
import oxim.digital.sanq.util.ImageLoader;

public final class UserSubscriptionsFragment extends BaseFragment implements UserSubscriptionsContract.View {

    public static final String TAG = UserSubscriptionsFragment.class.getSimpleName();

    @Inject
    UserSubscriptionsContract.Presenter presenter;

    @Inject
    ArticleCrudder articleCrudder;

    @Inject
    ImageLoader imageLoader;

    @BindView(R.id.user_feeds_recycler_view)
    RecyclerView userFeedsRecyclerView;

    @BindView(R.id.empty_state_view)
    FrameLayout emptyStateView;

    private FeedAdapter feedAdapter;

    private Unbinder unbinder = Unbinder.EMPTY;

    private CompositeDisposable dataDisposables = new CompositeDisposable();

    public static UserSubscriptionsFragment newInstance() {
        return new UserSubscriptionsFragment();
    }

    @Override
    protected void inject(final FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public ScopedPresenter getPresenter() {
        return presenter;
    }

    private void onAllArticles(final List<Article> articles) {
        Log.i("WAT", "All articles -> " + String.valueOf(articles));
    }

    private void onFavouriteArticles(final List<Article> favouriteArticles) {
        Log.i("WAT", "Favourite articles -> " + String.valueOf(favouriteArticles));
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_subscriptions, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        feedAdapter = new FeedAdapter(imageLoader);
        feedAdapter.setHasStableIds(true);
        userFeedsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userFeedsRecyclerView.setAdapter(feedAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        observeArticles();
    }

    private Disposable firstFavDisposable = Disposables.disposed();
    private Disposable secondFavDisposable = Disposables.disposed();

    private void observeArticles() {
        dataDisposables.add(articleCrudder.getAllArticles()
                                          .subscribe(this::onAllArticles, Throwable::printStackTrace));

        firstFavDisposable = articleCrudder.getFavouriteArticles()
                                           .subscribe(this::onFavouriteArticles, Throwable::printStackTrace);

        secondFavDisposable = articleCrudder.getFavouriteArticles()
                                            .subscribe(this::onFavouriteArticles, Throwable::printStackTrace);
    }

    @Override
    public void onPause() {
        dataDisposables.clear();
        super.onPause();
    }

    @Override
    public void showFeedSubscriptions(final List<FeedViewModel> feedSubscriptions) {
        feedAdapter.onFeedsUpdate(feedSubscriptions);
        adjustEmptyState(feedSubscriptions.isEmpty());
    }

    private void adjustEmptyState(final boolean isViewEmpty) {
        emptyStateView.setVisibility(isViewEmpty ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.add_new_feed_button)
    public void onClick() {
        presenter.subscribeToTheNewFeed(UUID.randomUUID().toString());
    }

    @OnClick({R.id.test_insert_button, R.id.test_favourite_button, R.id.test_unfavourite_button})
    public void onTestClick(View view) {
        switch (view.getId()) {
            case R.id.test_insert_button:
                insertArticles();
                break;
            case R.id.test_favourite_button:
                favouriteArticle();
                break;
            case R.id.test_unfavourite_button:
                unfavouriteArticle();
                break;
        }
    }

    private void insertArticles() {

        if (!firstFavDisposable.isDisposed()) {
            Log.w("WAT", "Disposing first fav disposable");
            firstFavDisposable.dispose();
        } else if (!secondFavDisposable.isDisposed()) {
            Log.w("WAT", "Disposing second fav disposable");
            secondFavDisposable.dispose();
        } else {
            Log.w("WAT", "Creating first fav disposable");
            firstFavDisposable = articleCrudder.getFavouriteArticles()
                                               .subscribe(this::onFavouriteArticles, Throwable::printStackTrace);
        }
//        final ApiArticle article1 = new ApiArticle("article_1,", "link_1", 0);
//        final ApiArticle article2 = new ApiArticle("article_2,", "link_2", 0);
//        final ApiArticle article3 = new ApiArticle("article_3,", "link_3", 0);
//
//        articleCrudder.insertArticles(Arrays.asList(article1, article2, article3))
//                      .subscribeOn(Schedulers.io())
//                      .subscribe(() -> Log.w("WAT", "Done with insert"));

    }

    private void favouriteArticle() {
        articleCrudder.favouriteArticle(2)
                      .subscribeOn(Schedulers.io())
                      .subscribe(() -> {
                      }, Throwable::printStackTrace);
    }

    private void unfavouriteArticle() {
        articleCrudder.unfavouriteArticle(2)
                      .subscribeOn(Schedulers.io())
                      .subscribe(() -> {
                      }, Throwable::printStackTrace);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
