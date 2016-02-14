package uz.greenwhite.lib.collection;

public abstract class MyPredicate<E> {

    public abstract boolean apply(E e);

    public MyPredicate<E> and(final MyPredicate<E> that) {
        final MyPredicate<E> self = this;
        return new MyPredicate<E>() {
            @Override
            public boolean apply(E element) {
                return self.apply(element) && that.apply(element);
            }
        };
    }

    public MyPredicate<E> or(final MyPredicate<E> that) {
        final MyPredicate<E> self = this;
        return new MyPredicate<E>() {
            @Override
            public boolean apply(E element) {
                return self.apply(element) || that.apply(element);
            }
        };
    }

    public MyPredicate<E> negate() {
        final MyPredicate<E> self = this;
        return new MyPredicate<E>() {
            @Override
            public boolean apply(E element) {
                return !self.apply(element);
            }
        };
    }

    public static <T> MyPredicate<T> and(MyPredicate<T> first, MyPredicate<T> second) {
        if (first != null && second != null) {
            return first.and(second);
        } else if (first != null) {
            return first;
        } else {
            return second;
        }
    }

    public static <T> MyPredicate<T> or(MyPredicate<T> first, MyPredicate<T> second) {
        if (first != null && second != null) {
            return first.and(second);
        } else if (first != null) {
            return first;
        } else {
            return second;
        }
    }

    public static <T> MyPredicate<T> negate(MyPredicate<T> predicate) {
        if (predicate != null) {
            return predicate.negate();
        }
        return null;
    }

    public static <T, E> MyPredicate<T> cover(final MyPredicate<E> predicate, final MyMapper<T, E> mapper) {
        if (predicate != null) {
            return new MyPredicate<T>() {
                @Override
                public boolean apply(T t) {
                    return predicate.apply(mapper.apply(t));
                }
            };
        }
        return null;
    }

    public static int compare(int lhs, int rhs) {
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    public static int compare(long lhs, long rhs) {
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    public static boolean equals(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

}
