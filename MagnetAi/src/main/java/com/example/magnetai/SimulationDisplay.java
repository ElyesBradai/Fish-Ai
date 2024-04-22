package com.example.magnetai;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class SimulationDisplay extends Simulation {
    private String selectedComponentType;
    private boolean isScaled;


    //    TODO MAKE FINISHLINE AND CHARGE A SINGLETON WHITNG THE SIMULATION
//    private boolean hasFinishLine;
    public SimulationDisplay() {
        super();
//        this.hasFinishLine = false;   //will make finishline a singleton within the grid (and charge too)
        selectedComponentType = "";
        bckg();
        addClickable();
        isScaled = false;
        
    }

    /**
     * Makes the simulation display clickable and editable to add the components
     */
    public void addClickable() {
        //TODO IMPLEMENT ALL COMPONENTS
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
                        Obstacle o1 = new Obstacle(index);
                        this.addToMap(o1, o1.getIndex());

                    }
                    case FinishLine.TYPE -> {
                        //TODO ALLOW ONLY ONE
//                        if (this.hasFinishLine) {}
                        FinishLine f1 = new FinishLine(index);
                        this.addToMap(f1, f1.getIndex());

                    }
                    case Superconductor.TYPE -> {
                        Superconductor s1 = new Superconductor(index);
                        this.addToMap(s1, s1.getIndex());

                    }
                    case MagneticField.TYPE -> {
                        MagneticField m1 = new MagneticField(index, new double[]{0, 0, 0.06});
                        this.addToMap(m1, m1.getIndex());
                    }
                    //case "Charge": {break;}
                    default -> {


                        System.out.println("please select a valid type");
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

    private void createBrains() {
        //this part creates decides the values (0 if empty or 1 if other)
        Deque<Double> empty = new ArrayDeque<Double>();
        for (Component[] row : this.map) {
            for (Component component: row) {
                if (component == null){
                    empty.add(0.0);
                }
                else {
                    empty.add(1.0);
                }
            }
        }
        //this part creates the brain (neural network) and call the predict method
        for (Simulation sim : simulationList) {
            int counter = 0; //to use within the empty array
            sim.setBrain(new NeuralNetwork(0.5f, new int[]{GRID_SIZE_Y * GRID_SIZE_X, 4,empty.size()+1}));
//            double[] predictions = sim.getBrain().predict(empty.stream().mapToDouble(Double::doubleValue).toArray());
//            double[] predictions = sim.getBrain().predict(sim.map)TODO FLATTEN AND INPUT MAP
            //TODO USE ANGLE
//            double angle = predictions[0] * Math.PI; //index 0 is the angle for the charge and the rest is the strength
//            for (int i = 0; i < sim.map.length; i++) {
//                for (int j = 0; j < sim.map[0].length; j++) {
//                    sim.map[i][j] = new MagneticField(i*GRID_SIZE_Y+j,new double[] {0,0,predictions[counter]*MagneticField.STRENGTH_COEFFICIENT});
//                    counter++;
//                }
//            }
        }
    }

    /**
     * opens a new stage with all the simulations running
     */
    public void showAllSimulations() {
        //This method opens a window with all the simulations with copied map
        
        double scale = calculateScale();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double screenWidth = bounds.getWidth();
        double screenHeight = bounds.getHeight();
        Simulation.getSimulationList().remove(this);
        FlowPane root = new FlowPane();
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(false);
        scrollPane.setFitToHeight(false);
        root.setAlignment(Pos.TOP_LEFT);
        root.setPrefWidth(screenWidth);
        root.setPrefHeight(screenHeight+(GRID_SIZE_Y * SQUARE_SIZE + SQUARE_SIZE)*scale);
        
        
        for (Simulation sim : Simulation.getSimulationList()) {
            root.getChildren().add(sim.getSimPane());
        }

        //scaling down to fit all simulations

        
        root.setHgap((GRID_SIZE_X * SQUARE_SIZE + SQUARE_SIZE) * scale);
        root.setVgap((GRID_SIZE_Y * SQUARE_SIZE + SQUARE_SIZE) * scale);
        root.setPrefWrapLength(screenWidth);
        int width = 0;
        int height = 0;
        width += (SQUARE_SIZE * scale * GRID_SIZE_X);
        height += SQUARE_SIZE * scale * GRID_SIZE_Y;

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
                            case MagneticField.TYPE, Obstacle.TYPE , Superconductor.TYPE , FinishLine.TYPE -> {
                                ((Rectangle) component.getBody()).setWidth(((Rectangle) component.getBody()).getWidth() * scale);
                                ((Rectangle) component.getBody()).setHeight(((Rectangle) component.getBody()).getHeight() * scale);
                                component.getBody().setTranslateX(component.getBody().getTranslateX() * scale);
                                component.getBody().setTranslateY(component.getBody().getTranslateY() * scale);
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
        Scene scene = new Scene(scrollPane, width, height);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public String getSelectedComponentType() {
        return selectedComponentType;
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
