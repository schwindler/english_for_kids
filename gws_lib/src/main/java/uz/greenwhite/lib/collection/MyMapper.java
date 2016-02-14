package uz.greenwhite.lib.collection;

public abstract class MyMapper<E, R> {
    public abstract R apply(E e);

    public static final <E> MyMapper<E, String> string() {
        return (MyMapper<E, String>) TO_STRING;
    }

    public static final <E> MyMapper<E, E> identity() {
        return (MyMapper<E, E>) IDENTITY;
    }

    private static final MyMapper<Object, String> TO_STRING = new MyMapper<Object, String>() {
        @Override
        public String apply(Object o) {
            return o.toString();
        }
    };

    private static final MyMapper<Object, Object> IDENTITY = new MyMapper<Object, Object>() {
        @Override
        public Object apply(Object o) {
            return o;
        }
    };
}
