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
        FXMLLoader fxmlloader = new FXMLLoader(MainClass.class.getResource("MenuPage.fxml"));
        Scene scene = new Scene(fxmlloader.load(), 600, 400);
        stage.setTitle("Magnet Ai: Main menu");
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        stage.show();
    }
}