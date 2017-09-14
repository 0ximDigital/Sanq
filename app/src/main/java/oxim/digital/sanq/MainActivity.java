package oxim.digital.sanq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import oxim.digital.sanq.data.Storage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Storage storage = new StorageProviderImpl().getStorage();

        Log.w("WAT", "Storage -> " + storage);
    }
}
