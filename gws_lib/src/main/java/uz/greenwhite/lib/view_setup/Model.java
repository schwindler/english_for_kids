package uz.greenwhite.lib.view_setup;

import java.util.ArrayList;
import java.util.List;

import uz.greenwhite.lib.variable.TextValue;

public class Model {

    public final TextValue value;

    private List<ModelChange> changeListeneres;

    public Model(TextValue value) {
        this.value = value;
    }

    public Model notifyListeners() {
        if (changeListeneres != null) {
            for (ModelChange ch : changeListeneres) {
                ch.onChange();
            }
        }
        return this;
    }

    public Model setValue(String text) {
        String old = value.getText();
        value.setText(text);
        if (!old.equals(value.getText())) {
            notifyListeners();
        }
        return this;
    }

    public Model add(ModelChange onChange) {
        if (changeListeneres == null) {
            changeListeneres = new ArrayList<ModelChange>();
        }
        for (ModelChange ch : changeListeneres) {
            if (ch == onChange) {
                return this;
            }
        }
        changeListeneres.add(onChange);
        return this;
    }

    public Model remove(ModelChange onChange) {
        if (changeListeneres == null) {
            return this;
        }
        changeListeneres.remove(onChange);
        return this;
    }

}
