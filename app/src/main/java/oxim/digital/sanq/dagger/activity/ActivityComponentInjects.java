package oxim.digital.sanq.dagger.activity;

import oxim.digital.sanq.ui.home.HomeActivity;
import oxim.digital.sanq.ui.home.HomePresenter;

public interface ActivityComponentInjects {

    void inject(HomeActivity activity);
    void inject(HomePresenter presenter);
}
