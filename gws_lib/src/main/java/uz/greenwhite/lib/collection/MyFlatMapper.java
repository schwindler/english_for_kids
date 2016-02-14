package uz.greenwhite.lib.collection;

public abstract class MyFlatMapper<E, R> {

    public abstract MyArray<R> apply(E element);

}
