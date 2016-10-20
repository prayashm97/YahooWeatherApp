package prayashmishra.com.yahooweatherapp.data;

/**
 * Created by Pray on 2016-10-20.
 */
import org.json.JSONObject;

public class Item implements JSONPopulator {

    public Condition getCondition() {
        return condition;
    }

    private Condition condition;

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
