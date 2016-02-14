package uz.greenwhite.lib.mold;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;

import uz.greenwhite.lib.error.AppError;

public class MoldUtil {

    public static <T extends MoldIndexFragment> T getIndexFragment(Activity activity) {
        return (T) ((MoldActivity) activity).getIndexFragment();
    }

    public static <T extends MoldContentFragment> T getContentFragment(Activity activity) {
        return (T) ((MoldActivity) activity).getContentFragment();
    }

    public static <T extends MoldMenuFragment> T getMenuFragment(Activity activity) {
        return (T) ((MoldActivity) activity).getMenuFragment();
    }

    public static <T extends Parcelable> T getData(Activity activity) {
        return (T) ((MoldActivity) activity).getData();
    }

    public static void setData(Activity activity, Parcelable data) {
        ((MoldActivity) activity).setData(data);
    }

    private static void setActionBarInfo(MoldContentFragment content, CharSequence title, int logo) {
        Bundle arguments = content.getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putParcelable(ActionBarInfo.ACTION_BAR_INFO, new ActionBarInfo(title, logo));
        content.setArguments(arguments);
    }

    public static void replaceContent(Activity activity, MoldContentFragment content) {
        ((MoldActivity) activity).replaceContent(content);
    }

    public static void replaceContent(Activity activity, MoldContentFragment content, CharSequence title, int logo) {
        setActionBarInfo(content, title, logo);
        replaceContent(activity, content);
    }

    public static void replaceContent(Activity activity, MoldContentFragment content, CharSequence title) {
        replaceContent(activity, content, title, 0);
    }

    public static void replaceContent(Activity activity, MoldContentFragment content, IndexForm form) {
        replaceContent(activity, content, form.title, form.icon);
    }

    public static void addContent(Activity activity, MoldContentFragment content) {
        ((MoldActivity) activity).addContent(content);
    }

    public static void addContent(Activity activity, MoldContentFragment content, CharSequence title, int logo) {
        setActionBarInfo(content, title, logo);
        addContent(activity, content);
    }

    public static void addContent(Activity activity, MoldContentFragment content, CharSequence title) {
        addContent(activity, content, title, 0);
    }

    public static void addContent(Activity activity, MoldContentFragment content, IndexForm form) {
        addContent(activity, content, form.title, form.icon);
    }

    public static void popContent(Activity activity, Object param) {
        MoldActivity a = (MoldActivity) activity;
        a.popContent(param);
    }

    public static void popContent(Activity activity) {
        popContent(activity, null);
    }

    public static void openIndexDrawer(Activity activity) {
        ((MoldActivity) activity).openDrawer(GravityCompat.START);
    }

    public static void closeIndexDrawer(Activity activity) {
        ((MoldActivity) activity).closeDrawer(GravityCompat.START);
    }

    public static void toggleIndexDrawer(Activity activity) {
        ((MoldActivity) activity).toggleDrawer(GravityCompat.START);
    }

    public static void openMenuDrawer(Activity activity) {
        ((MoldActivity) activity).openDrawer(GravityCompat.END);
    }

    public static void closeMenuDrawer(Activity activity) {
        ((MoldActivity) activity).closeDrawer(GravityCompat.END);
    }

    public static void toggleMenuDrawer(Activity activity) {
        ((MoldActivity) activity).toggleDrawer(GravityCompat.END);
    }

    public static void closeDrawers(Activity activity) {
        ((MoldActivity) activity).closeDrawers();
    }

    public static void showContent(Activity activity, int formId) {
        IndexFormFragment f = getIndexFragment(activity);
        f.showForm(formId);
    }

    public static void reloadContent(Activity activity) {
        MoldContentFragment c = getContentFragment(activity);
        if (c != null) {
            c.reloadContent();
        }
    }

    public static void setLogo(Activity activity, int resId) {
        MoldActivity mac = (MoldActivity) activity;
        //mac.getSupportActionBar().setLogo(resId);
    }

    public static void setTitle(Activity activity, CharSequence title) {
        MoldActivity mac = (MoldActivity) activity;
        mac.setActionBarTitle(title);
    }

    public static void setTitle(Activity activity, int stringId) {
        setTitle(activity, activity.getString(stringId));
    }

    public static <T extends Parcelable> Bundle parcelableArgument(Parcelable argument) {
        Bundle args = new Bundle();
        args.putParcelable("arg", argument);
        return args;
    }

    public static <T extends Parcelable> T parcelableArgument(Fragment fragment) {
        return fragment.getArguments().getParcelable("arg");
    }

    public static <T extends Fragment> T parcelableArgumentNewInstance(Class<T> cls, Parcelable argument) {
        try {
            if (argument == null) {
                throw AppError.NullPointer();
            }
            T f = cls.newInstance();
            Bundle args = parcelableArgument(argument);
            f.setArguments(args);
            return f;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
