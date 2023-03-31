package OctagonApp.model;

import java.io.FileNotFoundException;

public class Abdurozik extends Fighter implements IBehaviour {
    public Abdurozik(double x, double y, String imagePath) throws FileNotFoundException {
        super(x, y, imagePath);
    }
}
