package oxim.digital.sanq.dagger.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import oxim.digital.sanq.dagger.ComponentFactory;
import oxim.digital.sanq.dagger.application.SanqApplication;

public abstract class DaggerActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(getActivityComponent());
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = ComponentFactory.createActivityComponent(this, getReedlyApplication());
        }
        return activityComponent;
    }

    private SanqApplication getReedlyApplication() {
        return ((SanqApplication) getApplication());
    }

    protected abstract void inject(final ActivityComponent activityComponent);
}
