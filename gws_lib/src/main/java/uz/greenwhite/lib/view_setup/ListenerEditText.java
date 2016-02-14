package uz.greenwhite.lib.view_setup;

import android.text.Editable;
import android.text.TextWatcher;

class ListenerEditText implements TextWatcher {

    public final Model model;

    ListenerEditText(Model model) {
        this.model = model;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        model.setValue(s.toString());
    }
}
