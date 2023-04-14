package OctagonApp.controller;

import OctagonApp.DialogWindow;
import OctagonApp.UniqueNumbGen;
import OctagonApp.model.*;

import java.io.FileNotFoundException;
import java.net.URL;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static javafx.util.Duration.millis;

public class OctagonController implements Initializable {
    @FXML
    private Label timeLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button objectsButton;
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
    private Spinner<Integer> hasbullaSpawnSpinner;
    @FXML
    private Spinner<Integer> hasbullaLifeSpinner;
    @FXML
    private Spinner<Integer> abdurozikSpawnSpinner;
    @FXML
    private Spinner<Integer> abdurozikLifeSpinner;
    @FXML
    private ComboBox<String> hasbullaBox;
    @FXML
    private ComboBox<String> abdurozikBox;
    @FXML
    private CheckBox checkBox;
    private Duration simulationTime;
    @FXML
    private ToggleGroup timeRadioGroup;

    private HabitatModel habitatModel;
    private boolean startFlag;

    private Timer timer;
    private TimerTask simulationTask;
    private UniqueNumbGen genNumb;

    public OctagonController() {
    }

    public void setHabitatModel(HabitatModel model) {
        this.habitatModel = model;
    }

    @FXML
    private void handleCloseRequest() {
        timer.cancel();
    }

    @FXML
    private void setHasbullaSpawnChance() {
        habitatModel.setHasbulaSpawnChance(Short.valueOf(hasbullaBox.getValue()));
    }

    @FXML
    private void setAbdurozikSpawnChance() {
        habitatModel.setAbdurizikSpawnChance(Short.valueOf(abdurozikBox.getValue()));
    }
    @FXML
    private void setActionOnKey(KeyEvent keyEvent) throws FileNotFoundException {
        KeyCode pressedKey = keyEvent.getCode();
        if (pressedKey==KeyCode.B && !startFlag) {
            System.out.println("Started simulating");
            startButton.fire();
        }
        else if (pressedKey==KeyCode.E && startFlag){
            stopButton.fire();
            System.out.println("Simulation has stopped");
        }
        else if (pressedKey==KeyCode.T){
            timeRadioGroup.selectToggle(timeLabel.isVisible() ? timeRadioGroup.
                    getToggles().get(1) : timeRadioGroup.getToggles().get(0));
        }
    }

    private void startSimulation() {
        Platform.runLater(()->imagePane.getChildren().removeIf(node -> (node instanceof ImageView)));
        simulationTime = Duration.ZERO;
        startFlag = true;
        statisticsLabel.setVisible(false);
        timer.schedule(simulationTask,0,1000);
    }

    private void initTimerTask(){
        simulationTask = new TimerTask() {
            @Override
            public void run() {
                if (startFlag) {
                    simulationTime = simulationTime.add(Duration.seconds(1));
                    try {
                        update((long)simulationTime.toSeconds());
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    private void stopSimulation() throws FileNotFoundException {
        if (checkBox.isSelected()) {
            //timer.pause();
            startFlag=false;
            refreshStatisticsLabel();
            ImageView hasbulaView = new Hasbulla().getView();
            ImageView abdurozikView = new Abdurozik().getView();
            System.out.println(statisticsLabel.getText());
            DialogWindow<ButtonType> window = new DialogWindow<ButtonType>(DialogWindow.DialogType.STATISTICS,
                    statisticsLabel.getText(),hasbulaView,abdurozikView);
            window.initOwner(mainStage);
            if (window.showAndWait().get()==ButtonType.OK){
                stopAndClear();
            }
            else {
                startFlag=true;
            }
        }
        else {
            refreshStatisticsLabel();
            stopAndClear();
        }
    }

    private void stopAndClear() {
        showLabel(statisticsLabel);
        habitatModel.getFighterData().clearData();
        startFlag=false;
        simulationTask.cancel();
        initTimerTask();
    }


    private void initButtons() {
        startButton.setOnAction(actionEvent -> {
            startSimulation();
            startButton.setDisable(true);
            stopButton.setDisable(false);
        });
        stopButton.setOnAction(actionEvent -> {
            try {
                stopSimulation();
                if (!startFlag){
                    startButton.setDisable(false);
                    stopButton.setDisable(true);
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        objectsButton.setOnAction(actionEvent -> {
            if (startFlag) {
                startFlag=false;
            }
            DialogWindow<ButtonType> window = null;
            try {
                window = new DialogWindow<ButtonType>(DialogWindow.DialogType.OBJECTS);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            window.initOwner(mainStage);
            window.showAndWait();
            if (!startFlag){
                startFlag=true;
            }
        });
    }

    private void initRadButtons() {
        ObservableList<Toggle> toggles = timeRadioGroup.getToggles();
        showLabel(timeLabel);
        toggles.get(0).selectedProperty().addListener(observable -> {
            timeLabel.setVisible(true);
        });
        toggles.get(1).selectedProperty().addListener(observable -> {
            timeLabel.setVisible(false);
        });
    }

    private void initSpinners() {
        hasbullaSpawnSpinner.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            habitatModel.setHasbulaSpawnTime(newValue);
        });
        hasbullaLifeSpinner.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            habitatModel.setHasbullaLifeTime(newValue);
        });
        abdurozikSpawnSpinner.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            habitatModel.setAbdurizikSpawnTime(newValue);
        });
        abdurozikLifeSpinner.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            /*System.out.println(habitatModel.getAbduzorikLifeTime());*/
            habitatModel.setAbduzorikLifeTime(newValue);
            /*System.out.println(habitatModel.getAbduzorikLifeTime());*/
        });
        Stream.of(abdurozikLifeSpinner, abdurozikSpawnSpinner, hasbullaLifeSpinner, hasbullaSpawnSpinner)
                .forEach(spinner -> spinner.getEditor().setTextFormatter(new TextFormatter<Integer>(
                        change -> {
                            String input = change.getControlNewText();
                            if (input.matches("[0-9]*")) {
                                try {
                                    int value = Integer.parseInt(input);
                                    if (value > 0 && value <= 1000) {
                                        return change;
                                    }
                                } catch (NumberFormatException exception) {
                                }
                            }
                            return null;
                        })));
    }

    private void showLabel(Label label) {
        boolean isLabelVisible = label.isVisible();
        label.setVisible(!isLabelVisible);
        System.out.println(label.getText());
    }

    private void update(long time) throws FileNotFoundException {
        refreshImagePaneChildren(time);
        refreshTimeLabel(time);
        funeralFoo(time);
        System.out.println("StartFlag"+startFlag);
    }

    private void funeralFoo(long time) {   //похороны :)(:
        FighterData fishData = habitatModel.getFighterData();
        ObservableList<Node> imageViews = imagePane.getChildren();
        LinkedList<Fighter> deadFish = habitatModel.getFighterData().fighterList.stream().filter(obj -> {
            int lifeTime = (obj instanceof Hasbulla) ?
                    habitatModel.getHasbullaLifeTime() : habitatModel.getAbduzorikLifeTime();
            return (obj.getBirthTime() + lifeTime <= time);
        }).collect(Collectors.toCollection(LinkedList::new));
        deadFish.forEach(obj -> {
            int objId = obj.getId();
            fishData.idSet.remove(objId);
            fishData.birthTimeTree.remove(objId);
            fishData.fighterList.remove(obj);
            imageViews.remove(obj.getView());
        });
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
        if ((time % hasbullaSpawnTime == 0) && (generatedDigit < hasbullaSpawnChance)) {
            Fighter spawnedHasbulla = habitatModel.createFighter(xBound, yBound, genNumb.getNext(),
                    (int) this.simulationTime.toSeconds(), Hasbulla.class);
            Platform.runLater(()->imagePane.getChildren().add(spawnedHasbulla.getView()));
        }
        if ((time % abdurozikSpawnTime == 0) && (generatedDigit < abdurozikSpawnChance)) {
            Fighter spawnedAbdurozik = habitatModel.createFighter(xBound, yBound, genNumb.getNext(),
                    (int) this.simulationTime.toSeconds(), Abdurozik.class);
            Platform.runLater(()->imagePane.getChildren().add(spawnedAbdurozik.getView()));
        }
    }

    private void refreshTimeLabel(long time) {
        long goldAmount = habitatModel.getFighterAmount(Abdurozik.class);
        long guppyAmount = habitatModel.getFighterAmount(Hasbulla.class);
    }

    private void refreshStatisticsLabel() {
        long hasbullaAmount = this.habitatModel.getFighterAmount(Hasbulla.class);
        long abdurozikAmount = this.habitatModel.getFighterAmount(Abdurozik.class);
        long fightersAmount = hasbullaAmount + abdurozikAmount;
        statisticsLabel.setText("Hasbulla's count: " + hasbullaAmount + "\nAbdurozik's count: " + abdurozikAmount +
                "\nFighters count: " + fightersAmount);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer = new Timer();
        initTimerTask();
        initSpinners();
        initRadButtons();
        initButtons();
        this.startFlag = false;
        genNumb = new UniqueNumbGen(0, 1000000);
        ObservableList<String> boxChoices = IntStream.rangeClosed(0, 100).filter(i -> i % 10 == 0).mapToObj(
                Integer::toString).collect(Collectors.toCollection(FXCollections::observableArrayList));
        Stream.of(hasbullaBox, abdurozikBox).peek(box -> box.setItems(boxChoices)).forEach(box ->
                box.getSelectionModel().select(10));
    }
}