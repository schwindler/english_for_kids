package uz.greenwhite.lib.mold;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import uz.greenwhite.lib.R;
import uz.greenwhite.lib.Util;
import uz.greenwhite.lib.collection.MyAdapter;

public class IndexFormAdapter extends MyAdapter<IndexForm, IndexFormAdapter.ViewHolder> {


    public IndexFormAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.gwslib_mold_index_form_row;
    }

    @Override
    public ViewHolder makeHolder(View view) {
        ViewHolder h = new ViewHolder();
        h.icon = (ImageView) view.findViewById(R.id.gwslib_icon);
        h.title = (TextView) view.findViewById(R.id.gwslib_title);
        h.inner = (ImageView) view.findViewById(R.id.gwslib_inner);
        return h;
    }

    @Override
    public void populate(ViewHolder h, IndexForm item) {
        h.icon.setImageDrawable(Util.changeDrawableColor(context, item.icon, R.color.app_color));
        h.icon.setVisibility(item.icon == 0 ? View.GONE : View.VISIBLE);
        h.title.setText(item.title);
        h.inner.setVisibility(item.inner ? View.VISIBLE : View.INVISIBLE);
    }

    static class ViewHolder {
        ImageView icon;
        TextView title;
        ImageView inner;
    }
}
