package uz.greenwhite.lib.mold;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import uz.greenwhite.lib.GWSLIB;
import uz.greenwhite.lib.R;
import uz.greenwhite.lib.Util;
import uz.greenwhite.lib.collection.MyArray;
import uz.greenwhite.lib.error.AppError;

public class MoldActivity extends AppCompatActivity {

    private static final String ARG_CLASS = "uz.greenwhite.smartup.agent4.mac";
    private static final String ARG_BUNDLE = "uz.greenwhite.smartup.agent4.mab";
    private static final String ARG_DATA = "uz.greenwhite.smartup.agent4.data";

    private static Intent newIntent(Context ctx, Class<? extends Fragment> cls, Bundle args) {
        Intent i = new Intent(ctx, MoldActivity.class);
        i.putExtra(ARG_CLASS, cls);
        if (args != null) {
            i.putExtra(ARG_BUNDLE, args);
        }
        return i;
    }

    public static Intent newIntentIndex(Context ctx, Class<? extends MoldIndexFragment> cls, Bundle args) {
        return newIntent(ctx, cls, args);
    }

    public static Intent newIntentContent(Context ctx, Class<? extends MoldContentFragment> cls, Bundle args) {
        return newIntent(ctx, cls, args);
    }

    private static void open(Context ctx, Class<? extends Fragment> cls, Bundle args) {
        ctx.startActivity(newIntent(ctx, cls, args));
    }

    public static void openIndex(Context ctx, Class<? extends MoldIndexFragment> cls, Bundle args) {
        open(ctx, cls, args);
    }

    public static void openContent(Context ctx, Class<? extends MoldContentFragment> cls, Bundle args) {
        open(ctx, cls, args);
    }

    private ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private Parcelable mData;
    private Object contentResult;
    private int mMenuIdSeq;
    private MyArray<MoldMenuContainer> mMenuItems;
    boolean isActivityRestarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (GWSLIB.DEBUG) {
            LogCreated(savedInstanceState != null);
        }

        mMenuIdSeq = 10;
        setContentView(R.layout.gwslib_mold_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        initDrawer();

        if (savedInstanceState != null) {
            isActivityRestarted = true;
            mData = savedInstanceState.getParcelable(ARG_DATA);
        } else {
            isActivityRestarted = false;

            Fragment fragment = createFragment();

            if (fragment instanceof MoldIndexFragment) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.gwslib_mold_index, fragment)
                        .commit();
            } else {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
                openContent((MoldContentFragment) fragment, false);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mData != null) {
            outState.putParcelable(ARG_DATA, mData);
        }
        super.onSaveInstanceState(outState);
    }

    public void setActionBarTitle(CharSequence title) {
        if (GWSLIB.DEBUG) {
            title = title + " - debug";
        }
        mActionBar.setTitle(title);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        this.mMenuItems = MyArray.emptyArray();

        MyArray<MoldMenuContainer> menuItems = MyArray.emptyArray();

        MoldIndexFragment indexFragment = getIndexFragment();
        if (indexFragment != null && indexFragment.mMenus != null) {
            menuItems = MyArray.from(indexFragment.mMenus);
        }

        MoldContentFragment contentFragment = getContentFragment();
        if (contentFragment != null) {
            MyArray<MoldMenuContainer> contentMenus = MyArray.emptyArray();
            if (contentFragment.mMenus != null) {
                contentMenus = MyArray.from(contentFragment.mMenus);
            }
            menuItems = contentFragment.joinWithIndexMenus(menuItems, contentMenus);
            if (contentFragment.searchQuery != null) {
                addSearchMenu(menu, contentFragment.searchQuery, contentFragment.searchQueryIcon);
            }
        }

        for (MoldMenuContainer m : menuItems) {
            if (m.id == 0) {
                m.id = mMenuIdSeq++;
            }
            MenuItem menuItem = menu.add(Menu.NONE, m.id, Menu.NONE, "");
            if (m.view != null) {
                MenuItemCompat.setActionView(menuItem, m.view);
            } else {
                menuItem.setIcon(Util.changeDrawableColor(this, m.iconResId, R.color.white));
            }
            MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        }

        if (contentFragment != null && contentFragment.rightDrawerIcon != 0) {
            addRightDrawerToggleMenu(menu, contentFragment.rightDrawerIcon);
        }

        this.mMenuItems = menuItems;

        return super.onPrepareOptionsMenu(menu);
    }

    private void addRightDrawerToggleMenu(Menu menu, int rightDrawerIcon) {
        MenuItem menuItem = menu.add(Menu.NONE, 2, Menu.NONE, "");
        menuItem.setIcon(rightDrawerIcon);
        MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            if (w == null) {
                return ret;
            }
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            mDrawerLayout.closeDrawer(GravityCompat.END);
            if (getIndexFragment() == null) {
                finish();
            }
            return true;
        }
        if (id == 2) {
            toggleDrawer(GravityCompat.END);
            return true;
        }
        for (MoldMenuContainer m : mMenuItems) {
            if (m.id == id) {
                if (m.command != null) {
                    m.command.apply();
                }
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    public MoldIndexFragment getIndexFragment() {
        return (MoldIndexFragment) getSupportFragmentManager().findFragmentById(R.id.gwslib_mold_index);
    }

    public MoldContentFragment getContentFragment() {
        return (MoldContentFragment) getSupportFragmentManager().findFragmentById(R.id.gwslib_mold_content);
    }

    public MoldMenuFragment getMenuFragment() {
        return (MoldMenuFragment) getSupportFragmentManager().findFragmentById(R.id.gwslib_mold_menu);
    }

    public Parcelable getData() {
        return mData;
    }

    public void setData(Parcelable data) {
        this.mData = data;
    }

    private void openContent(MoldContentFragment contentFragment, boolean addToBackStack) {
        if (GWSLIB.DEBUG) {
            debugLog("Open content: " + ((Object) contentFragment).getClass().getName() + " addToBackStack=" + addToBackStack);
        }
        hideSoftKeyboard();
        if (!addToBackStack) {
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.gwslib_mold_content, contentFragment);
        MoldMenuFragment menuFragment = contentFragment.getMenuFragment();
        if (menuFragment == null) {
            menuFragment = new DummyMoldMenuFragment();
        }
        ft.replace(R.id.gwslib_mold_menu, menuFragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
        ft.commit();
    }

    public void replaceContent(MoldContentFragment content) {
        openContent(content, false);
    }

    public void addContent(MoldContentFragment content) {
        openContent(content, true);
    }

    public void popContent(Object result) {
        getSupportFragmentManager().popBackStack();
        this.contentResult = result;
    }

    public void onContentActivityCreated(MoldContentFragment that) {
        MoldContentFragment contentFragment = getContentFragment();
        if (contentFragment == that) {
            MoldIndexFragment indexFragment = getIndexFragment();
            if (indexFragment != null) {
                CharSequence title = indexFragment.getActionBarTitle();
                if (title != null) {
                    title = "";
                }
                setActionBarTitle(title);
                supportInvalidateOptionsMenu();
            }
            ActionBarInfo info = ActionBarInfo.extractActionBarInfo(contentFragment);
            if (info != null) {
                setActionBarTitle(info.title);
            }
        }
    }

    public void onContentStarted(MoldContentFragment that) {
        MoldContentFragment contentFragment = getContentFragment();
        if (contentFragment == that) {
            try {
                mDrawerLayout.closeDrawers();
                supportInvalidateOptionsMenu();
                if (contentResult != null) {
                    contentFragment.onAboveContentPopped(contentResult);
                }
            } finally {
                contentResult = null;
            }
        }
    }

    public void onMenuActivityCreated(MoldMenuFragment that) {
        MoldMenuFragment menuFragment = getMenuFragment();
        if (menuFragment == that && !(menuFragment instanceof DummyMoldMenuFragment)) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.END);
        }
    }

    public void closeDrawers() {
        mDrawerLayout.closeDrawers();
    }

    public void openDrawer(int gravity) {
        switch (gravity) {
            case GravityCompat.START:
                closeDrawer(GravityCompat.END);
                break;
            case GravityCompat.END:
                closeDrawer(GravityCompat.START);
                break;
        }
        mDrawerLayout.openDrawer(gravity);
    }

    public void closeDrawer(int gravity) {
        mDrawerLayout.closeDrawer(gravity);
    }

    public void toggleDrawer(int gravity) {
        if (mDrawerLayout.isDrawerOpen(gravity)) {
            closeDrawer(gravity);
        } else {
            openDrawer(gravity);
        }
    }

    private Fragment createFragment() {
        try {
            Bundle extras = getIntent().getExtras();
            Bundle args = extras.getBundle(ARG_BUNDLE);

            Class<?> cls = (Class<?>) extras.getSerializable(ARG_CLASS);
            Fragment fragment = (Fragment) cls.newInstance();
            if (args != null) {
                fragment.setArguments(args);
            }

            return fragment;

        } catch (Exception e) {
            throw new AppError(e);
        }
    }

    private void initDrawer() {
        mActionBar = getSupportActionBar();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.gwslib_mold_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_menu_white_24dp,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if ("start".equals(drawerView.getTag())) {
                    MoldIndexFragment indexFragment = getIndexFragment();
                    if (indexFragment != null) {
                        indexFragment.onDrawerClosed();
                    }
                } else if ("end".equals(drawerView.getTag())) {
                    MoldMenuFragment menuFragment = getMenuFragment();
                    if (menuFragment != null) {
                        menuFragment.onDrawerClosed();
                    }
                }
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if ("start".equals(drawerView.getTag())) {
                    MoldIndexFragment indexFragment = getIndexFragment();
                    if (indexFragment != null) {
                        indexFragment.onDrawerOpened();
                    }
                } else if ("end".equals(drawerView.getTag())) {
                    MoldMenuFragment menuFragment = getMenuFragment();
                    if (menuFragment != null) {
                        menuFragment.onDrawerOpened();
                    }
                }
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void addSearchMenu(Menu menu, MoldSearchQuery searchQuery, int searchQueryIcon) {
        MenuItem item = menu.add(Menu.NONE, 1, Menu.NONE, "");
        item.setIcon(searchQueryIcon);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW |
                MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

        SearchView searchView = new SearchView(this);
        searchView.setIconified(false);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(searchQuery);
        searchView.clearFocus();
    }

    public void hideSoftKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START) || mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            closeDrawers();
            return;
        }
        MoldIndexFragment indexFragment = getIndexFragment();
        if (indexFragment != null && indexFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    private void LogCreated(boolean restarted) {
        Bundle extras = getIntent().getExtras();
        Bundle args = extras.getBundle(ARG_BUNDLE);

        Class<?> cls = (Class<?>) extras.getSerializable(ARG_CLASS);

        if (restarted) {
            debugLog("Activity restarted: " + cls.getName());
        } else {
            debugLog("Activity created: " + cls.getName());
        }
    }

    static void debugLog(String message) {
        GWSLIB.log("MOLD " + message);
    }

}
