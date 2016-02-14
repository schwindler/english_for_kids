package johnny.hope.inc.englishkids.ui.lesson;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import johnny.hope.inc.englishkids.R;
import uz.greenwhite.lib.view_setup.ViewSetup;

public class LearningCategoryFragment extends Fragment implements View.OnClickListener {

    public static LearningCategoryFragment newInstance() {
        return new LearningCategoryFragment();
    }

    private ViewSetup vsRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vsRoot = new ViewSetup(inflater, container, R.layout.learn_category);
        return vsRoot.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vsRoot.id(R.id.number).setOnClickListener(this);
        vsRoot.id(R.id.animal).setOnClickListener(this);
        vsRoot.id(R.id.fruits).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        LearningFragment.open(getActivity(), (String) v.getTag());
    }
}
