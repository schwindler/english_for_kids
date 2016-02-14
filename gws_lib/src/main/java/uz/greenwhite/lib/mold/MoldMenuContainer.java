package uz.greenwhite.lib.mold;

import android.view.View;

import uz.greenwhite.lib.Command;

public class MoldMenuContainer {

    public final int iconResId;
    public final Command command;
    public final View view;
    int id = 0;

    public MoldMenuContainer(int iconResId, Command command) {
        this.iconResId = iconResId;
        this.command = command;
        this.view = null;
    }

    public MoldMenuContainer(View view) {
        this.iconResId = 0;
        this.command = null;
        this.view = view;
    }


}
