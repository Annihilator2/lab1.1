package OctagonApp.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class HabitatModel {
    private static final String hasbullaImagePath = "src/main/resources/OctagonApp/gifs/Hasbik.jpg";
    private static final String abdurozikImagePath = "src/main/resources/OctagonApp/gifs/Abdurozik.jpg";
    private int hasbullaSpawnTime;
    private int abdurozikSpawnTime;
    private int hasbullaLifeTime, abduzorikLifeTime;
    private short hasbullaSpawnChance;
    private short abdurozikSpawnChance;

    private static FighterData fighterData;
    private static ArrayList<Fighter> fighterArrayList = FighterArray.getInstance().getFighterArrayList();

    public HabitatModel() {
        fighterData = FighterData.getInstance();
        hasbullaLifeTime = 15;
        abduzorikLifeTime = 20;
        hasbullaSpawnChance = 100;
        abdurozikSpawnChance = 100;
        abdurozikSpawnTime = 3;
        hasbullaSpawnTime = 5;
    }

    public void setHasbullaLifeTime(int time) {
        this.hasbullaLifeTime = time;
    }

    public void setAbduzorikLifeTime(int time) {
        this.abduzorikLifeTime = time;
    }

    public int getAbduzorikLifeTime() {
        return abduzorikLifeTime;
    }

    public int getHasbullaLifeTime() {
        return hasbullaLifeTime;
    }

    public void setHasbulaSpawnTime(int newTime) {
        this.hasbullaSpawnTime = newTime;
    }

    public int getHasbulaSpawnTime() {
        return this.hasbullaSpawnTime;
    }

    public void setAbdurizikSpawnTime(int newTime) {
        this.abdurozikSpawnTime = newTime;
    }

    public int getAbdurizikSpawnTime() {
        return this.abdurozikSpawnTime;
    }

    public void setHasbulaSpawnChance(short newChance) {
        if (newChance >= 0 && newChance <= 100) {
            this.hasbullaSpawnChance = newChance;
        }

    }

    public short getHasbulaSpawnChance() {
        return this.hasbullaSpawnChance;
    }

    public void setAbdurizikSpawnChance(short newChance) {
        if (newChance >= 0 && newChance <= 100) {
            this.abdurozikSpawnChance = newChance;
        }

    }

    public short getAbdurizikSpawnChance() {
        return this.abdurozikSpawnChance;
    }

    /*public ArrayList<Fighter> getFighterArrayList() {
        return fighterArrayList;
    }*/


    public long getFighterAmount(Class clazz) {
        long fighterAmount = -1;
        if (clazz == Hasbulla.class || clazz == Abdurozik.class) {
            fighterAmount = fighterData.fighterList.stream().filter((obj) -> obj.getClass() == clazz).count();
        }

        return fighterAmount;
    }


    public Fighter createFighter(double xBound, double yBound, int id, int birthTime, Class clazz) throws FileNotFoundException {
        Random randomGenerator = new Random();
        int x = randomGenerator.nextInt((int) xBound);
        int y = randomGenerator.nextInt((int) yBound);
        Fighter createdFighter = null;
        if (clazz == Hasbulla.class) {
            Hasbulla hasbulla = new Hasbulla(x, y, id, birthTime);
            fighterData.fighterList.add(hasbulla);
            fighterData.idSet.add(id);
            fighterData.birthTimeTree.put(id, birthTime);
            createdFighter = hasbulla;
        } else if (clazz == Abdurozik.class) {
            Abdurozik abdurozik = new Abdurozik(x, y, id, birthTime);
            fighterData.fighterList.add(abdurozik);
            fighterData.idSet.add(id);
            fighterData.birthTimeTree.put(id, birthTime);
            createdFighter = abdurozik;
        }
        return createdFighter;
    }

    public FighterData getFighterData() {
        return this.fighterData;
    }
}