package uz.greenwhite.lib;

import android.util.SparseArray;

import java.math.BigDecimal;

public class Totalizer2 {

    private SparseArray<Totalizer> ts = new SparseArray<Totalizer>();

    public void add(int id1, int id2, BigDecimal val) {
        Totalizer total = ts.get(id1);
        if (total == null) {
            total = new Totalizer();
            ts.put(id1, total);
        }
        total.add(id2, val);
    }

    public BigDecimal get(int id1, int id2) {
        Totalizer total = ts.get(id1);
        if (total != null) {
            return total.get(id2);
        }
        return null;
    }

}
