package oxim.digital.sanq.domain.interactor.type;

import io.reactivex.Completable;

public interface CommandUseCaseWithParams<Params> {

    Completable execute(Params params);
}
