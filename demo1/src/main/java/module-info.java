module OctagonApp {
    requires javafx.controls;
    requires javafx.fxml;

    exports OctagonApp;
    opens OctagonApp to javafx.graphics;
    exports OctagonApp.model;
    opens OctagonApp.model to javafx.fxml;
    exports OctagonApp.controller;
    opens OctagonApp.controller to javafx.fxml;
}