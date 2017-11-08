package oxim.digital.sanq.base;

import android.os.Bundle;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import oxim.digital.sanq.dagger.fragment.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment implements BaseView, BackPropagatingFragment {

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().start();
    }

    protected void addDisposable(final Disposable disposable) {
        disposables.add(disposable);
    }

    @Override
    public void onDestroyView() {
        disposables.clear();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        getPresenter().destroy();
        super.onDestroy();
    }

    @Override
    public boolean onBack() {
        getPresenter().back();
        return true;
    }

    public abstract ScopedPresenter getPresenter();
}
