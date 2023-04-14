package OctagonApp.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Fighter {
    private final ImageView fighterView;
    private int xCoord, yCoord;
    private int birthTime;
    private int id;

    Fighter(Fighter temp) {
        this.fighterView = new ImageView(temp.getView().getImage());
    }

    Fighter(String imagePath) throws FileNotFoundException {
        this(0, 0,0, 0, imagePath);
    }

    public int getBirthTime(){
        return birthTime;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public int getId(){
        return id;
    }

    Fighter(double x, double y, int id, int birthTime, String imagePath) throws FileNotFoundException {
        Image fightersImage = new Image(new FileInputStream(imagePath));
        this.fighterView = new ImageView(fightersImage);
        this.fighterView.setPreserveRatio(true);
        this.fighterView.setX(x);
        this.fighterView.setY(y);
        this.fighterView.setFitWidth(90.0);
        this.fighterView.setFitHeight(90.0);
        this.birthTime=birthTime;
        this.id = id;
        this.xCoord= (int) x;
        this.yCoord= (int) y;
    }

    public ImageView getView() {
        return this.fighterView;
    }
}
