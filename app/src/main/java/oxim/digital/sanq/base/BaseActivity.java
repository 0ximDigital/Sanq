package oxim.digital.sanq.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import oxim.digital.sanq.dagger.activity.DaggerActivity;
import oxim.digital.sanq.util.ActivityUtils;

public abstract class BaseActivity extends DaggerActivity {

    @Inject
    protected FragmentManager fragmentManager;

    @Inject
    ActivityUtils activityUtils;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().start();
    }

    @Override
    public void onBackPressed() {
        if (!activityUtils.propagateBackToTopFragment(fragmentManager)) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        getPresenter().destroy();
        super.onDestroy();
    }

    protected abstract ScopedPresenter getPresenter();
}
