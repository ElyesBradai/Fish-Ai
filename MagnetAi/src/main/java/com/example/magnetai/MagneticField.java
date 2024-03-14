package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class MagneticField extends Rectangle implements Component {

    int index;
    String type;
    double[] strength;

    public MagneticField(int index, double[] strength){

        super(SQUARE_SIZE,SQUARE_SIZE, Color.GREEN); //creates a square of size 50 by 50
        this.index = index;
        this.strength = strength;
        this.type = "magneticField";
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
    public double[] getStrength() {
        return strength;
    }
    public void setStrength(double[] strength) {
        this.strength = strength;
    }
    @Override
    public Component clone() {
        return new MagneticField(this.getIndex(),this.getStrength());
    }

}
