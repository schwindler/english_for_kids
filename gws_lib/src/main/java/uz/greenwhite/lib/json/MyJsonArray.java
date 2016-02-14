package uz.greenwhite.lib.json;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import uz.greenwhite.lib.collection.MyArray;
import uz.greenwhite.lib.collection.MyMapper;

public class MyJsonArray {

    public final JSONArray json;

    public MyJsonArray(JSONArray json) {
        this.json = json;
    }

    public MyJsonArray(String json) {
        try {
            this.json = new JSONArray(json);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public MyJsonObject getObject(int i) {
        try {
            return new MyJsonObject(json.getJSONObject(i));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public MyJsonArray getArray(int i) {
        try {
            return new MyJsonArray(json.getJSONArray(i));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getString(int i) {
        try {
            return json.getString(i);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> MyArray<T> map(MyMapper<MyJsonObject, T> mapper) {
        List<T> r = new ArrayList<T>(json.length());
        for (int i = 0; i < json.length(); i++) {
            r.add(mapper.apply(getObject(i)));
        }
        return MyArray.from(r);
    }

    public MyArray<String> mapString() {
        List<String> r = new ArrayList<String>(json.length());
        for (int i = 0; i < json.length(); i++) {
            r.add(getString(i));
        }
        return MyArray.from(r);
    }

}
