package johnny.hope.inc.englishkids.ui.watch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import johnny.hope.inc.englishkids.R;
import uz.greenwhite.lib.collection.MyAdapter;
import uz.greenwhite.lib.view_setup.ViewSetup;

public class WatchFragment extends Fragment {

    public static WatchFragment newInstance() {
        return new WatchFragment();
    }

    private ViewSetup vsRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vsRoot = new ViewSetup(inflater, container, R.layout.watch_grid);
        return vsRoot.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridView cGrid = vsRoot.id(R.id.gallery);
        MyGridAdapter adapter = new MyGridAdapter(getActivity());
        cGrid.setAdapter(adapter);
        adapter.setItems(WatchApi.getItems());
        cGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Comming soon!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    class MyGridAdapter extends MyAdapter<Watch, MyGridAdapter.ViewHolder> {

        public MyGridAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutResource() {
            return R.layout.watch_grid_row;
        }

        @Override
        public ViewHolder makeHolder(View view) {
            ViewHolder h = new ViewHolder();
//            h.text = UI.id(view, R.id.text);
            return h;
        }

        @Override
        public void populate(final ViewHolder holder, final Watch item) {
//            holder.text.setText("");
        }

        class ViewHolder {
            TextView text;
        }
    }
}
