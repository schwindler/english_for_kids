package johnny.hope.inc.englishkids.ui.lesson;

import java.util.ArrayList;
import java.util.List;

import johnny.hope.inc.englishkids.R;
import johnny.hope.inc.englishkids.bean.Learning;
import uz.greenwhite.lib.collection.MyArray;

public class LearningApi {

    private static int[] animals = new int[]{
            R.drawable.beer, R.drawable.cow, R.drawable.elephant,
            R.drawable.goat, R.drawable.hippopotamus, R.drawable.jiraf, R.drawable.mamont,
            R.drawable.monkey, R.drawable.olw, R.drawable.rhino, R.drawable.cat, R.drawable.beaver, R.drawable.sheep, R.drawable.snake,
            R.drawable.tiger, R.drawable.turkey, R.drawable.dog
    };

    private static int[] fruts = new int[]{
            R.drawable.apricot, R.drawable.avocado, R.drawable.banana, R.drawable.cabbage, R.drawable.cherry
            , R.drawable.coconut, R.drawable.cranberry, R.drawable.gooseberry, R.drawable.grape, R.drawable.grapefruit
            , R.drawable.huckleberry, R.drawable.kiwi, R.drawable.lemon, R.drawable.mango, R.drawable.melon
            , R.drawable.meva1, R.drawable.orange, R.drawable.peach, R.drawable.pear, R.drawable.pineapple
            , R.drawable.pomegranate, R.drawable.raspberry, R.drawable.strawberry, R.drawable.tomato
            , R.drawable.walnut, R.drawable.watermelon
    };

    private static int[] numbers = new int[]{
            R.drawable.n0, R.drawable.n1, R.drawable.n2, R.drawable.n3, R.drawable.n4, R.drawable.n5, R.drawable.n6
            , R.drawable.n7, R.drawable.n8, R.drawable.n9, R.drawable.n10, R.drawable.n11
            , R.drawable.n12, R.drawable.n13, R.drawable.n14, R.drawable.n15, R.drawable.n16
            , R.drawable.n17, R.drawable.n18, R.drawable.n19, R.drawable.n20
    };

    public static MyArray<Learning> getItems(String key) {
        int[] lists = LearningApi.animals;
        switch (key) {
            case "number":
                lists = LearningApi.numbers;
                break;
            case "fruits":
                lists = LearningApi.fruts;
                break;
        }

        List<Learning> result = new ArrayList<>();
        int id = 0;
        for (int resId : lists) {
            result.add(new Learning(id++, resId));
        }
        return MyArray.from(result);
    }


}
