package uz.greenwhite.lib.mold;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uz.greenwhite.lib.Command;
import uz.greenwhite.lib.GWSLIB;

public abstract class MoldIndexFragment extends Fragment {

    List<MoldMenuContainer> mMenus;
    private boolean mFirstFormShowed;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (GWSLIB.DEBUG) {
            MoldActivity.debugLog("Index onActivityCreated: " + ((Object) this).getClass().getName());
        }
        mFirstFormShowed = false;
        mMenus = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        MoldActivity activity = (MoldActivity) getActivity();
        if (!(activity.isActivityRestarted || mFirstFormShowed)) {
            mFirstFormShowed = true;
            onShowForm();
        }
    }

    protected abstract void onShowForm();

    private List<MoldMenuContainer> getMenus() {
        if (mMenus == null) {
            mMenus = new ArrayList<MoldMenuContainer>();
        }
        return mMenus;
    }

    public void addMenu(int iconId, Command command) {
        getMenus().add(new MoldMenuContainer(iconId, command));
    }

    public void addMenu(View view) {
        getMenus().add(new MoldMenuContainer(view));
    }

    public CharSequence getActionBarTitle() {
        return null;
    }

    public int getActionBarLogo() {
        return 0;
    }

    public void onDrawerOpened() {

    }

    public void onDrawerClosed() {

    }

    public boolean onBackPressed() {
        return false;
    }

}
