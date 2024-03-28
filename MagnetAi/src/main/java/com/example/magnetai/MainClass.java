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
        Charge c1 = new Charge(1, ChargeType.NEGATIVE);
        s1.addToMap(c1,1);

        MagneticField opp1 = new MagneticField(11-1,new double[]{0,0,-0.2});
        MagneticField opp2 = new MagneticField(12-1,new double[]{0,0,-0.2});
        MagneticField opp3 = new MagneticField(19-1,new double[]{0,0,-0.2});
        MagneticField opp4 = new MagneticField(20-1,new double[]{0,0,-0.2});

        s1.addToMap(opp1,opp1.index);
        s1.addToMap(opp2,opp2.index);
        s1.addToMap(opp3,opp3.index);
        s1.addToMap(opp4,opp4.index);

        for (int i = 0; i < 3; i++) {
            new Simulation();
        }

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
        Rectangle mgfield = new Rectangle(100,100, Color.GREEN);
        mgfield.setOnMouseClicked(event -> {s1.setSelectedComponentType("magneticfield");selectedShape.set(mgfield);});


        Button b1 = new Button("Save");
        b1.setOnAction(event -> {

            s1.saveDesign();
            s1.showAllSimulations();


        });


        VBox selectorBox = new VBox(obSelector,finishSelector,superConductor,mgfield,selected,b1);
        root.getChildren().add(selectorBox);

        Scene scene = new Scene(root, 1200, 800);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}