package uz.greenwhite.lib.collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import uz.greenwhite.lib.error.AppError;

public abstract class MyAdapter<E, H> extends BaseAdapter implements Filterable {

    protected final Context context;
    protected final LayoutInflater inflater;
    protected MyArray<E> items = MyArray.emptyArray();
    protected MyArray<E> filteredItems = MyArray.emptyArray();

    private ItemFilter itemFilter;
    public MyPredicate<E> predicateSearch;
    public MyPredicate<E> predicateOthers;

    public MyAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private MyArray<E> filterItems() {
        MyPredicate<E> predicate = MyPredicate.and(predicateSearch, predicateOthers);
        if (predicate != null) {
            return items.filter(predicate);
        } else {
            return items;
        }
    }

    public void setItems(MyArray<E> items) {
        if (items == null) {
            throw AppError.NullPointer();
        }
        this.items = items;
        this.filteredItems = filterItems();
        notifyDataSetChanged();
    }

    public MyArray<E> getItems() {
        return this.items;
    }

    public MyArray<E> getFilteredItems() {
        return this.filteredItems;
    }

    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Override
    public E getItem(int position) {
        return filteredItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            H holder;
            if (convertView == null) {
                convertView = inflater.inflate(getLayoutResource(), parent, false);
                holder = makeHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (H) convertView.getTag();
            }

            populate(holder, getItem(position));

            return convertView;
        } catch (Exception ex) {
            throw new AppError(this.getClass().getName(), ex);
        }
    }

    public void filter() {
        getFilter().filter(null);
    }

    public abstract int getLayoutResource();

    public abstract H makeHolder(View view);

    public abstract void populate(H holder, E item);

    @Override
    public Filter getFilter() {
        if (itemFilter == null) {
            itemFilter = new ItemFilter();
        }
        return itemFilter;
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            try {
                MyArray<E> filteredItems = filterItems();
                result.values = filteredItems;
                result.count = filteredItems.size();
            } catch (Exception ex) {
                result.values = ex;
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values instanceof Exception) {
                throw new AppError((Throwable) results.values);
            } else {
                filteredItems = (MyArray<E>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
