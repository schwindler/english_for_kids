package uz.greenwhite.lib.collection;

public abstract class MyReducer<A, E> {

    public abstract A apply(A a, E e);

}
