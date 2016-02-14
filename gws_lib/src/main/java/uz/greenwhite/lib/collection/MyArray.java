package uz.greenwhite.lib.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class MyArray<E> implements Iterable<E> {

    private final Object[] data;
    private Map<Object, E> map;
    private MyMapper<?, ?> keyMap;

    private MyArray(Object[] data) {
        this.data = data;
    }

    public E get(int index) {
        return (E) this.data[index];
    }

    public CharSequence[] toCharSequenceArray() {
        CharSequence[] r = new CharSequence[data.length];
        for (int i = 0; i < data.length; i++) {
            r[i] = String.valueOf(data[i]);
        }
        return r;
    }

    public String mkString(String prefix, String infix, String suffix) {
        StringBuilder sb = new StringBuilder(prefix);

        switch (data.length) {
            case 0:
                break;
            case 1:
                sb.append(data[0]);
                break;
            default:
                sb.append(data[0]);
                for (int i = 1; i < data.length; i++) {
                    sb.append(infix);
                    sb.append(data[i]);
                }
                break;
        }

        sb.append(suffix);

        return sb.toString();
    }

    public String mkString(String infix) {
        return mkString("", infix, "");
    }

    private static final Object EMPTY = new MyArray<Object>(new Object[0]);

    public static <R> MyArray<R> emptyArray() {
        return (MyArray<R>) EMPTY;
    }

    private static <T> MyArray<T> fromPrivate(Object[] elements) {
        if (elements.length == 0) {
            return emptyArray();
        }
        return new MyArray<T>(elements);
    }

    public static <T> MyArray<T> from(T... elements) {
        return fromPrivate(elements);
    }

    public static <T> MyArray<T> from(List<T> elements) {
        return fromPrivate(elements.toArray());
    }

    public static <T> MyArray<T> from(Set<T> elements) {
        return fromPrivate(elements.toArray());
    }

    public static <T> MyArray<T> nvl(MyArray<T> arr) {
        if (arr == null) {
            return MyArray.emptyArray();
        }
        return arr;
    }

    public List<E> asList() {
        return Arrays.asList((E[]) data.clone());
    }

    public Set<E> asSet() {
        Set<E> r = new HashSet<E>();
        for (Object o : data) {
            r.add((E) o);
        }
        return r;
    }

    @Override
    public java.util.Iterator<E> iterator() {
        return new java.util.Iterator<E>() {

            int index = 0;

            @Override
            public boolean hasNext() {
                return index < data.length;
            }

            @Override
            public E next() {
                return (E) data[index++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove() method is not supported");
            }
        };
    }

    public <F> MyArray<F> toSuper() {
        return (MyArray<F>) this;
    }

    public MyArray<E> filter(MyPredicate<E> predicate) {
        if (data.length == 0) {
            return this;
        }
        java.util.ArrayList<E> r = new java.util.ArrayList<E>();
        for (int i = 0; i < data.length; i++) {
            E x = (E) data[i];
            if (predicate.apply(x)) {
                r.add(x);
            }
        }
        return from(r);
    }

    public boolean hasNull() {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                return true;
            }
        }
        return false;
    }

    public void checkNotNull() {
        if (hasNull()) {
            throw new RuntimeException("Array contains null element.");
        }
    }

    public MyArray<E> filterNotNull() {
        if (data.length == 0) {
            return this;
        }
        if (!hasNull()) {
            return this;
        }
        java.util.ArrayList<E> r = new java.util.ArrayList<E>();
        for (int i = 0; i < data.length; i++) {
            E x = (E) data[i];
            if (x != null) {
                r.add(x);
            }
        }
        return from(r);
    }

    public int findFirstPosition(MyPredicate<E> predicate) {
        for (int i = 0; i < data.length; i++) {
            if (predicate.apply((E) data[i])) {
                return i;
            }
        }
        return -1;
    }

    public <K> int findPosition(final K key, final MyMapper<E, K> mapper) {
        return findFirstPosition(new MyPredicate<E>() {
            @Override
            public boolean apply(E e) {
                return mapper.apply(e).equals(key);
            }
        });
    }

    public E findFirst(MyPredicate<E> predicate) {
        int pos = findFirstPosition(predicate);
        if (pos > -1) {
            return (E) data[pos];
        }
        return null;
    }

    public boolean contains(MyPredicate<E> predicate) {
        return findFirstPosition(predicate) > -1;
    }

    public <R> MyArray<R> map(MyMapper<E, R> mapper) {
        if (data.length == 0) {
            return emptyArray();
        }
        Object[] r = new Object[data.length];
        for (int i = 0; i < data.length; i++) {
            r[i] = mapper.apply((E) data[i]);
        }
        return fromPrivate(r);
    }

    public <R> MyArray<R> flatMap(MyFlatMapper<E, R> flatMapper) {
        if (data.length == 0) {
            return emptyArray();
        }
        List<R> r = new ArrayList<R>();
        for (int i = 0; i < data.length; i++) {
            E e = (E) data[i];
            MyArray<R> es = flatMapper.apply(e);
            for (R s : es) {
                r.add(s);
            }
        }
        return from(r);
    }

    private <K> void makeKeyMap(MyMapper<E, K> mapper) {
        if (map == null) {
            keyMap = mapper;
            map = new HashMap<Object, E>();
            for (int i = 0; i < data.length; i++) {
                E d = (E) data[i];
                Object key = mapper.apply(d);
                if (map.containsKey(key)) {
                    throw new RuntimeException("MyArray key duplicate found key adapter=" +
                            mapper.getClass().getName() + " key=" + key);
                } else {
                    map.put(key, d);
                }
            }
        }
    }

    public <K> E find(K key, MyMapper<E, K> mapper) {
        if (data.length == 0) {
            return null;
        }
        if (map == null) {
            makeKeyMap(mapper);
        }
        if (keyMap != mapper) {
            throw new IllegalArgumentException("Not proper key adapter used." +
                    keyMap.getClass().getName());
        }
        return map.get(key);
    }

    public <K> void checkUniqueness(MyMapper<E, K> mapper) {
        if (data.length == 0) {
            return;
        }
        if (map == null) {
            makeKeyMap(mapper);
        }
    }

    public <K> boolean contains(K key, MyMapper<E, K> mapper) {
        return find(key, mapper) != null;
    }

    public <A> A reduce(A acc, MyReducer<A, E> reducer) {
        for (int i = 0; i < data.length; i++) {
            acc = reducer.apply(acc, (E) data[i]);
        }
        return acc;
    }

    public MyArray<E> sort(java.util.Comparator<E> comparator) {
        E[] r = (E[]) new Object[data.length];
        System.arraycopy(data, 0, r, 0, data.length);
        java.util.Arrays.sort(r, comparator);
        return fromPrivate(r);
    }

    public boolean isEmpty() {
        return this.data.length == 0;
    }

    public boolean nonEmpty() {
        return !isEmpty();
    }

    public int size() {
        return data.length;
    }

    public MyArray<E> prepend(E element) {
        Object[] newData = new Object[data.length + 1];
        newData[0] = element;
        System.arraycopy(data, 0, newData, 1, data.length);
        return new MyArray<E>(newData);
    }

    public MyArray<E> append(E element) {
        Object[] newData = new Object[data.length + 1];
        System.arraycopy(data, 0, newData, 0, data.length);
        newData[data.length] = element;
        return new MyArray<E>(newData);
    }

    public MyArray<E> appendIf(E element, boolean state) {
        if (state) {
            return append(element);
        }
        return this;
    }

    public MyArray<E> append(MyArray<E> that) {
        if (this.isEmpty()) {
            return that;
        }
        if (that.isEmpty()) {
            return this;
        }
        Object[] newData = new Object[this.data.length + that.data.length];
        System.arraycopy(this.data, 0, newData, 0, this.data.length);
        System.arraycopy(that.data, 0, newData, this.data.length, that.data.length);
        return new MyArray<E>(newData);
    }

    public <K> MyArray<E> union(MyArray<E> that, MyMapper<E, K> keyAdapter) {
        List<E> result = null;
        for (Object o : that.data) {
            E el = (E) o;
            K key = keyAdapter.apply(el);
            if (!contains(key, keyAdapter)) {
                if (result == null) {
                    result = new ArrayList<E>(this.asList());
                }
                result.add(el);
            }
        }
        if (result == null) {
            return this;
        } else {
            return MyArray.from(result);
        }
    }

    public MyArray<E> union(MyArray<E> that) {
        return union(that, MyMapper.<E>identity());
    }

}