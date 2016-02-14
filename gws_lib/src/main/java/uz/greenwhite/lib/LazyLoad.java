package uz.greenwhite.lib;

public abstract class LazyLoad<A> {

    private A val;

    public final A get() {
        if (val == null) {
            val = load();
        }
        return val;
    }

    protected abstract A load();

}
