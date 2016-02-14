package uz.greenwhite.lib.view_setup;

import android.view.View;
import android.widget.AdapterView;

import uz.greenwhite.lib.variable.SpinnerOption;

public class ListenerSpinner implements AdapterView.OnItemSelectedListener {

    public final Model model;

    public ListenerSpinner(Model model) {
        this.model = model;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SpinnerOption option = (SpinnerOption) parent.getItemAtPosition(position);
        model.setValue(option.code);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
