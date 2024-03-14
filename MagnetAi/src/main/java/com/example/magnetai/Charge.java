package com.example.magnetai;

import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Charge extends Circle implements Component{



    final static String type = "charge";
    private static final Color CHARGE_COLOR = Color.RED;
//    double angle;
//    double xVelocity;
//    double yVelocity;
    ChargeType chargeType;
    int startingIndex;
    double[] velocity;

    public Charge(Point2D initialPosition,ChargeType type) {

        this.chargeType=type;
        this.setFill(CHARGE_COLOR);
        this.setCenterX(initialPosition.getX());
        this.setCenterY(initialPosition.getY());
        this.velocity = new double[]{0, 0, 0};
    }

    public void move(Component component) {
        switch (checkCollision(component)){
            case "nothing": {
                this.setTranslateX(this.getTranslateX() + this.velocity[0]);
                this.setTranslateY(this.getTranslateY() + this.velocity[1]);
                break;
            }
            case MagneticField.type:{

                if(this.chargeType.equals( ChargeType.NEGATIVE)) {
                    double[] newVelocity = MathFunctions.calcFinalVelocity(MathFunctions.ELECTRON_CONSTANT,MathFunctions.ELECTRON_MASS,((MagneticField) component).getStrength(),this.velocity);
                    this.velocity=newVelocity;
                    this.setTranslateX(this.getTranslateX()+this.velocity[0]);
                    this.setTranslateY(this.getTranslateY()+this.velocity[1]);

                }
                else if (this.chargeType.equals( ChargeType.POSITIVE)) {
                    double[] newVelocity = MathFunctions.calcFinalVelocity(MathFunctions.PROTON_CONSTANT,MathFunctions.PROTON_MASS,((MagneticField) component).getStrength(),this.velocity);
                    this.velocity=newVelocity;
                    this.setTranslateX(this.getTranslateX()+this.velocity[0]);
                    this.setTranslateY(this.getTranslateY()+this.velocity[1]);
                    
                }
                break;

            }





        }
    }

    public String checkCollision(Component component) {
     if(component!=null && this.intersects(component.getBody().getBoundsInParent())){

         return component.getType();
     }

     return "nothing";

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

    @Override
    public Component clone() {
        return null;
    }
}
