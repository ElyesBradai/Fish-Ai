package com.example.magnetai;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;

public class Simulation {

    public static final int GRID_SIZE_X = 6;
    public static final int GRID_SIZE_Y = 8;
    public static final int SQUARE_SIZE = 100;
    Component[][] map;
    Pane simPane;//not sure if i should use pane or other :/
    Rectangle player = new Rectangle(50,50);
    ArrayList<Rectangle> squareList;
    private NeuralNetwork neuralNetwork;
    private static myTimer timer;
    private static ArrayList<Simulation> simulationList = new ArrayList<Simulation>();

    public Simulation(){

        this.neuralNetwork = new NeuralNetwork();
        this.map = new Component[GRID_SIZE_X][GRID_SIZE_Y];
        this.simPane = new Pane();
        this.squareList = new ArrayList<Rectangle>();
        simulationList.add(this);
        bckg();
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
                this.squareList.add(temp);
                temp.setTranslateX(i*SQUARE_SIZE);
                temp.setTranslateY(j*SQUARE_SIZE);
        }
     }
    }

    void setPlayerPos(int[] pos) {
        player.setTranslateY(pos[0]*SQUARE_SIZE);
        player.setTranslateX(pos[1]*SQUARE_SIZE);
    }


    Component posToValue(int[] pos) {
        return map[pos[0]][pos[1]];
    }

    int posToIndex(int[] pos) {
        return pos[0]*GRID_SIZE_Y+pos[1];
    }

    int[] indexToPos(int index) {
        return new int[]{index/map[0].length, index %map[0].length};
    }

    Component checkRightValue(int index) {
        return posToValue(indexToPos(++index));
    }

    Component checkLeftValue(int index) {
        return posToValue(indexToPos(--index));
    }

    Component checkUpValue(int index) {
        index-=GRID_SIZE_Y;
        return posToValue(indexToPos(index));
    }

    Component checkDownValue(int index) {
        index+=GRID_SIZE_Y;
        return posToValue(indexToPos(index));
    }

    public void addToMap(Component component, int index) {
        int[] pos = indexToPos(index);
        this.map[pos[0]][pos[1]] = component;
        this.simPane.getChildren().add(component.getBody());

        boolean isObstacle = component.getType().equals("obstacle")
                || component.getType().equals("superconductor")
                || component.getType().equals("finishLine");
        component.getBody().setTranslateX(isObstacle ? pos[0]*SQUARE_SIZE : pos[0]*SQUARE_SIZE+SQUARE_SIZE/2);
        component.getBody().setTranslateY(isObstacle ? pos[1]*SQUARE_SIZE : pos[1]*SQUARE_SIZE+SQUARE_SIZE/2);
    }

    public int[] absolutePosToGridPos(double translateX, double translateY) {
        return new int[]{(int)(translateX/SQUARE_SIZE), (int)(translateY/SQUARE_SIZE)};
    }

    public Pane getSimPane() {
        return simPane;
    }

    public void setSimPane(Pane simPane) {
        this.simPane = simPane;
    }

    public static ArrayList<Simulation> getSimulationList() {
        return simulationList;
    }

    public void moveAllCharges() {

        for (Simulation sim:simulationList) {

            for (Component[] row: this.map) {

                for (Component charge:row) {

                    if(charge.getType().equals("charge")) {

                        ((Charge) charge).move();
                    }
                }
            }
        }
    }



    private class myTimer extends AnimationTimer {


        @Override
        public void handle(long now) {

        moveAllCharges();


        }
    }

    public static myTimer getTimer() {
        return timer;
    }

}
