package OctagonApp.model;

import java.io.FileNotFoundException;

public class Abdurozik extends Fighter implements IBehaviour {
    private static final String abdurozikImagePath = "src/main/resources/OctagonApp/gifs/Abdurozik.jpg";
    public Abdurozik(double x, double y, int id, int birthTime) throws FileNotFoundException {
        super(x, y, id, birthTime, abdurozikImagePath);
    }

    public Abdurozik() throws FileNotFoundException{
        super (abdurozikImagePath);
    }

    public Abdurozik(Abdurozik temp) {
        super(temp);
    }
}
