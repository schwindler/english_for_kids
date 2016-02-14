package uz.greenwhite.lib.variable;

import android.text.TextUtils;

import java.util.Comparator;

import uz.greenwhite.lib.collection.MyMapper;
import uz.greenwhite.lib.error.AppError;

public class SpinnerOption {

    public final String code;
    public final CharSequence name;
    public final Object tag;

    public SpinnerOption(String code, CharSequence name, Object tag) {
        this.code = code;
        this.name = name;
        this.tag = tag;

        if (code == null) {
            throw AppError.NullPointer();
        }
        if (name == null) {
            throw AppError.NullPointer();
        }
    }

    public SpinnerOption(int id, String name, Object tag) {
        this(String.valueOf(id), name, tag);
    }

    public SpinnerOption(String code, CharSequence name) {
        this(code, name, null);
    }

    public SpinnerOption(int id, CharSequence name) {
        this(String.valueOf(id), name);
    }

    public Integer getId() {
        if (TextUtils.isEmpty(code)) {
            return null;
        }
        return Integer.parseInt(code);
    }

    public static final SpinnerOption EMPTY = new SpinnerOption("", "");

    public static final Comparator<SpinnerOption> SORT_BY_NAME = new Comparator<SpinnerOption>() {
        @Override
        public int compare(SpinnerOption lhs, SpinnerOption rhs) {
            return lhs.name.toString().compareToIgnoreCase(rhs.name.toString());
        }
    };

    public static final MyMapper<SpinnerOption, String> KEY_ADAPTER =
            new MyMapper<SpinnerOption, String>() {
                @Override
                public String apply(SpinnerOption val) {
                    return val.code;
                }
            };

}
