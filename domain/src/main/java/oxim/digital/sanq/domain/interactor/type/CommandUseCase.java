package oxim.digital.sanq.domain.interactor.type;

import io.reactivex.Completable;

public interface CommandUseCase {

    Completable execute();
}
