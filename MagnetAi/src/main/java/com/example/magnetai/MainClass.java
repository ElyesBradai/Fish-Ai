package com.example.magnetai;

import javafx.application.Application;
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

    HBox root = new HBox();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        root.setSpacing(Simulation.GRID_SIZE_X * Simulation.SQUARE_SIZE);

        VBox vb1 = new VBox();
        root.getChildren().addAll(vb1);

        SimulationDisplay s1 = new SimulationDisplay();
        vb1.getChildren().add(s1.getSimPane());
        Charge c1 = new Charge(1);
        s1.addToMap(c1,6);

        Simulation s2 = new Simulation();
        Simulation s3 = new Simulation();
        Simulation s4 = new Simulation();
        Simulation s5 = new Simulation();
        Simulation s6 = new Simulation();
        Simulation s7 = new Simulation();
        Simulation s8 = new Simulation();
        Simulation s9 = new Simulation();
        Simulation s10 = new Simulation();
        Simulation s11 = new Simulation();
        Simulation s12 = new Simulation();
        Simulation s13 = new Simulation();
        Simulation s14 = new Simulation();
        Simulation s15 = new Simulation();
        Simulation s16 = new Simulation();
        Simulation s17 = new Simulation();
        Simulation s18 = new Simulation();
        Simulation s19 = new Simulation();
        Simulation s20 = new Simulation();
        Simulation s21 = new Simulation();
        Simulation s22 = new Simulation();
        Simulation s23 = new Simulation();
        Simulation s24 = new Simulation();
        Simulation s25 = new Simulation();
        Simulation s26 = new Simulation();
        Simulation s27 = new Simulation();



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

        Button b1 = new Button("Save");
        b1.setOnAction(event -> {

            s1.saveDesign();
            s1.showAllSimulations();

        });

        VBox selectorBox = new VBox(obSelector,finishSelector,superConductor,selected,b1);
        root.getChildren().add(selectorBox);
        //vb2.getChildren().add(s2.getSimPane());

        Scene scene = new Scene(root, 1200, 800);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}