package com.example.magnetai;

import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Charge extends Circle implements Component{



    private static final Color CHARGE_COLOR = Color.RED;
    static String type;
//    double angle;
//    double xVelocity;
//    double yVelocity;
    ChargeType ChargeType;
    int startingIndex;
    double[] velocity;

    public Charge(Point2D initialPosition,ChargeType type) {

        this.ChargeType=type;
        this.setFill(CHARGE_COLOR);
        this.setCenterX(initialPosition.getX());
        this.setCenterY(initialPosition.getY());
        this.type = "charge";
        this.velocity = new double[]{0, 0, 0};
    }

    public void move(double chargeValue, double chargeMass, Component component) {
        if (checkCollision(component)==Obstacle.type){

        }
   double[] newVelocity = MathFunctions.calcFinalVelocity(chargeValue, chargeMass,magneticField,velocity);

    }

    public String checkCollision(Component component) {
     if(this.intersects(component.getBody().getBoundsInParent())){

         return component.getType();
     }
     return"nothing";

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
        return this.type;
    }

    @Override
    public Shape getBody() {
        return this;
    }
}
