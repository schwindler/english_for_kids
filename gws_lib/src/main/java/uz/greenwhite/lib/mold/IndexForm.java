package uz.greenwhite.lib.mold;

import uz.greenwhite.lib.collection.MyMapper;

public class IndexForm {

    public final int id;
    public final CharSequence title;
    public final int icon;
    public final boolean inner;

    public IndexForm(int id, CharSequence title, int icon, boolean inner) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.inner = inner;
    }

    public IndexForm(int id, CharSequence title, int icon) {
        this(id, title, icon, false);
    }

    public IndexForm(int id, CharSequence title) {
        this(id, title, 0);
    }

    public static final MyMapper<IndexForm, Integer> KEY_ADAPTER = new MyMapper<IndexForm, Integer>() {
        @Override
        public Integer apply(IndexForm indexForm) {
            return indexForm.id;
        }
    };

}
