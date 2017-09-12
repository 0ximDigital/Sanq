package oxim.digital.sanq.domain.interactor;

import io.reactivex.Flowable;

public interface QueryUseCase<T> {

    Flowable<T> execute();
}
