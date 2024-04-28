package com.example.magnetai;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


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
    @FXML
    public TextField sizeYTextField;
    @FXML
    public TextField sizeXTextField;
    Charge c1;
    SimulationDisplay s1;
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
    private Rectangle eraserSelector;
    private double speed=0;
    private int simulationSize = 0;
    
    public static double[] dimension = new double[2];

    @FXML
    public void initialize() {

        handle();
//        simDisplayPane.widthProperty().addListener((observable, oldValue, newValue) -> {
//            double width = newValue.doubleValue();
//            System.out.println("Pane width: " + width);
//        });
//        simDisplayPane.heightProperty().addListener((observable, oldValue, newValue) -> {
//            double height = newValue.doubleValue();
//            System.out.println("Pane height: " + height);
//        });


    }

    public void handle() {
        startButton.setOnAction(event -> {
            if(s1.findFinish() != null && s1.findCharge()!=null && s1.checkValidPathDisplay()){
                s1.findCharge().setSpeed(speed);
                    s1.saveDesign();
                    s1.showAllSimulations((Stage)startButton.getScene().getWindow());
                    velocitySlider.setDisable(true);
            }
            else if(s1.findFinish() == null || s1.findCharge()==null){
                Alert alert = new Alert(Alert.AlertType.ERROR,"Please place a Charge and a FinishLine on your map! " ,
                        ButtonType.OK);
                alert.initOwner(startButton.getScene().getWindow());
                alert.show();
            }else if (!s1.checkValidPathDisplay()) {
                Alert noPathAlert = new Alert(Alert.AlertType.ERROR, "Make sure there is a path from the charge to the finish line!", ButtonType.OK);
                noPathAlert.initOwner(startButton.getScene().getWindow());
                noPathAlert.show();
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
            simulationSize = Integer.parseInt(simulationsTextField.getText());
            Simulation.GRID_SIZE_X = Integer.parseInt(sizeXTextField.getText());
            Simulation.GRID_SIZE_Y = Integer.parseInt(sizeYTextField.getText());
            int xPadding = 20;
            Simulation.dimensions[0]=simDisplayPane.getWidth() - 2 * xPadding;
            Simulation.dimensions[1]=simDisplayPane.getHeight();
            simDisplayPane.setAlignment(Pos.TOP_LEFT);
            Simulation.SQUARE_SIZE=Simulation.calculateDisplayScale();
            double gridHeight = Simulation.GRID_SIZE_Y * Simulation.SQUARE_SIZE;
            double padding = (simDisplayPane.getHeight()-gridHeight)/2;
            Charge.CHARGE_RADIUS=Simulation.calculateDisplayScale()/4;
            simDisplayPane.setStyle("-fx-background-color: #808080;");
            createDisplay();
            s1.bckg();
            s1.getSimPane().setTranslateY(padding);
            simDisplayPane.setPadding(new Insets(0,0,0,xPadding));
            s1.addClickable();
            startButton.setDisable(false);
            resetButton.setDisable(false);
            pauseButton.setDisable(false);
        });
        chargeChoiceBox.getItems().addAll("Proton", "Electron");
        chargeChoiceBox.setOnAction(actionEvent -> {
            polarity = (chargeChoiceBox.getValue() == null) ? null : chargeChoiceBox.getValue().toString();
        });
        velocitySlider.valueProperty().addListener((observableValue, newValue, oldValue) -> {
            speed=(newValue.doubleValue());
            velocityTextField.setText(newValue.toString());
        });
        strengthSlider.valueProperty().addListener((observableValue, newValue, oldValue) -> {
            // = newValue.doubleValue();
            strengthTextField.setText(newValue.toString());
        });
        
        
    }
    
    public void createDisplay(){
        s1 = new SimulationDisplay();
        
        // root.setSpacing(Simulation.GRID_SIZE_X * Simulation.SQUARE_SIZE);
        simDisplayPane.getChildren().add(s1.getSimPane());
        System.out.println(simulationSize);
        for (int i = 0; i < simulationSize; i++) {
            new Simulation();
            
        }
//        VBox vb1 = new VBox();
//        root.getChildren().addAll(vb1);
//      //   scaleDisplay(s1);
//        vb1.getChildren().add();
        
        //	Charge c1 = new Charge(ChargeType.NEGATIVE);
        //	s1.addToMap(c1, 5);
        
        // Create an object property to hold the selected rectangle
        ObjectProperty<Shape> selectedShape = new SimpleObjectProperty<>();
        Circle selected = new Circle(50);
        selected.setStrokeWidth(3);
        // Bind the fill property of the circle to the fill property of the selected rectangle
        selected.fillProperty().bind(Bindings.createObjectBinding(() ->
                selectedShape.get() != null ? selectedShape.get().getFill() : Color.BLACK, selectedShape));
        
        chargeSelector.setOnMouseClicked(event -> {
            s1.setSelectedComponentType(Charge.TYPE);
         //   System.out.println(Charge.TYPE);
            selectedShape.set(chargeSelector);
        });
        obstacleSelector.setOnMouseClicked(event -> {
            s1.setSelectedComponentType(Obstacle.TYPE);
           // System.out.println(Obstacle.TYPE);
            selectedShape.set(obstacleSelector);
        });
        fLSelector.setOnMouseClicked(event -> {
            s1.setSelectedComponentType(FinishLine.TYPE);
           // System.out.println(FinishLine.TYPE);
            selectedShape.set(fLSelector);
        });
        sCSelector.setOnMouseClicked(event -> {
            s1.setSelectedComponentType(Superconductor.TYPE);
           // System.out.println(Superconductor.TYPE);
            selectedShape.set(sCSelector);
        });
        
        eraserSelector.setOnMouseClicked(mouseEvent ->{
            s1.setSelectedComponentType("eraser");
            selectedShape.set(eraserSelector);
            
        });
    }
    
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    
}
