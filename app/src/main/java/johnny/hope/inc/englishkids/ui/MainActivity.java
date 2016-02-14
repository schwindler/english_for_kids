package johnny.hope.inc.englishkids.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import johnny.hope.inc.englishkids.R;
import johnny.hope.inc.englishkids.ui.lesson.LearningCategoryFragment;
import johnny.hope.inc.englishkids.ui.watch.WatchFragment;
import uz.greenwhite.lib.view_setup.ViewSetup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static void open(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    private ViewSetup vsRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vsRoot = new ViewSetup(this, R.layout.main);
        setContentView(vsRoot.view);

        vsRoot.id(R.id.btn_watch).setOnClickListener(this);
        vsRoot.id(R.id.btn_learn).setOnClickListener(this);
        vsRoot.id(R.id.btn_play).setOnClickListener(this);

        if (savedInstanceState == null)
            replaceContent(LearningCategoryFragment.newInstance(), R.drawable.bkg_learn);
    }

    @Override
    public void onClick(View v) {
        Fragment content = null;
        int bkgResId = 0;
        switch (v.getId()) {
            case R.id.btn_learn:
                bkgResId = R.drawable.bkg_learn;
                content = LearningCategoryFragment.newInstance();
                break;
            case R.id.btn_watch:
                bkgResId = R.drawable.bkg_watch;
                content = WatchFragment.newInstance();
                break;
            case R.id.btn_play:
                bkgResId = R.drawable.bkg_play;
                break;
        }
        replaceContent(content, bkgResId);
    }

    private void replaceContent(Fragment fragment, int bkgResId) {
        if (bkgResId != 0) vsRoot.id(R.id.background_ll).setBackgroundResource(bkgResId);

        if (fragment == null) {
            Toast.makeText(this, "Comming soon!", Toast.LENGTH_SHORT).show();
            ((ViewGroup) vsRoot.id(R.id.content)).removeAllViews();
            return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();

    }
}
