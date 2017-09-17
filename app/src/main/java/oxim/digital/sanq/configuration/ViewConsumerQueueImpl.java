package oxim.digital.sanq.configuration;

import java.util.Iterator;
import java.util.LinkedList;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.PublishProcessor;

public final class ViewConsumerQueueImpl<View> implements ViewConsumerQueue<View> {

    private final LinkedList<Consumer<View>> viewConsumers = new LinkedList<>();
    private final Object queueLock = new Object();

    private final PublishProcessor<Consumer<View>> viewConsumerProcessor = PublishProcessor.create();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private boolean isPaused = true;

    @Override
    public void resume() {
        isPaused = false;
        consumeQueue();
    }

    private void consumeQueue() {
        synchronized (queueLock) {
            final Iterator<Consumer<View>> consumersIterator = viewConsumers.iterator();
            while (consumersIterator.hasNext()) {
                final Consumer<View> pendingViewAction = consumersIterator.next();
                viewConsumerProcessor.onNext(pendingViewAction);
                consumersIterator.remove();
            }
        }
    }

    @Override
    public void enqueueViewConsumer(final Consumer<View> viewConsumer) {
        if (isPaused) {
            synchronized (queueLock) {
                viewConsumers.add(viewConsumer);
            }
        } else {
            viewConsumerProcessor.onNext(viewConsumer);
        }
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void destroy() {
        disposables.clear();
        viewConsumerProcessor.onComplete();
    }

    @Override
    public Flowable<Consumer<View>> viewConsumersFlowable() {
        return viewConsumerProcessor;
    }
}
