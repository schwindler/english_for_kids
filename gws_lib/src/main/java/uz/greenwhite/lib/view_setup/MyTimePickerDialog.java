package uz.greenwhite.lib.view_setup;

import android.app.TimePickerDialog;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import uz.greenwhite.lib.error.AppError;

public class MyTimePickerDialog implements TimePickerDialog.OnTimeSetListener {

    public final EditText et;

    public MyTimePickerDialog(EditText et) {
        this.et = et;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        et.setText(format(hourOfDay, minute));
    }

    public static void show(EditText et) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String time = et.getText().toString();
        if (time.length() > 0) {
            hour = hourOfTime(time);
            minute = minuteOfTime(time);
        }

        TimePickerDialog tpd = new TimePickerDialog(et.getContext(), new MyTimePickerDialog(et),
                hour, minute, true);
        tpd.show();
    }


    private static String padTime(int time) {
        if (time >= 0) {
            String r = String.valueOf(time);
            switch (r.length()) {
                case 1:
                    return "0" + r;
                case 2:
                    return r;
            }
        }
        throw AppError.Unsupported();
    }

    public static String format(int hourOfDay, int minute) {
        return padTime(hourOfDay) + ":" + padTime(minute);
    }

    public static int hourOfTime(String time) {
        return Integer.parseInt(time.substring(0, 2));
    }

    public static int minuteOfTime(String time) {
        return Integer.parseInt(time.substring(3));
    }
}
