package oxim.digital.sanq.data;

import android.app.Application;

import io.reactivex.Flowable;

public interface Storage {

    void initialize(Application application);

    Flowable<Object> query();
}
