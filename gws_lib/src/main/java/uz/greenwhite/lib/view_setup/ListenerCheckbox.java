package uz.greenwhite.lib.view_setup;

import android.widget.CompoundButton;

import uz.greenwhite.lib.variable.ValueBoolean;

public class ListenerCheckbox implements CompoundButton.OnCheckedChangeListener {

    public final Model model;

    public ListenerCheckbox(Model model) {
        this.model = model;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ValueBoolean vb = (ValueBoolean) model.value;
        boolean old = vb.getValue();
        vb.setValue(isChecked);
        if (old != vb.getValue()) {
            model.notifyListeners();
        }
    }
}
