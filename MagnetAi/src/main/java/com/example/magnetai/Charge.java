package com.example.magnetai;

import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Charge extends Circle implements Component{


    private static final int CAR_RADIUS = 15;
    private static final Color CHARGE_COLOR = Color.IVORY;
//    double angle;
//    double xVelocity;
//    double yVelocity;
    boolean negativelyCharged;
    int startingIndex;


    public Charge(Point2D initialPosition) {

        super(CAR_RADIUS);

        this.setFill(CHARGE_COLOR);

        this.setCenterX(initialPosition.getX());
        this.setCenterY(initialPosition.getY());







    }













    public void move() {


    }

    public void checkObstacleCollision() {

    }


    @Override
    public int getIndex() {
        return this.startingIndex;
    }

    @Override
    public int[] getPosition() {
        //to finish later
        return new int[0];
    }

    @Override
    public void collide() {

    }

    @Override
    public String getType() {
        return null;
    }
}
