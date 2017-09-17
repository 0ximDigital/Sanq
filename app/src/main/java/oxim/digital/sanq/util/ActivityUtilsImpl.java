package oxim.digital.sanq.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Function;

import java.util.List;

import oxim.digital.sanq.base.BackPropagatingFragment;

public final class ActivityUtilsImpl implements ActivityUtils {

    /**
     * @return {@code true} is back is consumed, {@code false} otherwise
     */
    @Override
    public boolean propagateBackToTopFragment(@NonNull final FragmentManager fragmentManager) {
        return callIfPresent(findBackPropagatingFragment(fragmentManager), BackPropagatingFragment::onBack);
    }

    private boolean callIfPresent(final Optional<BackPropagatingFragment> backPropagatingFragmentOptional, final Function<BackPropagatingFragment, Boolean> function) {
        if (backPropagatingFragmentOptional.isPresent()) {
            final BackPropagatingFragment backPropagatingFragment = backPropagatingFragmentOptional.get();
            return function.apply(backPropagatingFragment);
        }
        return false;
    }

    private Optional<BackPropagatingFragment> findBackPropagatingFragment(final FragmentManager fragmentManager) {
        final List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.size() == 0) {
            return Optional.empty();
        }
        for (int i = fragments.size() - 1; i >= 0; i--) {
            final Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isVisible()) {
                if (fragment instanceof BackPropagatingFragment) {
                    return Optional.of(((BackPropagatingFragment) fragment));
                }
                break;
            }
        }
        return Optional.empty();
    }
}
