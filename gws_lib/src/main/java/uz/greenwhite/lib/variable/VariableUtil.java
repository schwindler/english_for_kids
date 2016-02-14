package uz.greenwhite.lib.variable;

import uz.greenwhite.lib.collection.MyArray;

public class VariableUtil {

    public static void readyToChange(Variable... items) {
        for (Variable e : items) {
            if (e != null) {
                e.readyToChange();
            }
        }
    }

    public static boolean modified(Variable... items) {
        for (Variable e : items) {
            if (e != null && e.modified()) {
                return true;
            }
        }
        return false;
    }

    public static ErrorResult getError(Variable... items) {
        for (Variable e : items) {
            if (e != null) {
                ErrorResult r = e.getError();
                if (r.isError()) {
                    return r;
                }
            }
        }
        return ErrorResult.NONE;
    }

    public static <E extends Variable> void readyToChange(MyArray<E> items) {
        for (E e : items) {
            if (e != null) {
                e.readyToChange();
            }
        }
    }

    public static <E extends Variable> boolean modified(MyArray<E> items) {
        for (E e : items) {
            if (e != null && e.modified()) {
                return true;
            }
        }
        return false;
    }

    public static <E extends Variable> ErrorResult getError(MyArray<E> items) {
        for (E e : items) {
            if (e != null) {
                ErrorResult r = e.getError();
                if (r.isError()) {
                    return r;
                }
            }
        }
        return ErrorResult.NONE;
    }

}
