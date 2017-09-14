package oxim.digital.sanq;

import android.app.Application;

import io.reactivex.Flowable;
import oxim.digital.sanq.data.Storage;

public final class RealmStorage implements Storage {

    @Override
    public void initialize(final Application application) {

    }

    @Override
    public Flowable<Object> query() {
        return null;
    }
}
