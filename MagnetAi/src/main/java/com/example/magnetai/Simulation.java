package com.example.magnetai;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Simulation {


    public static final int GRID_SIZE = 5;
    public static final int SQUARE_SIZE = 100;
    int[][] map;
    Pane simPane;//???????????????
    Rectangle player = new Rectangle(50,50);
    private NeuralNetwork neuralNetwork;
    public Simulation(){

        this.neuralNetwork = new NeuralNetwork();
        this.map = new int[4][3];
        this.simPane = new Pane();
        bckg();
    }

    void bckg() {

        boolean color = true;
    if (map[0].length %2 ==1) {
        for (int i = 0; i < map.length; i++) {

            for (int j = 0; j < map[i].length; j++) {

                if (color) {

                    Rectangle temp = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.BROWN);
                    //map[i][j] = temp;
                    this.simPane.getChildren().add(temp);
                    temp.setTranslateX(i*SQUARE_SIZE);
                    temp.setTranslateY(j*SQUARE_SIZE);
                    color = false;

                }
                else {
                    Rectangle temp = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.BEIGE);
                    //map[i][j] = temp;
                    this.simPane.getChildren().add(temp);
                    temp.setTranslateX(i*SQUARE_SIZE);
                    temp.setTranslateY(j*SQUARE_SIZE);
                    color = true;

                }

            }

        }
    }

        if (map[0].length %2 ==0) {
            for (int i = 0; i < map.length; i++) {

                if (color) color = false;

                else color = true;


                for (int j = 0; j < map[i].length; j++) {

                    if (color) {

                        Rectangle temp = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.BROWN);
                        //map[i][j] = temp;
                        this.simPane.getChildren().add(temp);
                        temp.setTranslateX(i*SQUARE_SIZE);
                        temp.setTranslateY(j*SQUARE_SIZE);
                        color = false;

                    }
                    else {
                        Rectangle temp = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.BEIGE);
                        //map[i][j] = temp;
                        this.simPane.getChildren().add(temp);
                        temp.setTranslateX(i*SQUARE_SIZE);
                        temp.setTranslateY(j*SQUARE_SIZE);
                        color = true;

                    }

                }

            }
        }



    }

    void setPlayerPos(int[] pos) {

        player.setTranslateY(pos[0]*SQUARE_SIZE);
        player.setTranslateX(pos[1]*SQUARE_SIZE);



    }


    int posToValue(int[] pos) {

        return map[pos[0]][pos[1]];

    }


    int[] indexToPos(int index) {

        int[] pos = new int[2];

        pos[1] = index %GRID_SIZE;
        pos[0] = index/GRID_SIZE;

        return pos;

    }

    int checkRightValue(int index) {


        index+=1;
        return posToValue(indexToPos(index));


    }
    int checkLeftValue(int index) {


        index-=1;

        return posToValue(indexToPos(index));


    }
    int checkUpValue(int index) {

        index-=GRID_SIZE;
        return posToValue(indexToPos(index));


    }
    int checkDownValue(int index) {

        index+=GRID_SIZE;
        return posToValue(indexToPos(index));



    }

    public Pane getSimPane() {
        return simPane;
    }
}
