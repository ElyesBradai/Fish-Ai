package com.example.magnetai;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class FxController {


    @FXML
    public Button saveButton;
    @FXML
    public Button startButton;
    @FXML
    public Button resetButton;
    @FXML
    public Button pauseButton;
    @FXML
    public TextField velocityTextFieldX;
    @FXML
    public TextField velocityTextFieldY;
    @FXML
    public Slider velocitySliderX;
    @FXML
    public Slider velocitySliderY;

    @FXML
    private Label welcomeText;
    @FXML
    private FlowPane simDisplayPane;
    @FXML
    private Circle chargeSelector;
    @FXML
    private Rectangle obstacleSelector;
    @FXML
    private Rectangle fLSelector;
    @FXML
    private Rectangle sCSelector;

    @FXML
    public void initialize() {

        simDisplayPane.setAlignment(Pos.CENTER);
        createDisplay();


    }

    public void createDisplay() {
        HBox root = new HBox();
        root.setSpacing(Simulation.GRID_SIZE_X * Simulation.SQUARE_SIZE);
        simDisplayPane.getChildren().add(root);
        VBox vb1 = new VBox();
        root.getChildren().addAll(vb1);
        SimulationDisplay s1 = new SimulationDisplay();
        vb1.getChildren().add(s1.getSimPane());
        Charge c1 = new Charge(1, ChargeType.POSITIVE);
        s1.addToMap(c1, 6);
        Simulation s2 = new Simulation();

        // Create an object property to hold the selected rectangle
        ObjectProperty<Shape> selectedShape = new SimpleObjectProperty<>();
        Circle selected = new Circle(50);
        selected.setStrokeWidth(3);
        // Bind the fill property of the circle to the fill property of the selected rectangle
        selected.fillProperty().bind(Bindings.createObjectBinding(() ->
                selectedShape.get() != null ? selectedShape.get().getFill() : Color.BLACK, selectedShape));


        chargeSelector.setOnMouseClicked(event -> {
            s1.setSelectedComponentType(Charge.TYPE);
            System.out.println(Charge.TYPE);
            selectedShape.set(chargeSelector);
        });
        obstacleSelector.setOnMouseClicked(event -> {
            s1.setSelectedComponentType(MagneticField.TYPE);
            System.out.println(Obstacle.TYPE);
            selectedShape.set(obstacleSelector);
        });
        fLSelector.setOnMouseClicked(event -> {
            s1.setSelectedComponentType(FinishLine.TYPE);
            System.out.println(FinishLine.TYPE);
            selectedShape.set(fLSelector);
        });
        sCSelector.setOnMouseClicked(event -> {
            s1.setSelectedComponentType(Superconductor.TYPE);
            System.out.println(Superconductor.TYPE);
            selectedShape.set(sCSelector);
        });

        startButton.setOnAction(event -> {
            s1.saveDesign();
            s1.showAllSimulations();
        });
//        VBox selectorBox = new VBox(ObstacleSelector,FLSelector,SCSelector,selected,b1);
//        root.getChildren().add(selectorBox);
//        Scene scene = new Scene(root, 1200, 800);
//        Stage stage = new Stage();
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

}

