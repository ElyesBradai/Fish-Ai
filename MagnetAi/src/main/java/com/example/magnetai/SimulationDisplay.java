package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;

public class SimulationDisplay extends Simulation{

    private String selectedComponentType;
    boolean isDisplay;
//    private boolean hasFinishLine;

    public SimulationDisplay() {

        super();
//        this.hasFinishLine = false;   //will make finishline a singleton within the grid (and charge too)
        this.selectedComponentType = "Obstacle";
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

    void bckg() {
        boolean isColored = true;

        for (int i = 0; i < this.map.length; i++) {
            if (this.map[0].length %2 ==0) {
                isColored = !isColored;
            }

            for (int j = 0; j < map[i].length; j++) {
                isColored = !isColored;
                Rectangle temp = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, isColored ? Color.BROWN : Color.BEIGE);
                this.simPane.getChildren().add(temp);
                this.squareList.add(temp);System.out.println("added");
                temp.setTranslateX(i*SQUARE_SIZE);
                temp.setTranslateY(j*SQUARE_SIZE);
            }
        }
    }

    public String getSelectedComponentType() {
        return selectedComponentType;
    }

    public void setSelectedComponentType(String selectedComponentType) {
        this.selectedComponentType = selectedComponentType;
    }
}
