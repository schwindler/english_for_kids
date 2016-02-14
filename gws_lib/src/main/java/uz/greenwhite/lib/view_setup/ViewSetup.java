package uz.greenwhite.lib.view_setup;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import uz.greenwhite.lib.error.AppError;
import uz.greenwhite.lib.variable.TextValue;
import uz.greenwhite.lib.variable.ValueBoolean;
import uz.greenwhite.lib.variable.ValueSpinner;

public class ViewSetup {

    public final View view;
    private SparseArray<View> cachedViews = new SparseArray<View>();

    public ViewSetup(View parent) {
        this.view = parent;
    }

    public ViewSetup(LayoutInflater inflater, @Nullable ViewGroup container, int layoutId) {
        this.view = inflater.inflate(layoutId, container, false);
    }

    public ViewSetup(Context context, int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        this.view = inflater.inflate(layoutId, null);
    }

    public <T extends View> T id(int resId) {
        View v = cachedViews.get(resId);
        if (v == null) {
            v = view.findViewById(resId);
            if (v == null) {
                throw AppError.NullPointer();
            }
            cachedViews.put(resId, v);
        }
        return (T) v;
    }

    public TextView textView(int resId) {
        return id(resId);
    }

    public ImageView imageView(int resId) {
        return id(resId);
    }

    public EditText editText(int resId) {
        return id(resId);
    }

    public RatingBar ratingBar(int resId) {
        return id(resId);
    }

    public Button button(int resId) {
        return id(resId);
    }

    public CheckBox checkBox(int resId) {
        return id(resId);
    }

    public Spinner spinner(int resId) {
        return id(resId);
    }

    public void bind(int resId, TextValue value) {
        UI.bind(editText(resId), value);
    }

    public void bind(int resId, ValueBoolean value) {
        UI.bind(checkBox(resId), value);
    }

    public void bind(int resId, ValueSpinner value) {
        UI.bind(spinner(resId), value);
    }

    public Model model(int resId) {
        View v = id(resId);
        if (v instanceof EditText) {
            return UI.getModel((EditText) v);

        } else if (v instanceof CheckBox) {
            return UI.getModel((CheckBox) v);

        } else if (v instanceof Spinner) {
            return UI.getModel((Spinner) v);

        } else {
            return null;
        }
    }

    public void makeDatePicker(int resId) {
        UI.makeDatePicker(editText(resId));
    }

    public void makeDatePicker(int resId, boolean withClear) {
        UI.makeDatePicker(editText(resId), withClear);
    }

    public void makeTimePicker(int resId) {
        UI.makeTimePicker(editText(resId));
    }
}
