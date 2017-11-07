package oxim.digital.sanq.ui.feed.subscription;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

public final class SubscriptionViewModel extends ViewModel {

    @Override
    protected void onCleared() {
        Log.e("WAT", "cleared subscription view model");
        super.onCleared();
    }
}
