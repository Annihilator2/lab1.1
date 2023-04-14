package OctagonApp.model;

import java.io.FileNotFoundException;

public class Hasbulla extends Fighter implements IBehaviour {
    private static final String hasbullaImagePath = "src/main/resources/OctagonApp/gifs/Hasbik.jpg";
    public Hasbulla(double x, double y, int id, int birthTime) throws FileNotFoundException {
        super(x, y, id, birthTime, hasbullaImagePath);
    }

    public Hasbulla() throws FileNotFoundException {
        super(hasbullaImagePath);
    }

    public Hasbulla(Hasbulla temp) {
        super(temp);
    }
}