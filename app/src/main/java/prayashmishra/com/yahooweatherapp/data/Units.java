package prayashmishra.com.yahooweatherapp.data;

/**
 * Created by Pray on 2016-10-20.
 */

import org.json.JSONObject;

public class Units implements JSONPopulator {

    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");


    }
}
