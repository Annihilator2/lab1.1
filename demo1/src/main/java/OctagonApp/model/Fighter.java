package OctagonApp.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Fighter {
    private final ImageView fishView;

    Fighter(double x, double y, String imagePath) throws FileNotFoundException {
        Image fightersImage = new Image(new FileInputStream(imagePath));
        this.fishView = new ImageView(fightersImage);
        this.fishView.setPreserveRatio(true);
        this.fishView.setX(x);
        this.fishView.setY(y);
        this.fishView.setFitWidth(90.0);
        this.fishView.setFitHeight(90.0);
    }

    public ImageView getView() {
        return this.fishView;
    }
}
