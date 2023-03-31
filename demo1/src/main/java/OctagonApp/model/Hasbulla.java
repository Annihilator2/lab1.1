package OctagonApp.model;

import java.io.FileNotFoundException;

public class Hasbulla extends Fighter implements IBehaviour {
    public Hasbulla(double x, double y, String imagePath) throws FileNotFoundException {
        super(x, y, imagePath);
    }
}