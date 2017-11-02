package oxim.digital.sanq.data.reactive;

public interface ObservableDataSource {

    public void addObserver(DataSourceInvalidationObserver dataSourceObserver);

    public void removeObserver(DataSourceInvalidationObserver dataSourceObserver);
}
