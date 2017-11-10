package oxim.digital.sanq.configuration;

import android.arch.lifecycle.ViewModel;

import com.annimon.stream.Optional;

import oxim.digital.sanq.base.ViewPresenter;

public final class PresenterRetainer extends ViewModel {

    private Optional<ViewPresenter> viewPresenter = Optional.empty();

    public void setViewPresenter(final ViewPresenter viewPresenter) {
        this.viewPresenter.ifPresent(this::notifyError);
        this.viewPresenter = Optional.of(viewPresenter);
    }

    public boolean hasPresenter() {
        return viewPresenter.isPresent();
    }

    public Optional<ViewPresenter> getPresenter() {
        return viewPresenter;
    }

    @Override
    protected void onCleared() {
        viewPresenter.ifPresent(ViewPresenter::destroy);
    }

    private void notifyError(final ViewPresenter viewPresenter) {
        throw new IllegalStateException("Presenter retainer already has presenter " + viewPresenter);
    }
}
