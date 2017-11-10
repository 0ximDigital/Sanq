package oxim.digital.sanq.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import oxim.digital.sanq.dagger.fragment.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment implements BaseView, BackPropagatingFragment {

    private CompositeDisposable disposables = new CompositeDisposable();

    private Unbinder unbinder = Unbinder.EMPTY;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResourceId(), container, false);
    }

    @LayoutRes
    protected abstract int getLayoutResourceId();

    @Override
    @CallSuper
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        getPresenter().onViewAttached(this);
    }

    protected void addDisposable(final Disposable disposable) {
        disposables.add(disposable);
    }

    @Override
    public void onDestroyView() {
        getPresenter().onViewDetached();
        disposables.clear();
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public boolean onBack() {
        getPresenter().back();
        return true;
    }

    public abstract ViewPresenter getPresenter();
}
