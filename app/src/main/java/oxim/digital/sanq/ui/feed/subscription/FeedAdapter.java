package oxim.digital.sanq.ui.feed.subscription;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import oxim.digital.sanq.R;
import oxim.digital.sanq.ui.model.FeedViewModel;
import oxim.digital.sanq.util.ImageLoader;

public final class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private final ImageLoader imageLoader;

    private List<FeedViewModel> feedViewModels = new ArrayList<>();

    public FeedAdapter(final ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.feed_list_item, parent, false);
        return new FeedViewHolder(itemView, imageLoader);
    }

    @Override
    public void onBindViewHolder(final FeedViewHolder holder, final int position) {
        holder.setItem(feedViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return feedViewModels.size();
    }

    public void onFeedsUpdate(final List<FeedViewModel> feedViewModels) {
        this.feedViewModels = feedViewModels;
        notifyDataSetChanged();
    }

    static final class FeedViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.feed_image)
        ImageView feedImage;

        @Bind(R.id.feed_title)
        TextView feedTitle;

        @Bind(R.id.feed_description)
        TextView feedDescription;

        private final ImageLoader imageLoader;

        public FeedViewHolder(final View itemView, final ImageLoader imageLoader) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.imageLoader = imageLoader;
        }

        public void setItem(final FeedViewModel feedViewModel) {
            imageLoader.loadImage(feedViewModel.imageUrl, feedImage, R.drawable.feed_icon, R.drawable.feed_icon);
            feedTitle.setText(feedViewModel.title);
            feedDescription.setText(feedViewModel.description);
        }
    }
}
