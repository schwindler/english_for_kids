package uz.greenwhite.lib;

import uz.greenwhite.lib.collection.MyPredicate;

public class Tuple2 {
    public final Object first;
    public final Object second;

    public Tuple2(Object first, Object second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tuple2)) {
            return false;
        }
        Tuple2 p = (Tuple2) o;
        return MyPredicate.equals(p.first, first) && MyPredicate.equals(p.second, second);
    }

    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
    }
}