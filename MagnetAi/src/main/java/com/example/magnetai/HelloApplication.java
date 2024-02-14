package com.example.magnetai;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {




    @Override
    public void start(Stage stage) throws IOException {


        AnimationTimer timer = new myTimer();
        timer.start();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        



    }

    private class myTimer extends AnimationTimer {
        @Override
        public void handle(long l) {




        }
    }



    public static void main(String[] args) {
        launch();
    }
}