package oxim.digital.sanq.data.store;

import com.annimon.stream.function.Function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

// TODO - add abstract
public class BaseReactiveDataSource<Id, Model, Source> implements ReactiveDataSource<Id, Model, Source> {

    private final Source source;

    private final Function<Model, Id> idForModel;

    private final FlowableProcessor<List<Model>> allProcessor;

    private final Map<Id, FlowableProcessor<Model>> processorsMap = new HashMap<>();

    public BaseReactiveDataSource(final Source source, final Function<Model, Id> idForModel) {
        this.source = source;
        this.allProcessor = PublishProcessor.<List<Model>> create().toSerialized();
        this.idForModel = idForModel;
    }

    @Override
    public void store(final Model model) {

    }

    @Override
    public void storeAll(final List<Model> models) {

    }

    @Override
    public void replaceAll(final List<Model> models) {

    }

    @Override
    public void queryModel(final Function<Source, Model> queryFunction) {
        final Model model = queryFunction.apply(source);
        // Notify processors
    }

    @Override
    public void queryModels(final Function<Source, List<Model>> queryFunction) {
        final List<Model> models = queryFunction.apply(source);
        // Notify processors
    }

    @Override
    public Flowable<Model> get(final Id id) {
        return null;
    }

    @Override
    public Flowable<List<Model>> getAll() {
        return allProcessor;
    }
}
