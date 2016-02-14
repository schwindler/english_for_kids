package uz.greenwhite.lib.variable;

public class ValueBoolean implements TextValue {

    private boolean oldValue;
    private boolean value;

    public ValueBoolean() {
        this.value = false;
    }

    public ValueBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public void readyToChange() {
        oldValue = value;
    }

    @Override
    public boolean modified() {
        return oldValue != value;
    }

    @Override
    public ErrorResult getError() {
        return ErrorResult.NONE;
    }

    @Override
    public String getText() {
        return value? "1" : "0";
    }

    @Override
    public void setText(String text) {
        value = "1".equals(text);
    }
}
