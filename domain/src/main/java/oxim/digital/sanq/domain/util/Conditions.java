package oxim.digital.sanq.domain.util;

import com.annimon.stream.function.Supplier;

public final class Conditions {

    public static void throwIf(final boolean condition, final Supplier<RuntimeException> exceptionSupplier) {
        if (condition) {
            throw exceptionSupplier.get();
        }
    }
}
