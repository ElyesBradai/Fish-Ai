package com.example.magnetai;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {




    HBox root = new HBox();



    @Override
    public void start(Stage stage) throws IOException {

//        int playerPos = 5;
//        player.setUserData(playerPos);
//        setPlayerPos(indexToPos(playerPos));

//        root.getChildren().add(player);
        VBox vb1 = new VBox();
        //VBox vb2 = new VBox();
        root.getChildren().addAll(vb1);
        Simulation s1 = new Simulation();
        //Simulation s2 = new Simulation();
        vb1.getChildren().add(s1.getRoot());
        //vb2.getChildren().add(s2.getRoot());


        Scene scene = new Scene(root, 1200, 800);



//        scene.setOnKeyPressed(event -> {
//
//
//            switch (event.getCode()) {
//
//                case W: {
//                    player.setUserData((int)(player.getUserData())-5);
//                    setPlayerPos(indexToPos((int)(player.getUserData())));
//                    break;
//                }
//                case A: {
//                    player.setUserData((int)(player.getUserData())-1);
//                    setPlayerPos(indexToPos((int)(player.getUserData())));
//                    break;}
//                case S: {
//                    player.setUserData((int)(player.getUserData())+5);
//                    setPlayerPos(indexToPos((int)(player.getUserData())));
//                    break;}
//                case D: {
//                    player.setUserData((int)(player.getUserData())+1);
//                    setPlayerPos(indexToPos((int)(player.getUserData())));
//                    break;
//                }
//
//            }
//
//
//        });

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }








    public static void main(String[] args) {
        launch();
    }
}