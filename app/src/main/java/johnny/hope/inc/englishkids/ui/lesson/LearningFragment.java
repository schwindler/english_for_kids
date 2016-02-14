package johnny.hope.inc.englishkids.ui.lesson;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import johnny.hope.inc.englishkids.R;
import johnny.hope.inc.englishkids.bean.Learning;
import uz.greenwhite.lib.collection.MyAdapter;
import uz.greenwhite.lib.mold.MoldActivity;
import uz.greenwhite.lib.mold.MoldContentFragment;
import uz.greenwhite.lib.view_setup.UI;
import uz.greenwhite.lib.view_setup.ViewSetup;

public class LearningFragment extends MoldContentFragment {

    public static void open(Activity activity, String key) {
        Bundle b = new Bundle();
        b.putString("k", key);
        MoldActivity.openContent(activity, LearningFragment.class, b);
    }

    public String getKey() {
        return getArguments().getString("k");
    }

    private ViewSetup vsRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vsRoot = new ViewSetup(inflater, container, R.layout.learn_grid);
        return vsRoot.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridView cGrid = vsRoot.id(R.id.gallery);
        MyGridAdapter adapter = new MyGridAdapter(getActivity());
        cGrid.setAdapter(adapter);
        adapter.setItems(LearningApi.getItems(getKey()));
        cGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Comming soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    class MyGridAdapter extends MyAdapter<Learning, MyGridAdapter.ViewHolder> {

        public MyGridAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutResource() {
            return R.layout.learn_grid_row;
        }

        @Override
        public ViewHolder makeHolder(View view) {
            ViewHolder h = new ViewHolder();
            h.image = UI.id(view, R.id.image);
            return h;
        }

        @Override
        public void populate(final ViewHolder holder, final Learning item) {
            holder.image.setImageResource(item.resId);
        }

        class ViewHolder {
            ImageView image;
        }
    }
}
