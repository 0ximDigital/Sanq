package oxim.digital.sanq.ui.feed.subscription;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public final class HeavyObject {

    private final byte[] junk;

    private Disposable disposable = Disposables.disposed();

    public HeavyObject() {
        this.junk = takeSpace();
    }

    private byte[] takeSpace() {
        return new byte[1024 * 1024 * 5];   // 5 MB
    }

    public void attach(final Observable<Object> flowable) {
        disposable = flowable.subscribe(this::onNext, this::onError, this::onCompleted);
    }

    public void clear() {
        disposable.dispose();
    }

    private void onNext(final Object o) {
        // Ignore
    }

    private void onError(final Throwable throwable) {
        // Ignore
    }

    private void onCompleted() {
        // Ignore
    }
}
