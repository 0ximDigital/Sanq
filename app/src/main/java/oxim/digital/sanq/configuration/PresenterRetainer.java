package oxim.digital.sanq.configuration;

import android.arch.lifecycle.ViewModel;

import com.annimon.stream.Optional;

import oxim.digital.sanq.base.ViewPresenter;

import static oxim.digital.sanq.domain.util.Conditions.throwIf;

public final class PresenterRetainer extends ViewModel {

    private Optional<ViewPresenter> viewPresenter = Optional.empty();
    private boolean isCleared;

    public void setViewPresenter(final ViewPresenter viewPresenter) {
        validateRetainer(viewPresenter);
        this.viewPresenter = Optional.of(viewPresenter);
    }

    private void validateRetainer(final ViewPresenter viewPresenter) {
        throwIf((isCleared || hasPresenter()), () -> new IllegalStateException("Presenter retainer already has presenter " + viewPresenter));
    }

    public boolean hasPresenter() {
        return viewPresenter.isPresent();
    }

    public Optional<ViewPresenter> getPresenter() {
        return viewPresenter;
    }

    @Override
    protected void onCleared() {
        isCleared = true;
        viewPresenter.ifPresent(ViewPresenter::destroy);
        viewPresenter = Optional.empty();
    }
}
