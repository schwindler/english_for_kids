package uz.greenwhite.lib.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import uz.greenwhite.lib.collection.MyArray;

public abstract class JsonAdapter<E> {

    public abstract E read(JsonInput in) throws IOException;

    public abstract void write(JsonOutput out, E val) throws IOException;

    public JsonAdapter<MyArray<E>> toArrayAdapter() {
        final JsonAdapter<E> that = this;
        return new JsonAdapter<MyArray<E>>() {

            @Override
            public MyArray<E> read(JsonInput in) throws IOException {
                return in.nextArray(that);
            }

            @Override
            public void write(JsonOutput out, MyArray<E> val) throws IOException {
                out.value(val, that);
            }
        };
    }

    public JsonAdapter<Map<String, E>> toMapAdapter() {
        final JsonAdapter<E> that = this;
        return new JsonAdapter<Map<String, E>>() {
            @Override
            public Map<String, E> read(JsonInput in) throws IOException {
                HashMap<String, E> r = new HashMap<String, E>();
                in.beginObject();
                while (in.hasNext()) {
                    r.put(in.nextName(), that.read(in));
                }
                in.endObject();
                return r;
            }

            @Override
            public void write(JsonOutput out, Map<String, E> val) throws IOException {
                out.beginObject();
                for (String k : val.keySet()) {
                    out.name(k).value(val.get(k), that);
                }
                out.endObject();
            }
        };
    }

    public static final String nvl(Integer val) {
        if (val != null) {
            return val.toString();
        } else {
            return "";
        }
    }


    public static final JsonAdapter<String> STRING_ADAPTER = new JsonAdapter<String>() {
        @Override
        public String read(JsonInput in) throws IOException {
            return in.nextString();
        }

        @Override
        public void write(JsonOutput out, String val) throws IOException {
            out.value(val);
        }
    };

    public static final JsonAdapter<Integer> INTEGER_ADAPTER = new JsonAdapter<Integer>() {
        @Override
        public Integer read(JsonInput in) throws IOException {
            return in.nextInt();
        }

        @Override
        public void write(JsonOutput out, Integer val) throws IOException {
            out.value(val);
        }
    };

    public static final JsonAdapter<Long> LONG_ADAPTER = new JsonAdapter<Long>() {
        @Override
        public Long read(JsonInput in) throws IOException {
            return in.nextLong();
        }

        @Override
        public void write(JsonOutput out, Long val) throws IOException {
            out.value(val);
        }
    };

}
