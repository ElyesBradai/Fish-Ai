package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Superconductor extends Rectangle implements Component {

    int index;
    String type;

    public Superconductor(int index){

        super(SQUARE_SIZE,SQUARE_SIZE, Color.CYAN); //creates a square of size 50 by 50
        this.index = index;
        this.type = "obstacle";
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
        this.setMouseTransparent(true);
        this.setOnMouseEntered(event -> {this.setOpacity(0.5);});
    }


    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public int[] getPosition() {
        return new int[0];
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public Shape getBody() {
        return this;
    }
}
