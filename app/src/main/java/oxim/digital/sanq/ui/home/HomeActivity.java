package oxim.digital.sanq.ui.home;

import android.os.Bundle;

import javax.inject.Inject;

import oxim.digital.sanq.R;
import oxim.digital.sanq.base.BaseActivity;
import oxim.digital.sanq.base.ViewPresenter;
import oxim.digital.sanq.dagger.activity.ActivityComponent;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    @Inject
    HomeContract.Presenter presenter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected ViewPresenter getPresenter() {
        return presenter;
    }
}
