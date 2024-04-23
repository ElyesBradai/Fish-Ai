package com.example.magnetai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainClass extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(MainClass.class.getResource("Menupage.fxml"));
        Scene scene = new Scene(fxmlloader.load(), 600, 400);
        stage.setTitle("Hello");
        stage.setScene(scene);
        stage.show();
    }
}