package johnny.hope.inc.englishkids.ui.exercise;

import android.app.Activity;

import uz.greenwhite.lib.mold.MoldActivity;
import uz.greenwhite.lib.mold.MoldContentFragment;

public class ExerciseListeningFragment extends MoldContentFragment {
    public static void open(Activity activity) {
        MoldActivity.openContent(activity, ExerciseListeningFragment.class, null);
    }
}
