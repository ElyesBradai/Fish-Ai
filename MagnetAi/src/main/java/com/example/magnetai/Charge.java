package com.example.magnetai;

import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Charge extends Circle implements Component{



    private static final int CAR_RADIUS = 15;
    private static final Color CHARGE_COLOR = Color.RED;
//    double angle;
//    double xVelocity;
//    double yVelocity;
    boolean negativelyCharged;
    int startingIndex;
    double[] velocity;

    public Charge(Point2D initialPosition) {

        super(CAR_RADIUS);

        this.setFill(CHARGE_COLOR);

        this.setCenterX(initialPosition.getX());
        this.setCenterY(initialPosition.getY());


        this.velocity = new double[]{0, 0, 0};





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
    public String getType() {
        return null;
    }

    @Override
    public Shape getBody() {
        return this;
    }
}
