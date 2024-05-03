package com.example.magnetai;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SimulationDisplay extends Simulation {
    private String selectedComponentType;
    private boolean isScaled;
    
    /**
     *
     */
    public SimulationDisplay() {
        super();
        selectedComponentType = "";
        isScaled = false;
    }

    /**
     * Makes the simulation display clickable and editable to add the components
     */
    public void addClickable() {
        for (Rectangle square : squareList) {
            square.setOnMouseClicked(event -> {
                int index = posToIndex(absolutePosToGridPosDisplay(square.getTranslateX(), square.getTranslateY()));
                switch (this.selectedComponentType) {
                    case Charge.TYPE -> {
                        if (FxController.polarity != null) {
                            Charge c1 = (FxController.polarity.equals("Proton"))
                                    ? new Charge(index, ChargeType.POSITIVE) : new Charge(index, ChargeType.NEGATIVE);
                            this.addToMap(c1, c1.getIndex());
                        }

                    }
                    case Obstacle.TYPE -> {
                        Obstacle obs = new Obstacle(index);
                        this.addToMap(obs, obs.getIndex());

                    }
                    case FinishLine.TYPE -> {
                        FinishLine finish = new FinishLine(index);
                        this.addToMap(finish, finish.getIndex());

                    }
                    case Superconductor.TYPE -> {
                        Superconductor sup = new Superconductor(index);
                        this.addToMap(sup, sup.getIndex());

                    }
                    case MagneticField.TYPE -> {
                        MagneticField mag = new MagneticField(index, new double[]{0, 0, 0.06});
                        this.addToMap(mag, mag.getIndex());
                    } case "eraser" -> {
                        int[] pos = indexToPos(index);
                        if(this.map[pos[0]][pos[1]] != null){
                            simPane.getChildren().remove(this.map[pos[0]][pos[1]].getBody());
                            this.map[pos[0]][pos[1]] = null;
                        }
                    }
                    default -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR,"Please select a valid component before editing the map" ,
                                ButtonType.OK);
                        alert.show();
                    }
                }
            });
        }
    }

    /**
     * copies the design from the display and paste it into all simulations
     */
    public void saveDesign() {
        for (Simulation sim : Simulation.getSimulationList()) {
            Component[][] copiedMap = new Component[map.length][map[0].length];
            
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    // Perform deep copy of each Component object
                    if (map[i][j] != null) {
                        copiedMap[i][j] = map[i][j].clone();
                        sim.addToMap(copiedMap[i][j], copiedMap[i][j].getIndex());
                    }
                }
            }
            sim.map = copiedMap;
        }
        createBrains();
    }

    /**
     * opens a new stage with all the simulations running
     */
    public void showAllSimulations(Stage stage) {
        //This method opens a window with all the simulations with copied map
        double scale = calculateScale();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double screenWidth = bounds.getWidth();
        double screenHeight = bounds.getHeight();
        Simulation.getSimulationList().remove(this);
        root = new FlowPane();
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(false);
        scrollPane.setFitToHeight(false);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPrefWidth(screenWidth);
        root.setPrefHeight(screenHeight+(GRID_SIZE_Y * SQUARE_SIZE + SQUARE_SIZE) * scale);
        root.setPadding(new Insets(10,GRID_SIZE_X*SQUARE_SIZE * scale,GRID_SIZE_Y*SQUARE_SIZE * scale,GRID_SIZE_X * SQUARE_SIZE*scale));
        
        for (Simulation sim : Simulation.getSimulationList()) {
            root.getChildren().add(sim.getSimPane());
        }
        root.setHgap((GRID_SIZE_X * SQUARE_SIZE + SQUARE_SIZE) * scale);
        root.setVgap((GRID_SIZE_Y * SQUARE_SIZE + SQUARE_SIZE) * scale);
        root.setPrefWrapLength(screenWidth);

        //looping through each component to scale them down
        for (Simulation sim : Simulation.getSimulationList()) {
            for (Rectangle square : sim.squareList) {
                if (!isScaled) {
                    square.setWidth(square.getWidth() * scale);
                    square.setHeight(square.getHeight() * scale);
                    square.setTranslateX(square.getTranslateX() * scale);
                    square.setTranslateY(square.getTranslateY() * scale);
                }
            }
            for (Component[] row : sim.map) {
                for (Component component : row) {
                    if (component != null) {
                        switch (component.getType()) {
                            case Obstacle.TYPE , Superconductor.TYPE , FinishLine.TYPE -> {
                                ((Rectangle) component.getBody()).setWidth(((Rectangle) component.getBody()).getWidth() * scale);
                                ((Rectangle) component.getBody()).setHeight(((Rectangle) component.getBody()).getHeight() * scale);
                                component.getBody().setTranslateX(component.getBody().getTranslateX() * scale);
                                component.getBody().setTranslateY(component.getBody().getTranslateY() * scale);
                                component.getBody().setStrokeWidth(component.getBody().getStrokeWidth() * scale);
                            }
                            case "charge" -> {
                                ((Circle) component.getBody()).setRadius(((Circle) component.getBody()).getRadius() * scale);
                                component.getBody().setTranslateX(component.getBody().getTranslateX() * scale);
                                component.getBody().setTranslateY(component.getBody().getTranslateY() * scale);

                            }
                        }
                    }
                }
            }
        }
        for (Simulation sim : Simulation.simulationList) {
            for (Component[] row : sim.map) {
                for (Component charge : row) {
                    if (charge != null && charge.getType().equals(Charge.TYPE)) {
                        charge.getBody().toFront();
                    }
                }
            }
        }
        isScaled = true;
        this.getTimerInstance().start();
        Scene scene = new Scene(scrollPane,600,600);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        stage.setTitle("Magnet Ai: Training simulations");
        stage.setScene(scene);
        stage.setFullScreen(true);
    }

    public void setSelectedComponentType(String selectedComponentType) {
        this.selectedComponentType = selectedComponentType;
    }

    public void emptyDisplay() {
        for (Component[] row : this.map) {
            for (Component comp : row) {
                if (comp != null) {
                    this.simPane.getChildren().remove(comp.getBody());
                }
            }
        }
        this.map = new Component[GRID_SIZE_X][GRID_SIZE_Y];

    }
    
}
