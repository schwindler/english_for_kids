package uz.greenwhite.lib.variable;

import android.text.TextUtils;

import java.math.BigDecimal;

public class ValueBigDecimal implements TextValue, Quantity {

    private int precision;
    private int scale;
    private ValueString value;
    private BigDecimal cache;

    public ValueBigDecimal(int precision, int scale) {
        this.precision = precision;
        this.scale = scale;
        this.value = new ValueString(100);
    }

    public BigDecimal getValue() {
        if (cache == null) {
            String s = value.getValue();
            if (!TextUtils.isEmpty(s)) {
                try {
                    cache = new BigDecimal(s);
                } catch (Exception ex) {

                }
            }
        }
        return cache;
    }

    public void setValue(BigDecimal value) {
        this.cache = null;
        String v = "";
        if (value != null) {
            v = value.toPlainString();
        }
        this.value.setValue(v);
    }

    @Override
    public void readyToChange() {
        value.readyToChange();
    }

    @Override
    public boolean modified() {
        return value.modified();
    }

    @Override
    public ErrorResult getError() {
        BigDecimal v = getValue();
        if (value.nonEmpty() && v == null) {
            return ErrorResult.make("Entered values is not number.");
        }
        if (v != null && (v.precision() > precision || v.scale() > scale)) {
            return ErrorResult.make("Number is not suitable for precision or scale " + precision + "," + scale);
        }
        return ErrorResult.NONE;
    }

    @Override
    public String getText() {
        return value.getText();
    }

    @Override
    public void setText(String text) {
        this.cache = null;
        value.setText(text);
    }

    @Override
    public BigDecimal getQuantity() {
        BigDecimal v = getValue();
        if (v == null) {
            return BigDecimal.ZERO;
        }
        return v;
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public boolean nonEmpty() {
        return value.nonEmpty();
    }

    public boolean isZero() {
        if (isEmpty()) {
            return true;
        }
        BigDecimal v = getValue();
        if (v == null) {
            return true;
        }
        return v.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean nonZero() {
        return !isZero();
    }
}
