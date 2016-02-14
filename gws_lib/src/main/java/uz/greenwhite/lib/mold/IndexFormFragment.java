package uz.greenwhite.lib.mold;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import uz.greenwhite.lib.R;
import uz.greenwhite.lib.collection.MyArray;
import uz.greenwhite.lib.error.AppError;

public abstract class IndexFormFragment extends MoldIndexFragment {

    private ListView cList;
    private IndexFormAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cList = (ListView) inflater.inflate(R.layout.gwslib_mold_index_form, container, false);
        return cList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new IndexFormAdapter(getActivity());
        cList.setAdapter(adapter);
        cList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IndexForm item = adapter.getItem(position);
                showForm(item);
            }
        });
    }

    @Override
    protected void onShowForm() {
        if (adapter.getCount() == 0) {
            Log.e("Error", "Index adapter is empty.");
        } else {
            showForm(getDefaultFormId());
        }
    }

    public int getDefaultFormId() {
        return adapter.getItem(0).id;
    }

    protected void setForms(MyArray<IndexForm> forms) {
        adapter.setItems(forms);
    }

    public void showForm(int formId) {
        IndexForm form = adapter.getItems().find(formId, IndexForm.KEY_ADAPTER);
        if (form != null) {
            showForm(form);
        }
    }

    public abstract void showForm(IndexForm form);
}
