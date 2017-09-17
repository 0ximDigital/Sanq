package oxim.digital.sanq.domain.interactor.type;

import io.reactivex.Flowable;

public interface QueryUseCase<Result> {

    Flowable<Result> execute();
}
