package com.example.magnetai;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.io.IOException;

public class MainClass extends Application {




    HBox root = new HBox();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

//        int playerPos = 5;
//        player.setUserData(playerPos);
//        setPlayerPos(indexToPos(playerPos));

//        root.getChildren().add(player);
        root.setSpacing(Simulation.GRID_SIZE_X * Simulation.SQUARE_SIZE);
        VBox vb1 = new VBox();
        //VBox vb2 = new VBox();
        root.getChildren().addAll(vb1);
        SimulationDisplay s1 = new SimulationDisplay();
        //Simulation s2 = new Simulation();
        vb1.getChildren().add(s1.getSimPane());
        Obstacle o1 = new Obstacle(10);
        //s1.addToMap(o1,o1.index);
        Obstacle o2 = new Obstacle(11);
        //s1.addToMap(o2,o2.index);
        Obstacle o3 = new Obstacle(17);
        //s1.addToMap(o3,o3.index);
        Charge c1 = new Charge(new Point2D(0,0));
        s1.addToMap(c1,6);
        FinishLine f1 = new FinishLine(20);
        s1.addToMap(f1,f1.getIndex());



        // Create an object property to hold the selected rectangle
        ObjectProperty<Shape> selectedShape = new SimpleObjectProperty<>();
        Circle selected = new Circle(50);
        selected.setStrokeWidth(3);
        // Bind the fill property of the circle to the fill property of the selected rectangle
        selected.fillProperty().bind(Bindings.createObjectBinding(() ->
                        selectedShape.get() != null ? selectedShape.get().getFill() : Color.BLACK, selectedShape));

        Rectangle obSelector = new Rectangle(100,100, Color.BLUE);
        obSelector.setOnMouseClicked(event -> {s1.setSelectedComponentType("obstacle");selectedShape.set(obSelector);});
        Rectangle finishSelector = new Rectangle(100,100, Color.YELLOW);
        finishSelector.setOnMouseClicked(event -> {s1.setSelectedComponentType("finishLine");selectedShape.set(finishSelector);});
        Rectangle superConductor = new Rectangle(100,100, Color.CYAN);
        superConductor.setOnMouseClicked(event -> {s1.setSelectedComponentType("superConductor");selectedShape.set(superConductor);});

        VBox selectorBox = new VBox(obSelector,finishSelector,selected);
        root.getChildren().add(selectorBox);
        //vb2.getChildren().add(s2.getSimPane());



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
}