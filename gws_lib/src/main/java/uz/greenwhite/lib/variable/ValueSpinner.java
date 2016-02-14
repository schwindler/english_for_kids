package uz.greenwhite.lib.variable;

import android.text.TextUtils;

import uz.greenwhite.lib.collection.MyArray;
import uz.greenwhite.lib.collection.MyMapper;
import uz.greenwhite.lib.error.AppError;

public class ValueSpinner implements TextValue {

    public final MyArray<SpinnerOption> options;
    private SpinnerOption oldValue;
    private SpinnerOption value;

    public ValueSpinner(MyArray<SpinnerOption> options, SpinnerOption value) {
        if (value == null) {
            value = options.get(0);
        }
        checkValue(options, value);
        this.options = options;
        this.oldValue = value;
        this.value = value;
    }

    public ValueSpinner(MyArray<SpinnerOption> options) {
        this(options, null);
    }

    private static void checkValue(MyArray<SpinnerOption> options, SpinnerOption value) {
        if (value == null) {
            throw AppError.NullPointer();
        }
        options.checkUniqueness(SpinnerOption.KEY_ADAPTER);
        for (SpinnerOption o : options) {
            if (o == value) {
                return;
            }
        }
        throw new AppError("Given option value is not found in spinner options");
    }

    public int getPosition() {
        for (int i = 0; i < options.size(); i++) {
            if (value == options.get(i)) {
                return i;
            }
        }
        throw AppError.Unsupported();
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

    public SpinnerOption getValue() {
        return value;
    }

    public void setValue(SpinnerOption value) {
        checkValue(options, value);
        this.value = value;
    }

    @Override
    public String getText() {
        return value.code;
    }

    public Integer getValueCodeAsInt() {
        String code = value.code;
        if (TextUtils.isEmpty(code)) {
            return null;
        }
        return Integer.parseInt(value.code);
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(value.code);
    }

    public boolean nonEmpty() {
        return !isEmpty();
    }

    @Override
    public void setText(String text) {
        SpinnerOption o = options.find(text, SpinnerOption.KEY_ADAPTER);
        setValue(o);
    }

    public static <T> ValueSpinner make(MyArray<T> values, String selectedCode, MyMapper<T, SpinnerOption> toOption) {
        MyArray<SpinnerOption> options = values.map(toOption);

        SpinnerOption value = selectedCode == null ? null : options.find(selectedCode, SpinnerOption.KEY_ADAPTER);
        return new ValueSpinner(options, value);
    }

    public static <T> ValueSpinner make(MyArray<T> values, MyMapper<T, SpinnerOption> toOption) {
        return make(values, null, toOption);
    }
}
