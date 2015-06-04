package de.uniks.SensorDetector.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        Button btnConfig = (Button) findViewById(R.id.main_btn_config);
        btnConfig.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(StartActivity.this, ConfigActivity.class);
                startActivity(in);
            }
        });

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
            //this.architectView.load( "http://10.10.158.124" );
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