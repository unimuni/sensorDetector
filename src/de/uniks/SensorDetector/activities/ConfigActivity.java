package de.uniks.SensorDetector.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.*;
import de.uniks.SensorDetector.R;
import de.uniks.SensorDetector.domain.ConfigEntry;
import de.uniks.SensorDetector.service.ConfigService;

import java.util.ArrayList;
import java.util.List;

public class ConfigActivity extends Activity {

    private ConfigService configService = new ConfigService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configview);

        final Context applicationContext = getApplicationContext();
        String configJson = configService.loadConfigJSON(applicationContext);
        List<ConfigEntry> configEntries = configService.getAllConfigEntries(configJson);

        List<String> values = new ArrayList<>();
        for(ConfigEntry entry : configEntries) {
            values.add(entry.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);

        final ListView listView = (ListView) findViewById(R.id.config_list_config);
        listView.setAdapter(adapter);

        final ConfigActivity configActivity = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;
                String  sensorName = (String) listView.getItemAtPosition(position);

                ConfigEntry configEntry = configService.getConfigBySensorName(sensorName, applicationContext);

                String sensor = configEntry.getName();
                String url = configEntry.getUrl();

                final SpannableString message = new SpannableString(sensor + "\n" + url);
                Linkify.addLinks(message, Linkify.ALL);

                final AlertDialog d = new AlertDialog.Builder(configActivity)
                        .setPositiveButton(android.R.string.ok, null)
                        .setTitle("Sensorinformation")
                        .setMessage(message)
                        .create();

                d.show();
                ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

            }
        });
    }
}
