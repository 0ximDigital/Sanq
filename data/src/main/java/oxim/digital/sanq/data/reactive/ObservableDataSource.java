package oxim.digital.sanq.data.reactive;

public interface ObservableDataSource {

    void addObserver(DataSourceInvalidationObserver dataSourceObserver);

    void removeObserver(DataSourceInvalidationObserver dataSourceObserver);
}
