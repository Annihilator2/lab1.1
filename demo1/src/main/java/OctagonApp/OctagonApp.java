
package OctagonApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import OctagonApp.controller.OctagonController;
import OctagonApp.model.HabitatModel;

public class OctagonApp extends Application {

    private OctagonController habitatController;
    private HabitatModel habitatModel = new HabitatModel();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/hello-view.fxml"));
        primaryStage = loader.load();
        habitatController = loader.getController();
        habitatController.setHabitatModel(habitatModel);
        primaryStage.setWidth(1400);
        primaryStage.setHeight(900);
        primaryStage.setTitle("Octagon");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}