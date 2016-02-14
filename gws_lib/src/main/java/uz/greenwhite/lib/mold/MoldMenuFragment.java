package uz.greenwhite.lib.mold;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import uz.greenwhite.lib.GWSLIB;

public abstract class MoldMenuFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (GWSLIB.DEBUG) {
            MoldActivity.debugLog("Menu onActivityCreated: " + ((Object) this).getClass().getName());
        }
        MoldActivity activity = (MoldActivity) getActivity();
        activity.onMenuActivityCreated(this);
    }

    public void onDrawerOpened() {

    }

    public void onDrawerClosed() {

    }

}
