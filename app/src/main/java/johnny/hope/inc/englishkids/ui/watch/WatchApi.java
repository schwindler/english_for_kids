package johnny.hope.inc.englishkids.ui.watch;

import uz.greenwhite.lib.collection.MyArray;

public class WatchApi {

    public static MyArray<Watch> getItems() {
        MyArray<Watch> list = MyArray.from(
                new Watch(),
                new Watch(),
                new Watch(),
                new Watch(),
                new Watch(),
                new Watch(),
                new Watch(),
                new Watch(),
                new Watch(),
                new Watch(),
                new Watch(),
                new Watch()
        );
        return list;
    }


}
