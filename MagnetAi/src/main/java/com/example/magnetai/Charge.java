package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Charge extends Circle implements Component{
    final static double CHARGE_RADIUS = 15;
    final static String type = "charge";
    private static final Color CHARGE_COLOR = Color.RED;
    ChargeType chargeType;
    int startingIndex;
    double[] velocity;
    static final String NOTHING = "nothing";

    /**
     *
     * @param startingIndex
     * @param type
     */
    public Charge(int startingIndex, ChargeType type) {
        super(CHARGE_RADIUS, Color.RED);
        this.startingIndex = startingIndex;
        this.chargeType = type;
        this.setFill(CHARGE_COLOR);
        this.velocity = new double[]{0.3, 0.3, 0};
    }

    /**
     * makes the charge move based on the component colliding with it
     * @param component
     */
    public void move(Component component) {
        switch (checkCollisionType(component)){
            case Obstacle.type -> {
                System.out.println("OBSTACLE");
                this.velocity = new double[]{0, 0, 0};
                this.setTranslateX(this.getTranslateX() + this.velocity[0]);
                this.setTranslateY(this.getTranslateY() + this.velocity[1]);
                System.out.println(checkCollisionType(component));
            }
            case "nothing" -> {
                this.setTranslateX(this.getTranslateX() + this.velocity[0]);
                this.setTranslateY(this.getTranslateY() + this.velocity[1]);
            }
            default -> {
                this.setTranslateX(this.getTranslateX() + this.velocity[0]);
                this.setTranslateY(this.getTranslateY() + this.velocity[1]);
            }



            case MagneticField.type -> {
                if(this.chargeType.equals(ChargeType.NEGATIVE)) {
                    double[] newVelocity = MathFunctions.calcFinalVelocity(MathFunctions.ELECTRON_CONSTANT,MathFunctions.ELECTRON_MASS,this.velocity,((MagneticField) component).getStrength());
                    System.out.println("Current velocity is X: " + this.velocity[0] + "and Y: "+ this.velocity[1]+ "and Z: "+ this.velocity[2]);
                    this.velocity=newVelocity;
                    this.setTranslateX(this.getTranslateX() + this.velocity[0]);
                    this.setTranslateY(this.getTranslateY() + this.velocity[1]);
                }
                else if (this.chargeType.equals(ChargeType.POSITIVE)) {
                    double[] newVelocity = MathFunctions.calcFinalVelocity(MathFunctions.PROTON_CONSTANT,MathFunctions.PROTON_MASS,this.velocity,((MagneticField) component).getStrength());
                    this.velocity = newVelocity;
                    this.setTranslateX(this.getTranslateX() + this.velocity[0]);
                    this.setTranslateY(this.getTranslateY() + this.velocity[1]);
                }
            }
        }
    }

    /**
     * returns the component intersecting with this charge
     * @param component
     * @return a string type of component
     */
    public String checkCollisionType(Component component) {
     if(component != null && this.intersects(component.getBody().getLayoutBounds())){
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
        //TODO finish later
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
        return new Charge(this.startingIndex,this.chargeType);
    }
}
