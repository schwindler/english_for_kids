package uz.greenwhite.lib.variable;

import android.text.TextUtils;

import java.math.BigDecimal;

public class ValueInteger implements TextValue, Quantity {

    private final ValueString value;

    public ValueInteger(int size) {
        this.value = new ValueString(size);
    }

    public ValueInteger(int size, String value) {
        this(size);
        this.setText(value);
    }

    public ValueInteger(int size, Integer value) {
        this(size);
        this.setValue(value);
    }

    public Integer getValue() {
        String r = value.getValue();
        if (TextUtils.isEmpty(r)) {
            return null;
        }
        return Integer.valueOf(r);
    }

    public void setValue(Integer value) {
        if (value == null) {
            this.value.setValue(null);
        } else
            this.value.setValue(value.toString());
    }


    @Override
    public BigDecimal getQuantity() {
        String r = value.getText();
        if (TextUtils.isEmpty(r)) {
            return null;
        }
        return new BigDecimal(r);
    }

    @Override
    public String getText() {
        return value.getText();
    }

    @Override
    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            this.value.setText("");
        } else {
            this.value.setText(text);
        }
    }

    @Override
    public void readyToChange() {
        this.value.readyToChange();
    }

    @Override
    public boolean modified() {
        return this.value.modified();
    }

    @Override
    public ErrorResult getError() {
        ErrorResult r = value.getError();
        if (r.isError()) {
            return r;
        }
        try {
            String q = value.getValue();
            if (!TextUtils.isEmpty(q)) {
                Integer.parseInt(q);
            }
        } catch (Exception ex) {
            return ErrorResult.make(ex);
        }
        return ErrorResult.NONE;
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public boolean nonEmpty() {
        return value.nonEmpty();
    }

}
