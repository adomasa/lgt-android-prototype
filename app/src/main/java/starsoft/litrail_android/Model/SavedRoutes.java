package starsoft.litrail_android.Model;

import java.util.ArrayList;
import java.util.List;

public class SavedRoutes {

    public static final List<SavedRoute> ITEMS = new ArrayList<SavedRoute>();

    static {
        ITEMS.add(new SavedRoute("Kaunas", "Šiauliai"));
        ITEMS.add(new SavedRoute("Vilnius", "Šiauliai"));
        ITEMS.add(new SavedRoute("Panevėžys", "Klaipėda"));
    }
}
