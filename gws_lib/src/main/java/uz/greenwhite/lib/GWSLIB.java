package uz.greenwhite.lib;

import android.util.Log;

public class GWSLIB {

    private static final String TAG = "GWS_LIB";
    public static boolean DEBUG = false;

    public static void log(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void log(String message, Object... args) {
        if (DEBUG) {
            Log.d(TAG, String.format(message, args));
        }
    }

    public static void log(Throwable ex) {
        if (DEBUG) {
            Log.d(TAG, ex.getMessage(), ex);
        }
    }
}
