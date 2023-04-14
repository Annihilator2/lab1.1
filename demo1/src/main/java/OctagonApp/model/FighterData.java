package OctagonApp.model;

import java.util.*;
import java.util.stream.Stream;

public class FighterData {
    private static volatile FighterData fighterData;
    public Vector<Fighter> fighterList;
    public HashSet<Integer> idSet;
    public TreeMap<Integer,Integer> birthTimeTree;

    private FighterData(){
        fighterList = new Vector<>();
        idSet = new HashSet<>();
        birthTimeTree = new TreeMap<>();
    }
    public static FighterData getInstance() {
        FighterData localFighterData = fighterData;
        if (localFighterData ==null){
            synchronized (FighterData.class){
                localFighterData = fighterData;
                if (localFighterData ==null){
                    fighterData = localFighterData = new FighterData();
                }
            }
        }
        return localFighterData;
    }

    public void clearData() {
        Stream.of(fighterList,idSet,birthTimeTree.keySet(),birthTimeTree.values()).forEach(Collection::clear);
    }
}
