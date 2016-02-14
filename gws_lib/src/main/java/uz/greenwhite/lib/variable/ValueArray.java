package uz.greenwhite.lib.variable;

import uz.greenwhite.lib.collection.MyArray;
import uz.greenwhite.lib.collection.MyPredicate;
import uz.greenwhite.lib.error.AppError;

public class ValueArray<E extends Variable> implements Variable {

    private MyArray<E> items;
    private boolean changed = false;

    public ValueArray() {
        this.items = MyArray.emptyArray();
    }

    public ValueArray(MyArray<E> items) {
        this.items = items;
        if (this.items == null) {
            throw AppError.NullPointer();
        }
    }

    public MyArray<E> getItems() {
        return items;
    }

    public void append(E item) {
        this.items = items.append(item);
        this.changed = true;
    }

    public void delete(final E item) {
        this.items = items.filter(new MyPredicate<E>() {
            @Override
            public boolean apply(E e) {
                return e != item;
            }
        });
        this.changed = true;
    }

    @Override
    public void readyToChange() {
        changed = false;
        VariableUtil.readyToChange(items);
    }

    @Override
    public boolean modified() {
        if (changed) {
            return true;
        }
        return VariableUtil.modified(items);
    }

    @Override
    public ErrorResult getError() {
        return VariableUtil.getError(items);
    }

}
