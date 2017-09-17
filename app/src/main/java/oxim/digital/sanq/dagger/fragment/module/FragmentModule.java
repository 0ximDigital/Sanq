package oxim.digital.sanq.dagger.fragment.module;

import dagger.Module;
import oxim.digital.sanq.dagger.fragment.DaggerFragment;

@Module
public class FragmentModule {

    private final DaggerFragment fragment;

    public FragmentModule(final DaggerFragment fragment) {
        this.fragment = fragment;
    }
}
