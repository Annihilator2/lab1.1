package OctagonApp.model;

import java.util.ArrayList;

public class FighterArray {
    private static volatile FighterArray fighterArray;
    private ArrayList<Fighter> fighterArrayList = new ArrayList();

    private FighterArray() {
    }

    public static FighterArray getInstance() {
        FighterArray localFighterArray = fighterArray;
        if (localFighterArray == null) {
            Class var1 = FighterArray.class;
            synchronized(FighterArray.class) {
                localFighterArray = fighterArray;
                if (localFighterArray == null) {
                    fighterArray = localFighterArray = new FighterArray();
                }
            }
        }

        return localFighterArray;
    }

    public ArrayList<Fighter> getFighterArrayList() {
        return this.fighterArrayList;
    }
}
