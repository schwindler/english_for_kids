package uz.greenwhite.lib.mold;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class ActionBarInfo implements Parcelable {

    public final CharSequence title;
    public final int logo;

    public ActionBarInfo(CharSequence title, int logo) {
        this.title = title;
        this.logo = logo;
    }

    public ActionBarInfo(CharSequence title) {
        this(title, 0);
    }

    public ActionBarInfo(Parcel in) {
        this(TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in), in.readInt());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        TextUtils.writeToParcel(title, dest, flags);
        dest.writeInt(logo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionBarInfo> CREATOR = new Creator<ActionBarInfo>() {
        @Override
        public ActionBarInfo createFromParcel(Parcel source) {
            return new ActionBarInfo(source);
        }

        @Override
        public ActionBarInfo[] newArray(int size) {
            return new ActionBarInfo[size];
        }
    };

    static final String ACTION_BAR_INFO = "__action_bar_info__";

    public static ActionBarInfo extractActionBarInfo(MoldContentFragment content) {
        Bundle arguments = content.getArguments();
        if (arguments != null) {
            return arguments.getParcelable(ACTION_BAR_INFO);
        }
        return null;
    }

}
