package com.example.magnetai;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.util.ArrayList;

public class SimulationDisplay extends Simulation{

    private String selectedComponentType;
    boolean isDisplay;
//    private boolean hasFinishLine;

    public SimulationDisplay() {
        super();
//        this.hasFinishLine = false;   //will make finishline a singleton within the grid (and charge too)
        this.selectedComponentType = "";
        bckg();
        this.addClickable();
    }


    public void addClickable() {

        //TODO IMPLEMENT ALL COMPONENTS
        for (Rectangle square: squareList)  {

            square.setOnMouseClicked(event -> {

                int index = posToIndex(absolutePosToGridPos(square.getTranslateX(),square.getTranslateY()));

                switch (this.selectedComponentType) {

                    case "obstacle": {
                        Obstacle o1 = new Obstacle(index);
                        this.addToMap(o1,o1.getIndex());
                        break;
                    }
                    case "finishLine": {
                        //TODO ALLOW ONLY ONE
//                        if (this.hasFinishLine) {}
                        FinishLine f1 = new FinishLine(index);
                        this.addToMap(f1, f1.getIndex());
                        break;
                    }
                    case "superConductor": {
                        Superconductor s1 = new Superconductor(index);
                        this.addToMap(s1, s1.getIndex());
                        break;
                    }
                    //case "Charge": {break;}
                    default: System.out.println("please select a valid type");
                }
            });
        }
    }
    public void saveDesign() {

        for (Simulation sim : Simulation.getSimulationList()) {
            Component[][] copiedMap = new Component[this.map.length][this.map[0].length];
            for (int i = 0; i < this.map.length; i++) {
                for (int j = 0; j < this.map[0].length; j++) {
                    // Perform deep copy of each Component object
                    if (this.map[i][j] != null) {

                        copiedMap[i][j] = this.map[i][j].clone();
                        sim.addToMap(copiedMap[i][j],copiedMap[i][j].getIndex());
                    }
                }
            }
            sim.map = copiedMap;
        }
    }

    public void showAllSimulations() {
        //This method opens a window with all the simulations with copied map
        Simulation.getSimulationList().remove(this);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.TOP_LEFT);
        root.setPrefWrapLength(1600);

        for (Simulation sim : Simulation.getSimulationList()) {

            root.getChildren().add(sim.getSimPane());

        }
        //scaling down to fit all simulations
        int numSimulations = Simulation.getSimulationList().size();
        int numRows = (int) Math.ceil(Math.sqrt(numSimulations));
        int numCols = (int) Math.ceil((double) numSimulations / numRows);

        double scaleX = (double) 1 / numCols;
        double scaleY = (double) 1 / numRows;

        root.setHgap((GRID_SIZE_X*SQUARE_SIZE + SQUARE_SIZE)*scaleX);
        root.setVgap((GRID_SIZE_Y*SQUARE_SIZE + SQUARE_SIZE)*scaleY);


        int WIDTH = 1200;
        int HEIGHT = 1000;

        WIDTH += SQUARE_SIZE*scaleX*GRID_SIZE_X;
        HEIGHT -= SQUARE_SIZE * scaleY * GRID_SIZE_Y;


        for (Simulation sim : Simulation.getSimulationList()) {

            for (Rectangle square: sim.squareList) {

                square.setWidth(square.getWidth()*scaleX);
                square.setHeight(square.getHeight()*scaleY);
                square.setTranslateX(square.getTranslateX()*scaleX);
                square.setTranslateY(square.getTranslateY()*scaleY);
            }


            for (Component[] row:sim.map) {

                for (Component component:row) {
                    if (component != null) {
                        switch (component.getType()) {

                            case "obstacle":
                            case "superConductor":
                            case "finishLine": {

                                ((Rectangle) component.getBody()).setWidth(((Rectangle) component.getBody()).getWidth() * scaleX);
                                ((Rectangle) component.getBody()).setHeight(((Rectangle) component.getBody()).getHeight() * scaleY);
                                ((Rectangle) component.getBody()).setTranslateX(((Rectangle) component.getBody()).getTranslateX() * scaleX);
                                ((Rectangle) component.getBody()).setTranslateY(((Rectangle) component.getBody()).getTranslateY() * scaleY);
                                break;
                            }
                            case "charge": {

                                ((Circle) component.getBody()).setRadius(((Circle) component.getBody()).getRadius() * scaleX);
                                ((Circle) component.getBody()).setTranslateX(((Circle) component.getBody()).getTranslateX() * scaleX);
                                ((Circle) component.getBody()).setTranslateY(((Circle) component.getBody()).getTranslateY() * scaleY);
                                break;
                            }
                        }
                    }
                }
            }
        }
        this.getTimerInstance().start();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public String getSelectedComponentType() {
        return selectedComponentType;
    }

    public void setSelectedComponentType(String selectedComponentType) {
        this.selectedComponentType = selectedComponentType;
    }
}
