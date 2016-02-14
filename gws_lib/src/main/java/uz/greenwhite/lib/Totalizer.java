package uz.greenwhite.lib;

import android.util.SparseArray;

import java.math.BigDecimal;

public class Totalizer {

    private SparseArray<Quant> qs = new SparseArray<Quant>();

    public void add(int id, BigDecimal val) {
        Quant q = qs.get(id);
        if (q == null) {
            q = new Quant(id);
            qs.put(id, q);
        }
        q.add(val);
    }

    public BigDecimal get(int id) {
        Quant q = qs.get(id);
        if (q != null) {
            return q.quant;
        }
        return null;
    }


    static class Quant {
        final int id;
        BigDecimal quant;

        Quant(int id) {
            this.id = id;
        }

        public void add(BigDecimal val) {
            if (val == null) {
                return;
            }
            if (quant == null) {
                quant = val;
            } else {
                quant = quant.add(val);
            }
        }
    }
}
