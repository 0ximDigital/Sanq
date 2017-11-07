package oxim.digital.sanq.ui.feed.subscription;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import oxim.digital.sanq.R;
import oxim.digital.sanq.base.BaseFragment;
import oxim.digital.sanq.base.ScopedPresenter;
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
    public void onPause() {
        dataDisposables.clear();
        super.onPause();
    }

    @Override
    public void showFeedSubscriptions(final List<FeedViewModel> feedSubscriptions) {
        feedAdapter.onFeedsUpdate(feedSubscriptions);
        adjustEmptyState(feedSubscriptions.isEmpty());
    }

    @Override
    public void showMessage(final String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void adjustEmptyState(final boolean isViewEmpty) {
        emptyStateView.setVisibility(isViewEmpty ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.add_new_feed_button)
    public void onClick() {
        presenter.subscribeToTheNewFeed("https://xkcd.com/rss.xml");
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
