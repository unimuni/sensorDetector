package de.uniks.SensorDetector.service;

import android.content.Context;
import de.uniks.SensorDetector.domain.ConfigEntry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ConfigService {

    public static final String CONFIG_FILE_LOCATION = "www/config.json";

    public String loadConfigJSON(Context context) {
        String json = "";
        try {
            InputStream is = context.getAssets().open(CONFIG_FILE_LOCATION);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    public List<ConfigEntry> getAllConfigEntries(String configJson) {

        List<ConfigEntry> list = new ArrayList<ConfigEntry>();
        try {
            JSONArray data = new JSONArray(configJson);

            for (int i = 0; i < data.length(); i++) {
                JSONObject entry = data.getJSONObject(i);
                ConfigEntry configEntry = createConfigEntry(entry);
                list.add(configEntry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private ConfigEntry createConfigEntry(JSONObject entry) throws JSONException {
        String name = entry.getString("name");
        String url = entry.getString("url");

        ConfigEntry configEntry = new ConfigEntry();
        configEntry.setName(name);
        configEntry.setUrl(url);

        return configEntry;
    }

    public ConfigEntry getConfigBySensorName(String sensorname, Context context) {

        if(sensorname == null || context == null) {
            return null;
        }

        String json = loadConfigJSON(context);
        List<ConfigEntry> configEntryList = getAllConfigEntries(json);
        for(ConfigEntry entry : configEntryList) {

            if(entry != null && sensorname.equals(entry.getName()) ) {
                return entry;
            }
        }
        return null;
    }
}
