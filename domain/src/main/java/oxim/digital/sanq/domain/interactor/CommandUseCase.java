package oxim.digital.sanq.domain.interactor;

import io.reactivex.Completable;

public interface CommandUseCase {

    Completable execute();
}
