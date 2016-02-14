package uz.greenwhite.lib.collection;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import uz.greenwhite.lib.error.AppError;

public class MyMap<E> implements Map<String, E> {

    private Map<String, E> vals;

    public MyMap(Map<String, E> v) {
        vals = new HashMap<String, E>(v.size());
        vals.putAll(v);
    }

    @Override
    public void clear() {
        throw AppError.Unsupported();
    }

    @Override
    public boolean containsKey(Object key) {
        return vals.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return vals.containsValue(value);
    }

    @NonNull
    @Override
    public Set<Entry<String, E>> entrySet() {
        return vals.entrySet();
    }

    @Override
    public E get(Object key) {
        return vals.get(key);
    }

    @Override
    public boolean isEmpty() {
        return vals.isEmpty();
    }

    @NonNull
    @Override
    public Set<String> keySet() {
        return vals.keySet();
    }

    @Override
    public E put(String key, E value) {
        throw AppError.Unsupported();
    }

    @Override
    public void putAll(Map<? extends String, ? extends E> map) {
        throw AppError.Unsupported();
    }

    @Override
    public E remove(Object key) {
        throw AppError.Unsupported();
    }

    @Override
    public int size() {
        return vals.size();
    }

    @NonNull
    @Override
    public Collection<E> values() {
        return vals.values();
    }
}
