package oxim.digital.sanq.domain.interactor;

import io.reactivex.Flowable;

public interface QueryUseCase<Result> {

    Flowable<Result> execute();
}
