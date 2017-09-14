package oxim.digital.sanq;

import oxim.digital.sanq.data.Storage;
import oxim.digital.sanq.data.StorageProvider;

public final class StorageProviderImpl implements StorageProvider {

    @Override
    public Storage getStorage() {
        return new RoomStorage();
    }
}
