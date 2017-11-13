package oxim.digital.sanq.ui.feed.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.disposables.Disposables;
import oxim.digital.sanq.R;
import oxim.digital.sanq.base.BaseFragment;
import oxim.digital.sanq.base.ViewPresenter;
import oxim.digital.sanq.dagger.fragment.FragmentComponent;
import oxim.digital.sanq.util.ActionTextWatcher;

public final class NewFeedSubscriptionFragment extends BaseFragment implements NewFeedSubscriptionContract.View {

    public static final String TAG = NewFeedSubscriptionFragment.class.getSimpleName();

    @Inject
    NewFeedSubscriptionContract.Presenter presenter;

    @BindView(R.id.new_feed_subscription_content_container)
    CardView newFeedSubscriptionContentContainer;

    @BindView(R.id.dialog_background)
    View dialogBackground;

    @BindView(R.id.feed_url_input)
    TextInputEditText feedUrlInput;

    @BindView(R.id.message_text_view)
    TextView messageTextView;

    @BindView(R.id.loading_indicator)
    View loadingIndicator;

    @BindView(R.id.add_feed_button)
    Button addNewFeedButton;

    public static NewFeedSubscriptionFragment newInstance() {
        return new NewFeedSubscriptionFragment();
    }

    @Override
    protected void inject(final FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_new_feed_subscription;
    }

    @Override
    public ViewPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Need inspiration?
//        feedUrlInput.setText("https://xkcd.com/rss.xml");
//        feedUrlInput.setText("https://www.reddit.com/r/androiddev/new.rss");
//        feedUrlInput.setText("http://android-developers.blogspot.com/feeds/posts/default?alt=rss");

        observeViewState();
    }

    private void observeViewState() {
        addDisposable(presenter.viewState()
                               .subscribe(this::onViewState));
    }

    private void onViewState(final NewFeedViewState newFeedViewState) {
        showMessage(newFeedViewState.getMessage());
        showIsLoading(newFeedViewState.isLoading());
    }

    private void showMessage(final String message) {
        messageTextView.setText(message);
    }

    private void showIsLoading(final boolean isLoading) {
        messageTextView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        loadingIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        addNewFeedButton.setEnabled(!isLoading);
    }

    @Override
    public Flowable<NewFeedRequest> newFeedRequest() {
        return Flowable.create(this::notifyInputChanges, BackpressureStrategy.LATEST);
    }

    private void notifyInputChanges(final FlowableEmitter<NewFeedRequest> emitter) {
        final ActionTextWatcher actionTextWatcher = new ActionTextWatcher(changedText -> emitter.onNext(new NewFeedRequest(changedText.toString())));
        emitter.setDisposable(Disposables.fromAction(() -> feedUrlInput.removeTextChangedListener(actionTextWatcher)));
        feedUrlInput.addTextChangedListener(actionTextWatcher);
    }

    @OnClick(R.id.add_feed_button)
    public void onAddFeedButtonClick() {
        presenter.subscribeToFeed(feedUrlInput.getText().toString());
    }

    @OnClick(R.id.dialog_background)
    public void onDialogBackgroundClick() {
        presenter.back();
    }
}
