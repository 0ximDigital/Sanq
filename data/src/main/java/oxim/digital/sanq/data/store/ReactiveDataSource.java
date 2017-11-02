package oxim.digital.sanq.data.store;

import com.annimon.stream.function.Function;

import java.util.List;

import io.reactivex.Flowable;

public interface ReactiveDataSource<Id, Model, Source> {

    void store(Model model);

    void storeAll(List<Model> models);

    void replaceAll(List<Model> models);

    void queryModel(Function<Source, Model> queryFunction);

    void queryModels(Function<Source, List<Model>> queryFunction);

    Flowable<Model> get(Id id);

    Flowable<List<Model>> getAll();
}
