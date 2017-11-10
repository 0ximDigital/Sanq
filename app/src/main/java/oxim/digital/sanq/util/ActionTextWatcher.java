package oxim.digital.sanq.util;

import com.annimon.stream.function.Consumer;

public final class ActionTextWatcher extends TextWatcherAdapter {

    private final Consumer<CharSequence> onTextChangedAction;

    public ActionTextWatcher(final Consumer<CharSequence> onTextChangedAction) {
        this.onTextChangedAction = onTextChangedAction;
    }

    @Override
    public void onTextChanged(final CharSequence charSequence, final int start, final int before, final int count) {
        onTextChangedAction.accept(charSequence);
    }
}
