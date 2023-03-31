package OctagonApp.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class HabitatModel {
    private static final String hasbullaImagePath = "src/main/resources/OctagonApp/gifs/hasbulla-kick.gif";
    private static final String abdurozikImagePath = "src/main/resources/OctagonApp/gifs/abdurozik.gif";
    private int hasbullaSpawnTime = 3;
    private int abdurozikSpawnTime = 5;
    private short hasbullaSpawnChance = 100;
    private short abdurozikSpawnChance = 100;
    private static ArrayList<Fighter> fighterArrayList = FighterArray.getInstance().getFighterArrayList();

    public HabitatModel() {
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
            this.abdurozikSpawnTime = newChance;
        }

    }

    public short getAbdurizikSpawnChance() {
        return this.abdurozikSpawnChance;
    }

    public ArrayList<Fighter> getFighterArrayList() {
        return fighterArrayList;
    }

    public void addFighterToList(Fighter fighter) {
        this.getFighterArrayList().add(fighter);
    }

    public void clearFighterList() {
        fighterArrayList.clear();
    }

    public long getFighterAmount(Class clazz) {
        long fishAmount = -1L;
        if (clazz == Hasbulla.class || clazz == Abdurozik.class) {
            fishAmount = fighterArrayList.stream().filter((obj) -> {
                return obj.getClass() == clazz;
            }).count();
        }

        return fishAmount;
    }

    public Fighter createFighter(double xBound, double yBound, Class clazz) throws FileNotFoundException {
        Random randomGenerator = new Random();
        int x = randomGenerator.nextInt((int)xBound);
        int y = randomGenerator.nextInt((int)yBound);
        Fighter createdFighter = null;
        if (clazz == Hasbulla.class) {
            Hasbulla hasbulla = new Hasbulla((double)x, (double)y, hasbullaImagePath);
            this.addFighterToList(hasbulla);
            createdFighter = hasbulla;
        } else if (clazz == Abdurozik.class) {
            Abdurozik abdurozik = new Abdurozik((double)x, (double)y, abdurozikImagePath);
            this.addFighterToList(abdurozik);
            createdFighter = abdurozik;
        }

        return (Fighter)createdFighter;
    }
}