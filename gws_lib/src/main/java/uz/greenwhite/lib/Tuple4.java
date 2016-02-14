package uz.greenwhite.lib;

public class Tuple4 {
    public final Object first;
    public final Object second;
    public final Object third;
    public final Object fourth;

    public Tuple4(Object first, Object second, Object third, Object fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple4 tuple4 = (Tuple4) o;

        if (first != null ? !first.equals(tuple4.first) : tuple4.first != null) return false;
        if (fourth != null ? !fourth.equals(tuple4.fourth) : tuple4.fourth != null) return false;
        if (second != null ? !second.equals(tuple4.second) : tuple4.second != null) return false;
        if (third != null ? !third.equals(tuple4.third) : tuple4.third != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        result = 31 * result + (third != null ? third.hashCode() : 0);
        result = 31 * result + (fourth != null ? fourth.hashCode() : 0);
        return result;
    }
}
