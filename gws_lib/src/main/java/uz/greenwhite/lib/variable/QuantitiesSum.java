package uz.greenwhite.lib.variable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class QuantitiesSum implements Quantity {

    public final List<Quantity> quantities = new ArrayList<Quantity>();

    @Override
    public BigDecimal getQuantity() {
        BigDecimal total = BigDecimal.ZERO;
        for (Quantity q : quantities) {
            total = total.add(q.getQuantity());
        }
        return total;
    }

}