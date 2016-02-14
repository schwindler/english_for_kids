package uz.greenwhite.lib.json;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uz.greenwhite.lib.collection.MyArray;
import uz.greenwhite.lib.json.android_json.JsonReader;
import uz.greenwhite.lib.json.android_json.MalformedJsonException;

public class JsonInput {

    private final JsonReader reader;

    public JsonInput(JsonReader reader) {
        this.reader = reader;
    }

    public void beginArray() throws IOException {
        reader.beginArray();
    }

    public void endArray() throws IOException {
        reader.endArray();
    }

    public void beginObject() throws IOException {
        reader.beginObject();
    }

    public void endObject() throws IOException {
        reader.endObject();
    }

    public boolean hasNext() throws IOException {
        return reader.hasNext();
    }

    public String nextName() throws IOException {
        return reader.nextName();
    }

    public char nextNameChar() throws IOException {
        String s = reader.nextName();
        if (s.length() == 1) {
            return s.charAt(0);
        } else {
            throw new MalformedJsonException("nextNameChar: got more chars then expected name=" + s);
        }
    }

    public String nextString() throws IOException {
        return reader.nextString();
    }

    public boolean nextBoolean() throws IOException {
        return "1".equals(reader.nextString());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(reader.nextString());
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(reader.nextString());
    }

    public Integer nextInteger() throws IOException {
        String s = reader.nextString();
        if (s.length() == 0) {
            return null;
        } else {
            return new Integer(s);
        }
    }

    public BigDecimal nextBigDecimal() throws IOException {
        return new BigDecimal(reader.nextString());
    }

    public <T> T nextObject(JsonAdapter<T> adapter) throws IOException {
        return adapter.read(this);
    }

    public <T> MyArray<T> nextArray(JsonAdapter<T> adapter) throws IOException {
        List<T> r = new LinkedList<T>();
        reader.beginArray();
        while (reader.hasNext()) {
            r.add(adapter.read(this));
        }
        reader.endArray();
        return MyArray.from(r);
    }

    public void skipValue() throws IOException {
        reader.skipValue();
    }

    public void close() throws IOException {
        reader.close();
    }

    @Override
    public String toString() {
        return reader.toString();
    }

    public static <V> V parse(String json, JsonAdapter<V> adapter) {
        try {
            JsonInput in = new JsonInput(new JsonReader(new StringReader(json)));
            V r = adapter.read(in);
            in.close();
            return r;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
