package oxim.digital.sanq.ui.feed.subscription;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import oxim.digital.sanq.R;
import oxim.digital.sanq.base.BaseFragment;
import oxim.digital.sanq.base.ViewPresenter;
import oxim.digital.sanq.dagger.fragment.FragmentComponent;
import oxim.digital.sanq.ui.model.FeedViewModel;
import oxim.digital.sanq.util.ImageLoader;

public final class UserSubscriptionsFragment extends BaseFragment implements UserSubscriptionsContract.View {

    public static final String TAG = UserSubscriptionsFragment.class.getSimpleName();

    @Inject
    UserSubscriptionsContract.Presenter presenter;

    @Inject
    ImageLoader imageLoader;

    @BindView(R.id.user_feeds_recycler_view)
    RecyclerView userFeedsRecyclerView;

    @BindView(R.id.empty_state_view)
    FrameLayout emptyStateView;

    @BindView(R.id.loading_indicator)
    View loadingIndicator;

    private FeedAdapter feedAdapter;

    private CompositeDisposable dataDisposables = new CompositeDisposable();

    public static UserSubscriptionsFragment newInstance() {
        return new UserSubscriptionsFragment();
    }

    @Override
    protected void inject(final FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public ViewPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_user_subscriptions;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
        observeViewState();
    }

    private void observeViewState() {
        addDisposable(presenter.viewState()
                               .subscribe(this::onViewState));
    }

    private void onViewState(final UserSubscriptionsViewState userSubscriptionsViewState) {
        showIsLoading(userSubscriptionsViewState.isLoading());
        showFeedSubscriptions(userSubscriptionsViewState.getFeedViewModels());
    }

    private void showIsLoading(final boolean isLoading) {
        loadingIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void initializeRecyclerView() {
        feedAdapter = new FeedAdapter(imageLoader);
        feedAdapter.setHasStableIds(true);
        userFeedsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userFeedsRecyclerView.setAdapter(feedAdapter);
    }

    @Override
    public void onPause() {
        dataDisposables.clear();
        super.onPause();
    }

    public void showFeedSubscriptions(final List<FeedViewModel> feedSubscriptions) {
        feedAdapter.onFeedsUpdate(feedSubscriptions);
        adjustEmptyState(feedSubscriptions.isEmpty());
    }

    private void adjustEmptyState(final boolean isViewEmpty) {
        emptyStateView.setVisibility(isViewEmpty ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.add_new_feed_button)
    public void onClick() {
        presenter.showNewFeedSubscriptionScreen();
    }
}
