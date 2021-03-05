package Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Order extends Item {
    private String time;

    public Order(String name, String description, double price, String time) {
        super(name, description, price);
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", super.getName());
            obj.put("description", super.getDescription());
            obj.put("price", super.getPrice());
            obj.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
