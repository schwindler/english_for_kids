package johnny.hope.inc.englishkids.ui.exercise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import johnny.hope.inc.englishkids.R;
import uz.greenwhite.lib.view_setup.ViewSetup;

public class ExerciseFragment extends Fragment implements View.OnClickListener {

    public static ExerciseFragment newInstance() {
        return new ExerciseFragment();
    }

    private ViewSetup vsRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.vsRoot = new ViewSetup(inflater, container, R.layout.exercise);
        return this.vsRoot.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        vsRoot.id(R.id.game).setOnClickListener(this);
        vsRoot.id(R.id.listening).setOnClickListener(this);
        vsRoot.id(R.id.matching).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game:
                ExerciseGameFragment.open(getActivity());
                break;
            case R.id.listening:
                ExerciseListeningFragment.open(getActivity());
                break;
            case R.id.matching:
                ExerciseMatchingFragment.open(getActivity());
                break;
        }
    }
}
