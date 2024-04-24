package com.example.magnetai;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.io.IOException;


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
    private FlowPane simDisplayPane;
    @FXML
    private Circle chargeSelector;
    @FXML
    private Rectangle obstacleSelector;
    @FXML
    private Rectangle fLSelector;
    @FXML
    private Rectangle sCSelector;
    
    public static double[] dimension = new double[2];

    @FXML
    public void initialize() {

        handle();
        createDisplay();
        simDisplayPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            System.out.println("Pane width: " + width);
        });
        simDisplayPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            System.out.println("Pane height: " + height);
        });


    }

    public void handle() {
        startButton.setOnAction(event -> {
            if(s1.findFinish() != null && s1.findCharge()!=null && s1.checkValidPathDisplay()){
                    s1.saveDesign();
                    s1.showAllSimulations();
                    velocitySlider.setDisable(true);
                    Stage stage = (Stage) startButton.getScene().getWindow();
                    stage.close();

            }else if (!s1.checkValidPathDisplay()) {
                Alert noPathAlert = new Alert(Alert.AlertType.ERROR, "Make sure there is a path from the charge to the finish line!", ButtonType.OK);
                noPathAlert.show();}
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Please choose place a Charge and a FinishLine on your map! " +
                        "Also make sure there is a path from the charge to the finish line!",
                        ButtonType.OK);
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
            s1.findCharge().setSpeed(newValue.doubleValue());
            velocityTextField.setText(newValue.toString());
        });
        strengthSlider.valueProperty().addListener((observableValue, newValue, OldValue) -> {
            // = newValue.doubleValue();
            strengthTextField.setText(newValue.toString());
        });
    }

    public void createDisplay() {
        HBox root = new HBox();
       // root.setSpacing(Simulation.GRID_SIZE_X * Simulation.SQUARE_SIZE);
        simDisplayPane.setAlignment(Pos.TOP_LEFT);
        simDisplayPane.getChildren().add(root);
        VBox vb1 = new VBox();
        root.getChildren().addAll(vb1);
      //   scaleDisplay(s1);
        vb1.getChildren().add(s1.getSimPane());

        //	Charge c1 = new Charge(ChargeType.NEGATIVE);
        //	s1.addToMap(c1, 5);
        for (int i = 0; i < 30; i++) {
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
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    
//    public void scaleDisplay(Simulation sim){
//            double ratioSquareSize = Simulation.caculateDisplayScale();
//            for (int j = 0; j < sim.getSquareList().size(); j++) {
//              Rectangle temp = sim.getSquareList().get(j);
//              temp.setHeight(temp.getHeight() * ratioSquareSize);
//              temp.setWidth(temp.getWidth() * ratioSquareSize);
//
//               // System.out.println(temp.getHeight() + " " + temp.getWidth());
//               //   System.out.println(ratioSquareSize);
//
//                temp.setTranslateX(temp.getTranslateX() * ratioSquareSize);
//                temp.setTranslateY(temp.getTranslateY() * ratioSquareSize);
//
//        }
//
//
//
//    }
}

