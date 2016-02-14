package uz.greenwhite.lib;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.text.Html;
import android.text.TextUtils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uz.greenwhite.lib.collection.MyArray;
import uz.greenwhite.lib.error.AppError;

public class Util {

    public static final ThreadLocal<SimpleDateFormat> DATE_FORMAT_AS_NUMBER = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd", Locale.US);
        }

        ;
    };

    public static final ThreadLocal<SimpleDateFormat> DATE_WEEK_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("EEEE, dd.MM.yyyy");
        }
    };

    public static final ThreadLocal<SimpleDateFormat> DATE_TIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
        }
    };

    public static final ThreadLocal<SimpleDateFormat> YYYYMMDDHHMMSS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        }
    };

    public static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        }
    };

    private static final ThreadLocal<DecimalFormat> MONEY_FORMAT = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            DecimalFormat df = new DecimalFormat();
            df.setMinimumFractionDigits(0);
            df.setMaximumFractionDigits(2);
            df.setGroupingUsed(true);
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
            return df;
        }
    };

    public static String formatMoney(BigDecimal amount) {
        return MONEY_FORMAT.get().format(amount.setScale(2, BigDecimal.ROUND_DOWN));
    }

    public static String dateFormatAsNumber(Date date) {
        try {
            return DATE_FORMAT_AS_NUMBER.get().format(date);
        } catch (Exception e) {
            throw new AppError(e);
        }
    }

    public static Date parseDate(String s) {
        if (s == null) {
            return null;
        }
        try {
            if (s.length() == 10) {
                return DATE_FORMAT.get().parse(s);
            } else if (s.length() == 8) {
                return DATE_FORMAT_AS_NUMBER.get().parse(s);
            } else {
                return DATE_TIME_FORMAT.get().parse(s);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date time format error:" + String.valueOf(s));
        }
    }

    public static Integer dateStringAsInteger(String s) {
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        try {
            Date d = Util.DATE_FORMAT.get().parse(s);
            String k = Util.DATE_FORMAT_AS_NUMBER.get().format(d);
            return Integer.parseInt(k);
        } catch (ParseException e) {
            throw new AppError(e);
        }

    }

    public static String dateIntegerAsString(Integer i) {
        if (i == null) {
            return "";
        }
        try {
            Date d = Util.DATE_FORMAT_AS_NUMBER.get().parse(i.toString());
            return Util.DATE_FORMAT.get().format(d);
        } catch (ParseException e) {
            throw new AppError(e);
        }
    }

    public static Integer parseInteger(String s) {
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        return Integer.parseInt(s);
    }


    public static <A> A nvl(A v, A d) {
        return v != null ? v : d;
    }

    public static <A> MyArray<A> nvl(MyArray<A> v) {
        return nvl(v, MyArray.<A>emptyArray());
    }

    public static String nvl(String v) {
        return nvl(v, "");
    }

    public static boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    final protected static char[] hexArray = "0123456789abcdef".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String calcSHA(byte b[]) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(b);
        return bytesToHex(md.digest());
    }

    public static CharSequence toRedText(CharSequence text) {
        return Html.fromHtml("<font color='red'>" + text + "</font>");
    }

    public static boolean deleteFolderRecursive(File path) {
        if (path.isDirectory()) {
            for (File file : path.listFiles()) {
                if (!deleteFolderRecursive(file)) {
                    return false;
                }
            }
        }
        return path.delete();
    }

    public static String extractStackTrace(Throwable ex) {
        StringWriter result = new StringWriter();
        PrintWriter writer = new PrintWriter(result);
        ex.printStackTrace(writer);
        writer.close();
        return result.toString();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return wifi.isConnected() || mobile.isConnected();
    }

    public static Drawable changeDrawableColor(Context ctx, @DrawableRes int resId, @ColorRes int resColorId) {
        if (ctx == null || resId == 0 || resColorId == 0) {
            return null;
        }
        Resources res = ctx.getResources();
        Drawable drawable = res.getDrawable(resId);
        drawable.setColorFilter(res.getColor(resColorId), PorterDuff.Mode.SRC_IN);
        return drawable;
    }


    public static String getFileSize(String path) {
        File file = new File(path);
        double length = file.length();//byte
        String sLength = "" + ((int) length) + " byte";
        if (length > 1024) {
            length = length / 1024;//kb
            if (length > 1024) {
                length = length / 1024;//mb
                String[] splits = ("" + length).split("\\.");
                String kb = splits[1].substring(0, 2);
                return splits[0] + ("00".equals(kb) ? "" : "." + kb) + " mb";
            }
            String[] split = ("" + length).split("\\.");
            String b = split[1].substring(0, 1);
            return split[0] + ("0".equals(b) ? "" : "." + b) + " kb";
        }
        return sLength;
    }

    public static boolean isExistFile(String serverPath, String fileName) {
        return new File(serverPath + "/" + fileName).exists();
    }

    public static String getMimiType(String type) {
        switch (type) {
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "mp4":
                return "video/mp4";
            case "mpeg":
                return "video/mpeg";
            case "ogg":
                return "video/ogg";
            case "quicktime":
                return "video/quicktime";
            case "gif":
                return "image/gif";
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "svg":
                return "image/svg+xml";
            default:
                return "application/" + type;
        }
    }

    public static boolean checkSelfPermissionGranted(Context ctx, String permission) {
        return ctx.checkPermission(permission, android.os.Process.myPid(), android.os.Process.myUid())
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Activity activity, String... permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permission, 0);
        }
    }

    public static Integer tryParseInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            return null;
        }
    }
}
