package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Superconductor extends Rectangle implements Component {

    final static String type = "superconductor";
    int index;

    /**
     *
     * @param index
     */
    public Superconductor(int index){

        super(SQUARE_SIZE,SQUARE_SIZE, Color.CYAN); //creates a square of size 50 by 50
        this.index = index;
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
        this.setMouseTransparent(true);
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
    @Override
    public Component clone() {
        return new Superconductor(this.getIndex());
    }
}
