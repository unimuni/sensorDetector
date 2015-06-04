package de.uniks.SensorDetector.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
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

        String configJson = configService.loadConfigJSON(getApplicationContext());
        List<ConfigEntry> configEntries = configService.getAllConfigEntries(configJson);

        List<String> values = new ArrayList<>();
        for(ConfigEntry entry : configEntries) {
            values.add(entry.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);

        final ListView listView = (ListView) findViewById(R.id.config_list_config);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;
                String  itemValue = (String) listView.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
