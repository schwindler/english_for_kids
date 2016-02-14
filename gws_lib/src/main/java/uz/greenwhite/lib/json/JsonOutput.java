package uz.greenwhite.lib.json;


import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Map;

import uz.greenwhite.lib.collection.MyArray;
import uz.greenwhite.lib.json.android_json.JsonWriter;

public class JsonOutput {

    private final JsonWriter writer;

    public JsonOutput(JsonWriter writer) {
        this.writer = writer;
    }

    public JsonOutput beginArray() throws IOException {
        writer.beginArray();
        return this;
    }

    public JsonOutput endArray() throws IOException {
        writer.endArray();
        return this;
    }

    public JsonOutput beginObject() throws IOException {
        writer.beginObject();
        return this;
    }

    public JsonOutput endObject() throws IOException {
        writer.endObject();
        return this;
    }

    public JsonOutput name(String name) throws IOException {
        writer.name(name);
        return this;
    }

    public JsonOutput name(char name) throws IOException {
        writer.name(String.valueOf(name));
        return this;
    }

    public JsonOutput value(String value) throws IOException {
        if (value == null) {
            throw new NullPointerException("string value == null");
        }
        writer.value(value);
        return this;
    }

    public JsonOutput value(boolean value) throws IOException {
        writer.value(value ? "1" : "0");
        return this;
    }

    public JsonOutput value(int value) throws IOException {
        writer.value(String.valueOf(value));
        return this;
    }

    public JsonOutput value(long value) throws IOException {
        writer.value(String.valueOf(value));
        return this;
    }

    public JsonOutput value(BigDecimal value) throws IOException {
        if (value == null) {
            throw new NullPointerException("bigDecimal value == null");
        }
        writer.value(value.toPlainString());
        return this;
    }

    public <T> JsonOutput value(T value, JsonAdapter<T> adapter) throws IOException {
        if (value == null) {
            throw new NullPointerException("Object value == null");
        }
        adapter.write(this, value);
        return this;
    }

    public <T> JsonOutput value(MyArray<T> value, JsonAdapter<T> adapter) throws IOException {
        if (value == null) {
            throw new NullPointerException("MyArray value == null");
        }
        writer.beginArray();
        for (T v : value) {
            adapter.write(this, v);
        }
        writer.endArray();
        return this;
    }

    public void flush() throws IOException {
        writer.flush();
    }

    public void close() throws IOException {
        writer.close();
    }

    public static <V> String stringify(V val, JsonAdapter<V> adapter) {
        StringWriter s = new StringWriter();
        try {
            JsonOutput out = new JsonOutput(new JsonWriter(s));
            out.value(val, adapter);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return s.toString();
    }

}
