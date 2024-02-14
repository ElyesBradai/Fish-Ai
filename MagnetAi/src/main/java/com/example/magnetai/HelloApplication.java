package com.example.magnetai;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {




    int[][] map = new int[5][5];
    Pane root = new Pane();

    Rectangle player = new Rectangle(50,50);


    @Override
    public void start(Stage stage) throws IOException {

        int playerPos = 5;
        player.setUserData(playerPos);

        setPlayerPos(indexToPos(playerPos));


        bckg();



        root.getChildren().add(player);






        Scene scene = new Scene(root, 1200, 800);

        scene.setOnKeyPressed(event -> {


            switch (event.getCode()) {

                case W: {
                    player.setUserData((int)(player.getUserData())-5);
                    setPlayerPos(indexToPos((int)(player.getUserData())));
                    break;
                }
                case A: {
                    player.setUserData((int)(player.getUserData())-1);
                    setPlayerPos(indexToPos((int)(player.getUserData())));
                    break;}
                case S: {
                    player.setUserData((int)(player.getUserData())+5);
                    setPlayerPos(indexToPos((int)(player.getUserData())));
                    break;}
                case D: {
                    player.setUserData((int)(player.getUserData())+1);
                    setPlayerPos(indexToPos((int)(player.getUserData())));
                    break;
                }

            }


        });

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    int[] indexToPos(int index) {

        int[] pos = new int[2];

        pos[1] = index %5;
        pos[0] = index/5;

        return pos;

    }

    int posToValue(int[] pos) {

        return map[pos[0]][pos[1]];

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

        index-=5;
        return posToValue(indexToPos(index));


    }
    int checkDownValue(int index) {

        index+=5;
        return posToValue(indexToPos(index));



    }


    void bckg() {

        boolean color = true;

        for (int i = 0; i < map.length; i++) {

            for (int j = 0; j < map[i].length; j++) {

                if (color) {

                    Rectangle temp = new Rectangle(100, 100, Color.BROWN);
                    //map[i][j] = temp;
                    root.getChildren().add(temp);
                    temp.setTranslateX(i*100);
                    temp.setTranslateY(j*100);
                    color = false;

                }
                else {
                    Rectangle temp = new Rectangle(100, 100, Color.BEIGE);
                    //map[i][j] = temp;
                    root.getChildren().add(temp);
                    temp.setTranslateX(i*100);
                    temp.setTranslateY(j*100);
                    color = true;

                }



            }

        }


    }

    void setPlayerPos(int[] pos) {

        player.setTranslateY(pos[0]*100);
        player.setTranslateX(pos[1]*100);



    }





    public static void main(String[] args) {
        launch();
    }
}