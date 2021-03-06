package prayashmishra.com.yahooweatherapp.data;

/**
 * Created by Pray on 2016-10-20.
 */
import org.json.JSONObject;

public class Channel implements JSONPopulator {

    private Item item;
    private Units units;

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    @Override
    public void populate(JSONObject data) {
        units = new Units();
        units.populate(data.optJSONObject("units"));
        item = new Item();
        item.populate(data.optJSONObject("item"));
    }
}
