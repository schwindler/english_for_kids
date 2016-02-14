package uz.greenwhite.lib.view_setup;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import uz.greenwhite.lib.R;
import uz.greenwhite.lib.Util;

public class MyDatePickerDialog implements DatePickerDialog.OnDateSetListener {

    public final EditText et;

    public MyDatePickerDialog(EditText et) {
        this.et = et;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth, 0, 0);
        Date date = c.getTime();
        String result = Util.DATE_FORMAT.get().format(date);
        et.setText(result);
    }

    public static void show(final EditText editText, boolean withClear) {
        Calendar c = Calendar.getInstance();
        String s = editText.getText().toString();
        try {
            Date date = Util.DATE_FORMAT.get().parse(s);
            c.setTime(date);
        } catch (ParseException e) {
            c = Calendar.getInstance();
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        Context context = editText.getContext();
        DatePickerDialog dpd = new DatePickerDialog(context,
                new MyDatePickerDialog(editText), year, month, day);
        if (withClear) {
            dpd.setButton(DialogInterface.BUTTON_NEUTRAL, context.getString(R.string.clear),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editText.setText("");
                            dialog.dismiss();
                        }
                    });
        }
        dpd.show();
    }
}
