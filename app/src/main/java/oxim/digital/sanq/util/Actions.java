package oxim.digital.sanq.util;

import io.reactivex.functions.Consumer;

public final class Actions {

    public static final Consumer NO_OP_CONSUMER_1 = (o) -> {
    };

    @SuppressWarnings("unchecked")
    public static <T> Consumer<T> noOpConsumer() {
        return (Consumer<T>) NO_OP_CONSUMER_1;
    }
}
