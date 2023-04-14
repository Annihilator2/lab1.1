package OctagonApp;

import OctagonApp.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DialogWindow<T> extends Dialog<T> {

    private Text text;

    public DialogWindow(DialogType dialogType) throws FileNotFoundException {
        this(dialogType, "", new ImageView(), new ImageView());
    }

    public DialogWindow(DialogType dialogType, String contentText, ImageView firstView, ImageView secondView) throws FileNotFoundException {
        DialogPane dialogPane = this.getDialogPane();
        this.getDialogPane().setPrefWidth(600);
        this.getDialogPane().setPrefHeight(300);
        switch (dialogType) {
            case STATISTICS:
                String[] contentLines = contentText.split("\n");
                Label firstLabel = new Label(contentLines[0]);
                Label secondLabel = new Label(contentLines[1]);
                Stream.of(firstLabel, secondLabel).forEach(label -> label.setStyle("-fx-font-size: 16; -fx-font-weight: bold;"));
                Stream.of(firstView, secondView).forEach(view -> view.setFitHeight(29));
                HBox firstBox = new HBox(firstView, firstLabel);
                HBox secondBox = new HBox(secondView, secondLabel);
                VBox contentBox = new VBox(firstBox, secondBox);
                this.initModality(Modality.WINDOW_MODAL);
                this.getDialogPane().setContent(contentBox);
                this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                this.getDialogPane().setPrefWidth(contentBox.getPrefWidth());
                this.getDialogPane().setPrefHeight(contentBox.getPrefHeight());
                this.setTitle("Statistics window");
                break;
            case OBJECTS:
                ObservableList<Fighter> fighterList = FXCollections.observableList(FighterData.getInstance().fighterList);
                TableView<Fighter> table = new TableView<>(fighterList);
                TableColumn<Fighter, String> classColumn = new TableColumn<>("class");
                TableColumn<Fighter, Integer> xColumn = new TableColumn<>("xAxis coordinate");
                TableColumn<Fighter, Integer> yColumn = new TableColumn<>("yAxis coordinate");
                TableColumn<Fighter, Integer> idColumn = new TableColumn<>("id");
                TableColumn<Fighter, Integer> birthTimeColumn = new TableColumn<>("birthTime");
                classColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getClass().getSimpleName()));
                xColumn.setCellValueFactory(new PropertyValueFactory<>("xCoord"));
                yColumn.setCellValueFactory(new PropertyValueFactory<>("yCoord"));
                idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                birthTimeColumn.setCellValueFactory(new PropertyValueFactory<>("birthTime"));
                table.getColumns().addAll(classColumn, xColumn, yColumn, idColumn, birthTimeColumn);
                dialogPane.setContent(table);
                dialogPane.getButtonTypes().addAll(ButtonType.CLOSE);
                this.initModality(Modality.WINDOW_MODAL);
                this.setTitle("Table of objects");
                break;
            case CONSOLE:
                TextArea consoleField = new TextArea();
                consoleField.setStyle("-fx-background-color: transparent; -fx-text-fill: white; " + "-fx-font-size: 16; -fx-control-inner-background: black; -fx-font-weight: bold");
                this.getDialogPane().setContent(consoleField);
                this.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                this.setTitle("Console window");
        }
    }

    public enum DialogType {
        STATISTICS,
        OBJECTS,
        CONSOLE;

        DialogType() {

        }
    }
}