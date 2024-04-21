package com.example.magnetai;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class FxController {


    public static String polarity = null;
    @FXML
    public ChoiceBox chargeChoiceBox;
    @FXML
    public Button saveButton;
    @FXML
    public Button startButton;
    @FXML
    public Button resetButton;
    @FXML
    public Button pauseButton;
    @FXML
    public TextField velocityTextField;
    @FXML
    public TextField velocityTextFieldY;
    @FXML
    public Slider velocitySlider;
    @FXML
    public Slider velocitySliderY;
    @FXML
    public TextField inwardTextField;
    @FXML
    public TextField outwardTextField;
    @FXML
    public TextField mutationRateTextField;
    @FXML
    public TextField inputLayersTextField;
    @FXML
    public TextField simulationsTextField;
    @FXML
    public TextField generationCounterTextField;
    @FXML
    public TextField strengthTextField;
    @FXML
    public Slider strengthSlider;
    @FXML
    public MenuBar menuBar;
    @FXML
    public BorderPane borderPane;
    Charge c1;
    SimulationDisplay s1 = new SimulationDisplay();
    @FXML
    private Label welcomeText;
    @FXML
    private ScrollPane simDisplayPane;
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

        handle();
        createDisplay();


    }

    public void handle() {

        startButton.setOnAction(event -> {
            if(s1.findFinish() != null || s1.findCharge()!=null){
                s1.saveDesign();
                s1.showAllSimulations();
                velocitySlider.setDisable(true);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"Please choose place a Charge and a FinishLine on your map!", ButtonType.OK);
                alert.show();
            }
        });
        resetButton.setOnAction(actionEvent -> {
            s1.emptyDisplay();

        });
        pauseButton.setOnAction(actionEvent -> {
            for (Simulation sim : Simulation.getSimulationList()) {
                sim.getTimerInstance().stop();
            }


        });
        saveButton.setOnAction(actionEvent -> {


        });
        chargeChoiceBox.getItems().addAll("Proton", "Electron");
        chargeChoiceBox.setOnAction(actionEvent -> {
            polarity = (chargeChoiceBox.getValue() == null) ? null : chargeChoiceBox.getValue().toString();
        });
        velocitySlider.valueProperty().addListener((observableValue, newValue, OldValue) -> {
            s1.findCharge().getVelocity()[0] = newValue.doubleValue();
            s1.findCharge().getVelocity()[1] = newValue.doubleValue();
            velocityTextField.setText(newValue.toString());
        });
        strengthSlider.valueProperty().addListener((observableValue, newValue, OldValue) -> {
            // = newValue.doubleValue();
            strengthTextField.setText(newValue.toString());
        });


    }

    public void createDisplay() {
        HBox root = new HBox();
        root.setSpacing(Simulation.GRID_SIZE_X * Simulation.SQUARE_SIZE);
        simDisplayPane.setContent(root);
        VBox vb1 = new VBox();
        root.getChildren().addAll(vb1);
        vb1.getChildren().add(s1.getSimPane());
        //	Charge c1 = new Charge(ChargeType.NEGATIVE);
        //	s1.addToMap(c1, 5);
        for (int i = 0; i < 10; i++) {
            Simulation s2 = new Simulation();

        }


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
            s1.setSelectedComponentType(Obstacle.TYPE);
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

