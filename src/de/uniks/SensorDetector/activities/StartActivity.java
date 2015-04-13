package de.uniks.SensorDetector.activities;

import android.app.Activity;
import android.os.Bundle;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;
import de.uniks.SensorDetector.License;
import de.uniks.SensorDetector.R;

import java.io.IOException;

public class StartActivity extends Activity {
    private ArchitectView architectView;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.architectView = (ArchitectView)this.findViewById( R.id.architectView );
        final StartupConfiguration config = new StartupConfiguration( License.KEY );
        this.architectView.onCreate( config );
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        this.architectView.onPostCreate();
        try {
            this.architectView.load( "file:///android_asset/www/index.html" );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.architectView.onResume();
    }

    @Override
    protected void onPause() {
        super.onStop();
        this.architectView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.architectView.onDestroy();
    }
}