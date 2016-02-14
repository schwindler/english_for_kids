package uz.greenwhite.lib.view_setup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import uz.greenwhite.lib.collection.MyArray;
import uz.greenwhite.lib.variable.SpinnerOption;

public class MySpinnerAdapter extends BaseAdapter {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final MyArray<SpinnerOption> options;

    public MySpinnerAdapter(Context context, MyArray<SpinnerOption> options) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.options = options;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public SpinnerOption getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private View createView(int position, View convertView, ViewGroup parent, int resource) {
        TextView view = (TextView) convertView;
        if (view == null) {
            view = (TextView) mInflater.inflate(resource, parent, false);
        }
        SpinnerOption item = getItem(position);
        view.setText(item.name);

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent, android.R.layout.simple_spinner_item);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent,
                android.R.layout.simple_spinner_dropdown_item);
    }
}
