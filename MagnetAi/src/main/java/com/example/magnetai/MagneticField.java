package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * the magneticField class.
 */
public class MagneticField extends Rectangle implements Component {
    static final String TYPE = "magneticfield";
    int index;
    private double[] strength;
    public static double STRENGTH_COEFFICIENT = 0.3;
    public static double square_size = Simulation.SQUARE_SIZE;

    /**
     * creates a magnetic field with the specified index and strength.
     * @param index the index where the magnetic field will be placed
     * @param strength the strength vector of the magnetic field
     */
    public MagneticField(int index, double[] strength) {
        super(square_size, square_size, Color.GREEN); //creates a square of size 50 by 50
        this.index = index;
        this.strength = strength;
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
        this.setMouseTransparent(true);
    }

    @Override
    public Component clone() {
        return new MagneticField(this.getIndex(), this.getStrength());
    }

    @Override
    public int getIndex() {
        return this.index;
    }
    

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Shape getBody() {
        return this;
    }
    
    public double[] getStrength(){
        return strength;
    }
    
    public void setStrength(double[] strength) {
        this.strength = strength;
    }
}
