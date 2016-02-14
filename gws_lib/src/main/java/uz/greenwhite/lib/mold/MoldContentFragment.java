package uz.greenwhite.lib.mold;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uz.greenwhite.lib.Command;
import uz.greenwhite.lib.GWSLIB;
import uz.greenwhite.lib.R;
import uz.greenwhite.lib.collection.MyArray;

public abstract class MoldContentFragment extends Fragment {

    MoldSearchQuery searchQuery;
    List<MoldMenuContainer> mMenus;
    int searchQueryIcon;
    int rightDrawerIcon;

    private static final String ARG_FRAGMENT_DATA = "uz.greenwhite.smartup.agent4.fragment_data";
    private Parcelable mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mData = savedInstanceState.getParcelable(ARG_FRAGMENT_DATA);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mData != null)
            outState.putParcelable(ARG_FRAGMENT_DATA, mData);
    }

    public <T extends Parcelable> T manageFragmentData(Parcelable data) {
        if (mData == null) {
            mData = data;
        }
        return (T) mData;
    }

    public void setFragmentData(Parcelable data) {
        this.mData = data;
    }

    public <T extends Parcelable> T getFragmentData() {
        return (T) mData;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (GWSLIB.DEBUG) {
            MoldActivity.debugLog("Content onActivityCreated: " + ((Object) this).getClass().getName());
        }
        mMenus = null;
        MoldActivity activity = (MoldActivity) getActivity();
        activity.onContentActivityCreated(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        MoldActivity activity = (MoldActivity) getActivity();
        activity.onContentStarted(this);
    }

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

    public void putRightDrawerToggleMenu(int iconResId) {
        this.rightDrawerIcon = iconResId;
    }

    public void putRightDrawerToggleMenu() {
        putRightDrawerToggleMenu(R.drawable.ic_menu_white_24dp);
    }

    public void setSearchMenu(MoldSearchQuery searchQuery) {
        this.searchQueryIcon = R.drawable.ic_search_white_24dp;
        this.searchQuery = searchQuery;
    }

    public void setSearchMenu(int iconResId, MoldSearchQuery searchQuery) {
        this.searchQueryIcon = iconResId;
        this.searchQuery = searchQuery;
    }

    public MoldMenuFragment getMenuFragment() {
        return null;
    }

    public void reloadContent() {

    }

    public void onAboveContentPopped(Object result) {

    }

    public MyArray<MoldMenuContainer> joinWithIndexMenus(MyArray<MoldMenuContainer> indexMenus,
                                                         MyArray<MoldMenuContainer> contentMenus) {
        return indexMenus.append(contentMenus);
    }


}
