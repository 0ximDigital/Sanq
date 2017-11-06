package oxim.digital.sanq.data.reactive;

public interface InvalidatingDataSource {

    void addObserver(DataSourceInvalidationObserver dataSourceObserver);

    void removeObserver(DataSourceInvalidationObserver dataSourceObserver);
}
