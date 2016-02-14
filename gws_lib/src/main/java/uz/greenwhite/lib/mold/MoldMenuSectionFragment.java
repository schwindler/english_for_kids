package uz.greenwhite.lib.mold;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Iterator;
import java.util.List;

import uz.greenwhite.lib.R;
import uz.greenwhite.lib.collection.MyArray;

public class MoldMenuSectionFragment extends MoldMenuFragment {

    ListView cList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cList = (ListView) inflater.inflate(R.layout.gwslib_mold_menu_section, container, false);
        return cList;
    }

    public void setSections(MyArray<Section> sections) {
        SessionAdapter adapter = new SessionAdapter(sections);
        cList.setAdapter(adapter);
    }

    public interface Section {

        View createView(LayoutInflater inflater, ViewGroup parent);

    }

    public abstract class LinearLayoutSection implements Section {

        @Override
        public View createView(LayoutInflater inflater, ViewGroup parent) {
            View v = inflater.inflate(R.layout.gwslib_mold_menu_section_row, parent, false);
            addViews((LinearLayout) v);
            return v;
        }

        public abstract void addViews(LinearLayout cnt);
    }

    class SessionAdapter extends BaseAdapter {

        final LayoutInflater inflater;
        final MyArray<Section> sections;

        SessionAdapter(MyArray<Section> sections) {
            inflater = LayoutInflater.from(getActivity());
            this.sections = sections;
        }

        @Override
        public int getCount() {
            return sections.size();
        }

        @Override
        public int getViewTypeCount() {
            return sections.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return sections.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = sections.get(position).createView(inflater, parent);
            }
            return v;
        }
    }


}
