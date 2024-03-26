package com.example.magnetai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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