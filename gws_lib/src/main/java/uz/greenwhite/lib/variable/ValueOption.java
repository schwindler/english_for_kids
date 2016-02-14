package uz.greenwhite.lib.variable;

import uz.greenwhite.lib.error.AppError;

public class ValueOption<T extends Variable> implements Variable {

    public final String title;
    public final ValueBoolean checked = new ValueBoolean();
    public final T valueIfChecked;

    public ValueOption(String title, T value) {
        this.title = title;
        this.valueIfChecked = value;
        if (value == null) {
            throw AppError.NullPointer();
        }
    }

    public T getValue() {
        if (checked.getValue()) {
            return valueIfChecked;
        }
        return null;
    }

    @Override
    public void readyToChange() {
        checked.readyToChange();
        valueIfChecked.readyToChange();
    }

    @Override
    public boolean modified() {
        return checked.modified() || valueIfChecked.modified();
    }

    @Override
    public ErrorResult getError() {
        return valueIfChecked.getError();
    }
}
