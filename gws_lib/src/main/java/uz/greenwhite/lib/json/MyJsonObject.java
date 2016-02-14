package uz.greenwhite.lib.json;

import org.json.JSONException;
import org.json.JSONObject;

public class MyJsonObject {

    public final JSONObject json;

    public MyJsonObject(JSONObject json) {
        this.json = json;
    }

    public MyJsonObject(String json) {
        try {
            this.json = new JSONObject(json);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public MyJsonObject getObject(String name) {
        try {
            return new MyJsonObject(json.getJSONObject(name));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public MyJsonArray getArray(String name) {
        try {
            return new MyJsonArray(json.getJSONArray(name));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getString(String name) {
        try {
            return json.getString(name);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public int getInt(String name) {
        try {
            return json.getInt(name);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public double getDouble(String name) {
        try {
            return json.getDouble(name);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean has(String name) {
        return json.has(name);
    }
}
