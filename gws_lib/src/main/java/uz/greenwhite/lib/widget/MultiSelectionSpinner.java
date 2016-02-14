package uz.greenwhite.lib.widget;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import uz.greenwhite.lib.R;
import uz.greenwhite.lib.collection.MyArray;
import uz.greenwhite.lib.collection.MyMapper;
import uz.greenwhite.lib.collection.MyPredicate;

public class MultiSelectionSpinner extends Spinner implements
        OnMultiChoiceClickListener {
    private MyArray<MultiSelection> ms = null;

    ArrayAdapter<String> simple_adapter;

    public MultiSelectionSpinner(Context context) {
        super(context);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (ms != null && which < ms.size()) {
            MultiSelection multiSelection = ms.get(which);
            multiSelection.selected = isChecked;

            simple_adapter.clear();
            simple_adapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        CharSequence[] titles = ms.map(new MyMapper<MultiSelection, String>() {
            @Override
            public String apply(MultiSelection multiSelection) {
                return multiSelection.title;
            }
        }).toCharSequenceArray();

        boolean[] s = new boolean[ms.size()];
        for (int i = 0; i < ms.size(); i++) {
            s[i] = ms.get(i).selected;
        }

        builder.setMultiChoiceItems(titles, s, this);
        builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(MultiSelection[] items) {
        this.ms = MyArray.from(items);
        this.simple_adapter.clear();
        this.simple_adapter.add(buildSelectedItemString());
    }

    public void setItems(MyArray<MultiSelection> items) {
        this.ms = items;
        this.simple_adapter.clear();
        this.simple_adapter.add(buildSelectedItemString());
    }

    public void selectedAll(boolean selected) {
        for (MultiSelection m : ms) {
            m.selected = selected;
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int index) {
        for (int i = 0; i < ms.size(); i++) {
            ms.get(i).selected = false;
        }
        if (index >= 0 && index < ms.size()) {
            ms.get(index).selected = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public MyArray<MultiSelection> getSelectedStrings() {
        MyArray<MultiSelection> selection = MyArray.emptyArray();
        for (int i = 0; i < ms.size(); ++i) {
            MultiSelection multiSelection = ms.get(i);
            if (multiSelection.selected) {
                selection = selection.append(multiSelection);
            }
        }
        return selection;
    }

    public MyArray<Integer> getSelectedIds() {
        MyArray<Integer> selection = MyArray.emptyArray();
        for (int i = 0; i < ms.size(); ++i) {
            MultiSelection multiSelection = ms.get(i);
            if (multiSelection.selected) {
                selection = selection.append(multiSelection.id);
            }
        }
        return selection;
    }

    private String buildSelectedItemString() {

        String val = ms
                .filter(new MyPredicate<MultiSelection>() {
                    @Override
                    public boolean apply(MultiSelection multiSelection) {
                        return multiSelection.selected;
                    }
                })
                .map(new MyMapper<MultiSelection, String>() {
                    @Override
                    public String apply(MultiSelection multiSelection) {
                        return multiSelection.title;
                    }
                })
                .mkString(",")
                .trim();

        if (TextUtils.isEmpty(val)) {
            return getContext().getString(R.string.not_selected);
        }
        return val;
    }

    public final static class MultiSelection {

        public final Integer id;
        public final String title;
        public boolean selected;

        public MultiSelection(Integer id, String title, boolean selected) {
            this.id = id;
            this.title = title;
            this.selected = selected;
        }

        public MultiSelection(Integer id, String title) {
            this(id, title, false);
        }

        public static final MyMapper<MultiSelection, Integer> KEY_ADAPTER = new MyMapper<MultiSelection, Integer>() {
            @Override
            public Integer apply(MultiSelection multiSelection) {
                return multiSelection.id;
            }
        };
    }
}