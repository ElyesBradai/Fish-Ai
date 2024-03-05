package com.example.magnetai;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Simulation {


    public static final int GRID_SIZE = 5;
    public static final int SQUARE_SIZE = 100;
    Component[][] map;
    Pane simPane;//not sure if i should use pane or other :/
    Rectangle player = new Rectangle(50,50);
    private NeuralNetwork neuralNetwork;
    public Simulation(){

        this.neuralNetwork = new NeuralNetwork();
        this.map = new Component[5][6];
        this.simPane = new Pane();
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
        index-=GRID_SIZE;
        return posToValue(indexToPos(index));
    }
    Component checkDownValue(int index) {
        index+=GRID_SIZE;
        return posToValue(indexToPos(index));
    }

    public void addToMap(Component component, int index) {
        int[] pos = indexToPos(index);
        this.map[pos[0]][pos[1]] = component;
        this.simPane.getChildren().add(component.getBody());
        component.getBody().setTranslateX(pos[0]*SQUARE_SIZE+SQUARE_SIZE/2);
        component.getBody().setTranslateY(pos[1]*SQUARE_SIZE+SQUARE_SIZE/2);
    }

    public Pane getSimPane() {
        return simPane;
    }
}
