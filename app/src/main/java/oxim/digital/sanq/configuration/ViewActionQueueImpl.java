package oxim.digital.sanq.configuration;

import java.util.Iterator;
import java.util.LinkedList;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.PublishProcessor;

// TODO - Lifecycle component ?
public final class ViewActionQueueImpl<View> implements ViewActionQueue<View> {

    private final LinkedList<Consumer<View>> viewActions = new LinkedList<>();
    private final Object queueLock = new Object();

    private final PublishProcessor<Consumer<View>> viewActionsProcessor = PublishProcessor.create();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private boolean isPaused = true;

    @Override
    public void resume() {
        isPaused = false;
        consumeQueue();
    }

    private void consumeQueue() {
        synchronized (queueLock) {
            final Iterator<Consumer<View>> consumersIterator = viewActions.iterator();
            while (consumersIterator.hasNext()) {
                final Consumer<View> pendingViewAction = consumersIterator.next();
                viewActionsProcessor.onNext(pendingViewAction);
                consumersIterator.remove();
            }
        }
    }

    @Override
    public void enqueueViewAction(final Consumer<View> viewConsumer) {
        if (isPaused) {
            synchronized (queueLock) {
                viewActions.add(viewConsumer);
            }
        } else {
            viewActionsProcessor.onNext(viewConsumer);
        }
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void destroy() {
        disposables.clear();
        viewActionsProcessor.onComplete();
    }

    @Override
    public Flowable<Consumer<View>> viewActions() {
        return viewActionsProcessor;
    }
}
