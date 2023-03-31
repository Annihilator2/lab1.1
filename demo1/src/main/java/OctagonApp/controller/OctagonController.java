package OctagonApp.controller;

import OctagonApp.model.Abdurozik;
import OctagonApp.model.Fighter;
import OctagonApp.model.HabitatModel;
import OctagonApp.model.Hasbulla;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OctagonController implements Initializable {
    @FXML
    private Label timeLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Label statisticsLabel;
    @FXML
    private Stage mainStage;
    @FXML
    private Scene mainScene;
    @FXML
    private Pane imagePane;
    @FXML
    private Pane controlPane;
    @FXML
    private SplitPane rootPane;
    private HabitatModel habitatModel;
    private boolean startFlag;
    private long startTime;
    private Timer timer;

    public OctagonController() {
    }

    public void setHabitatModel(HabitatModel model) {
        this.habitatModel = model;
    }

    @FXML
    private void handleCloseRequest() {
        this.timer.cancel();
    }

    @FXML
    private void setActionOnKey(KeyEvent keyEvent) {
        KeyCode pressedKey = keyEvent.getCode();
        if (pressedKey == KeyCode.B && !this.startFlag) {
            System.out.println("Pressed B");
            this.imagePane.getChildren().removeIf((node) -> {
                return node instanceof ImageView;
            });
            this.startSimulation();
        } else if (pressedKey == KeyCode.E && this.startFlag) {
            this.stopSimulation();
            System.out.println("Simulation has stopped");
        } else if (pressedKey == KeyCode.T) {
            this.showLabel(this.timeLabel);
        }

    }

    private void startSimulation() {
        startTime=System.currentTimeMillis();
        startFlag=true;
        statisticsLabel.setVisible(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(()->{
                    long simulationTime = (System.currentTimeMillis()-startTime)/1000+1;
                    try {
                        System.out.println("Trying to update");
                        update(simulationTime);
                    } catch (FileNotFoundException exception) {
                        throw new RuntimeException(exception);
                    }
                });
            };
        },0,1000);
    }

    private void stopSimulation() {
        this.timer.cancel();
        this.timer.purge();
        this.timer = new Timer();
        this.refreshStatisticsLabel();
        this.showLabel(this.statisticsLabel);
        this.habitatModel.clearFighterList();
        this.startFlag = false;
    }

    private void showLabel(Label label) {
        boolean isLabelVisible = label.isVisible();
        label.setVisible(!isLabelVisible);
        System.out.println(label.getText());
    }

    private void update(long time) throws FileNotFoundException {
        this.refreshImagePaneChildren(time);
        this.refreshTimeLabel(time);
    }

    private void refreshImagePaneChildren(long time) throws FileNotFoundException {
        double xBound = this.imagePane.getWidth() - 120.0;
        double yBound = this.imagePane.getHeight() - 120.0;
        int hasbullaSpawnTime = this.habitatModel.getHasbulaSpawnTime();
        int abdurozikSpawnTime = this.habitatModel.getAbdurizikSpawnTime();
        short hasbullaSpawnChance = this.habitatModel.getHasbulaSpawnChance();
        short abdurozikSpawnChance = this.habitatModel.getAbdurizikSpawnChance();
        Random randomGenerator = new Random();
        int generatedDigit = randomGenerator.nextInt(100);
        Fighter createFighter;
        if (time % (long)hasbullaSpawnTime == 0L && generatedDigit < hasbullaSpawnChance) {
            createFighter = this.habitatModel.createFighter(xBound, yBound, Hasbulla.class);
            this.imagePane.getChildren().add(createFighter.getView());
        }

        if (time % (long)abdurozikSpawnTime == 0L && generatedDigit < abdurozikSpawnChance) {
            createFighter = this.habitatModel.createFighter(xBound, yBound, Abdurozik.class);
            this.imagePane.getChildren().add(createFighter.getView());
        }

    }

    private void refreshTimeLabel(long time) {
        this.timeLabel.setText("Simulation time: " + time);
        System.out.println("Simulation time: " + time);
    }

    private void refreshStatisticsLabel() {
        long hasbullaAmount = this.habitatModel.getFighterAmount(Hasbulla.class);
        long abdurozikAmount = this.habitatModel.getFighterAmount(Abdurozik.class);
        this.statisticsLabel.setText("Hasbulla'sCount: " + hasbullaAmount + "\nAbdurozik's count: " + abdurozikAmount);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.startFlag = false;
        this.timer = new Timer();
    }
}