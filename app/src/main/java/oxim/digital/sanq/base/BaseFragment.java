package oxim.digital.sanq.base;

import android.os.Bundle;

import oxim.digital.sanq.dagger.fragment.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment implements BaseView, BackPropagatingFragment {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().start();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().activate();
    }

    @Override
    public void onStop() {
        getPresenter().deactivate();
        super.onStop();
    }

    @Override
    public boolean onBack() {
        getPresenter().back();
        return true;
    }

    public abstract ScopedPresenter getPresenter();
}
