package uz.greenwhite.lib.view_setup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import uz.greenwhite.lib.Command;
import uz.greenwhite.lib.GWSLIB;
import uz.greenwhite.lib.R;
import uz.greenwhite.lib.Util;
import uz.greenwhite.lib.error.AppError;
import uz.greenwhite.lib.error.UserError;
import uz.greenwhite.lib.variable.TextValue;
import uz.greenwhite.lib.variable.ValueBoolean;
import uz.greenwhite.lib.variable.ValueSpinner;

public class UI {

    public static <T extends View> T id(View parent, int resId) {
        View v = parent.findViewById(resId);
        if (v == null) {
            throw AppError.NullPointer();
        }
        return (T) v;
    }

    public static DialogSetup dialog() {
        return new DialogSetup();
    }

    public static void alert(Activity activity, CharSequence title, CharSequence message) {
        dialog().title(title).message(message).show(activity);
    }

    public static void alertError(Activity activity, Throwable th) {
        if (GWSLIB.DEBUG && !(th instanceof UserError)) {
            Log.e("Smartup", String.valueOf(th), th);
        }
        if (activity == null) {
            throw AppError.NullPointer();
        }
        if (th == null) {
            throw AppError.NullPointer();
        }

        alert(activity, activity.getString(R.string.error), Util.toRedText(th.getMessage()));
    }

    public static void alertError(Activity activity, String message) {
        alert(activity, activity.getString(R.string.error), Util.toRedText(message));
    }

    public static void confirm(Activity activity, CharSequence title, CharSequence message,
                               Command command) {
        dialog().title(title).message(message).show(activity, command);
    }

    public static ShortHtml html() {
        return new ShortHtml();
    }

    public static void bind(EditText et, TextValue value) {
        ListenerEditText tag = null;
        if (et.getTag() instanceof ListenerEditText) {
            tag = (ListenerEditText) et.getTag();
            et.removeTextChangedListener(tag);
        }

        et.setText(value.getText());

        Model model = new Model(value);
        tag = new ListenerEditText(model);

        et.setTag(tag);
        et.addTextChangedListener(tag);
    }

    public static Model getModel(EditText et) {
        if (et.getTag() instanceof ListenerEditText) {
            ListenerEditText tag = (ListenerEditText) et.getTag();
            return tag.model;
        }
        return null;
    }

    public static void bind(CheckBox cb, ValueBoolean value) {
        cb.setOnCheckedChangeListener(null);
        cb.setTag(null);

        cb.setChecked(value.getValue());

        Model model = new Model(value);
        cb.setTag(model);
        cb.setOnCheckedChangeListener(new ListenerCheckbox(model));
    }

    public static Model getModel(CheckBox cb) {
        if (cb.getTag() instanceof Model) {
            return (Model) cb.getTag();
        }
        return null;
    }

    public static void bind(Spinner sp, ValueSpinner value) {
        sp.setAdapter(new MySpinnerAdapter(sp.getContext(), value.options));

        sp.setOnItemSelectedListener(null);

        sp.setSelection(value.getPosition());

        sp.setOnItemSelectedListener(new ListenerSpinner(new Model(value)));
    }

    public static Model getModel(Spinner sp) {
        AdapterView.OnItemSelectedListener onItemSelectedListener = sp.getOnItemSelectedListener();
        if (onItemSelectedListener instanceof ListenerSpinner) {
            return ((ListenerSpinner) onItemSelectedListener).model;
        }
        return null;
    }

    public static void setDialogPositiveClickWithoutDismiss(final AlertDialog d, final View.OnClickListener onClick) {
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(onClick);
            }
        });
    }

    public static void makeDatePicker(final EditText et, final boolean withClear) {
        et.setOnLongClickListener(null);
        et.setKeyListener(null);
        et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gwslib_datepicker, 0);
        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    MyDatePickerDialog.show(et, withClear);
                }
                return false;
            }
        });
    }

    public static void makeDatePicker(EditText et) {
        makeDatePicker(et, false);
    }

    public static void makeTimePicker(final EditText et) {
        et.setOnLongClickListener(null);
        et.setKeyListener(null);
        et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gwslib_datepicker, 0);
        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    MyTimePickerDialog.show(et);
                }
                return false;
            }
        });
    }

}
