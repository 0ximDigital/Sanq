package oxim.digital.sanq.domain.interactor.type;

import io.reactivex.Flowable;

public interface QueryUseCaseWithRequest<Request, Result> {

    Flowable<Result> execute(Request request);
}
