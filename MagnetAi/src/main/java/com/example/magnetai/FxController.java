package com.example.magnetai;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    public Button recommendedButton;
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
    public TextField HiddenLayersTextField;
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
    public MenuItem closeButtonMenuBar;
    public MenuItem deleteButtonMenuBar;
    public MenuItem aboutButtonMenuBar;
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
    private double speed = 0;
    private int simulationSize = 0;
    
    public static double[] dimension = new double[2];
    
    /**
     *
     */
    @FXML
    public void initialize() {
        handle();
        setupMenuBar();
    }
    
    /**
     *
     */
    private void setupMenuBar() {
        closeButtonMenuBar.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        deleteButtonMenuBar.setOnAction(actionEvent -> {
            s1.emptyDisplay();
            simDisplayPane.getChildren().remove(s1.getSimPane());
        });
        aboutButtonMenuBar.setOnAction(actionEvent -> showaboutpage());
    }
    
    /**
     *
     */
    private void showaboutpage(){
        Text text1 = new Text(30, 50, "About");
        text1.setFont(new Font("SansSerif",40));
        String text = """
                The goal of this project is to demonstrate the phenomena of magnetic fields by displaying how magnetic fields 
                are able to redirect the direction of charges. A charge (shown in red in the following visual representation) 
                will have an initial velocity.e goal of the program is to get the charge to the finish line (in yellow). We will 
                also implement superconductors (shown in blue) which are squares that are not obstacles for the charge, but for 
                the magnetic fields. This means that on blue squares, it is impossible to place a magnetic field, but the electron 
                will not be affected by them.We will also implement an Ai that chooses the location of the magnetic fields and try 
                to get the best arrangement of the two types of magnetic fields (inwards or outwards) to get the electron as close
                as possible to the finish line.
                """;
        Text text2 = new Text(30, 100, text);
        text2.setFont(new Font("SansSerif",17));
        Text text3 = new Text(30, 200, "Team Members: Elyes Bradai, Ziad Agha, Amine Ait Yakoub");
        text3.setFont(new Font("SansSerif",10));
        VBox vbox = new VBox(text1, text2,text3);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 1200, 600);
        Stage aboutPageStage = new Stage();
        aboutPageStage.setTitle("About Page");
        aboutPageStage.setScene(scene);
        aboutPageStage.show();
    }

    /**
     *
     */
    public void handle() {
        startButton.setOnAction(event -> {
            if (s1.findFinish() != null && s1.findCharge() != null && s1.checkValidPathDisplay()){
                s1.findCharge().setSpeed(speed);
                s1.saveDesign();
                s1.showAllSimulations((Stage)startButton.getScene().getWindow());
                velocitySlider.setDisable(true);
                
            }
            else if (s1.findFinish() == null || s1.findCharge() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR,"Please place a Charge and a FinishLine on your map! " ,
                        ButtonType.OK);
                alert.initOwner(startButton.getScene().getWindow());
                alert.show();
            }
            else if (!s1.checkValidPathDisplay()) {
                Alert noPathAlert = new Alert(Alert.AlertType.ERROR, "Make sure there is a path from the charge to the finish line!",
                        ButtonType.OK);
                noPathAlert.initOwner(startButton.getScene().getWindow());
                noPathAlert.show();
            }
        });
        resetButton.setOnAction(actionEvent -> {
            s1.emptyDisplay();
            simDisplayPane.getChildren().remove(s1.getSimPane());
        });
        recommendedButton.setOnAction(actionEvent -> {
            chargeChoiceBox.setValue("Proton");
            velocitySlider.valueProperty().setValue(3.5);
            velocityTextField.setText("3.5");
            speed = 3.5;
            strengthSlider.setValue(1);
            strengthTextField.setText("1");
            MagneticField.STRENGTH_COEFFICIENT = 0.1;
            mutationRateTextField.setText("0.5");
            Simulation.mutationRate = 0.5f;
            HiddenLayersTextField.setText("n,n,8");
            simulationsTextField.setText("10");
            sizeXTextField.setText("8");
            sizeYTextField.setText("8");
        });
        saveButton.setOnAction(actionEvent -> {
            if (s1 != null) {
                s1.emptyDisplay();
                simDisplayPane.getChildren().remove(s1.getSimPane());
                Simulation.getSimulationList().clear();
            }
            simulationSize = Integer.parseInt(simulationsTextField.getText());
            Simulation.mutationRate = Float.valueOf(mutationRateTextField.getText());
            Simulation.GRID_SIZE_X = Integer.parseInt(sizeXTextField.getText());
            Simulation.GRID_SIZE_Y = Integer.parseInt(sizeYTextField.getText());
            int xPadding = 20;
            Simulation.dimensions[0] = simDisplayPane.getWidth() - 2 * xPadding;
            Simulation.dimensions[1] = simDisplayPane.getHeight();
            simDisplayPane.setAlignment(Pos.TOP_LEFT);
            Simulation.calculateSquareSize();
            double gridHeight = Simulation.GRID_SIZE_Y * Simulation.SQUARE_SIZE;
            double padding = (simDisplayPane.getHeight()-gridHeight)/2;
            simDisplayPane.setStyle("-fx-background-color: #808080;");
            Simulation.layerInput = setupLayers(HiddenLayersTextField.getText());
            s1 = new SimulationDisplay();
            simDisplayPane.getChildren().add(s1.getSimPane());
            createDisplay();
            s1.bckg();
            s1.getSimPane().setTranslateY(padding);
            simDisplayPane.setPadding(new Insets(0,0,0,xPadding));
            s1.addClickable();
            startButton.setDisable(false);
            resetButton.setDisable(false);
        });
        chargeChoiceBox.getItems().addAll("Proton", "Electron");
        chargeChoiceBox.setOnAction(actionEvent -> {
            polarity = (chargeChoiceBox.getValue() == null) ? null : chargeChoiceBox.getValue().toString();
        });
        velocitySlider.valueProperty().addListener((observableValue, newValue, oldValue) -> {
            speed = newValue.doubleValue();
            velocityTextField.setText(newValue.toString());
        });
        strengthSlider.valueProperty().addListener((observableValue, newValue, oldValue) -> {
            MagneticField.STRENGTH_COEFFICIENT = newValue.doubleValue() / 10;
            strengthTextField.setText(newValue.toString());
        });
    }

    /**
     *
     */
    public void createDisplay(){

        // Create an object property to hold the selected rectangle
        for (int i = 0; i < simulationSize; i++) {
            new Simulation();
        }
        ObjectProperty<Shape> selectedShape = new SimpleObjectProperty<>();
        Circle selected = new Circle(50);
        selected.setStrokeWidth(3);

        // Bind the fill property of the circle to the fill property of the selected rectangle
        selected.fillProperty().bind(Bindings.createObjectBinding(() ->
                selectedShape.get() != null ? selectedShape.get().getFill() : Color.BLACK, selectedShape));
        chargeSelector.setOnMouseClicked(event -> {
            s1.setSelectedComponentType(Charge.TYPE);
            selectedShape.set(chargeSelector);
        });
        obstacleSelector.setOnMouseClicked(event -> {
            s1.setSelectedComponentType(Obstacle.TYPE);
            selectedShape.set(obstacleSelector);
        });
        fLSelector.setOnMouseClicked(event -> {
            s1.setSelectedComponentType(FinishLine.TYPE);
            selectedShape.set(fLSelector);
        });
        sCSelector.setOnMouseClicked(event -> {
            s1.setSelectedComponentType(Superconductor.TYPE);
            selectedShape.set(sCSelector);
        });
        eraserSelector.setOnMouseClicked(mouseEvent ->{
            s1.setSelectedComponentType("eraser");
            selectedShape.set(eraserSelector);
            
        });
    }

    /**
     *
     * @param layerInput
     * @return
     */
    private int[] setupLayers(String layerInput) {
        try {
            String[] neuronsPerLayerArray = layerInput.split(",");
            int[] neuronsPerLayer = new int[neuronsPerLayerArray.length];

            // Convert the string values to integers
            for (int i = 0; i < neuronsPerLayerArray.length; i++) {
                if (neuronsPerLayerArray[i].trim().equals("n")) {
                    neuronsPerLayer[i] = Simulation.GRID_SIZE_Y * Simulation.GRID_SIZE_X;
                }
                else {
                    neuronsPerLayer[i] = Integer.parseInt(neuronsPerLayerArray[i].trim());
                }
            }
            return neuronsPerLayer;
        } catch (NumberFormatException ex) {
            // Handle the case where the input is not a valid integer
            System.err.println("Invalid input. Please enter valid integers for neurons per layer.");
        }
        return null;
    }
    
}
