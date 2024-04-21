package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class Charge extends Circle implements Component {
    static final double CHARGE_RADIUS = 15;
    static final String TYPE = "charge";
    static final String NOTHING = "nothing";
    private static final Color CHARGE_COLOR = Color.RED;
    private double[] velocity;
    ChargeType chargeType;
    int startingIndex;
    boolean isAlive;
    
    private Random random = new Random();
    private double angle=random.nextDouble(-Math.PI,Math.PI);

    /**
     * @param startingIndex
     * @param type
     */
    public Charge(int startingIndex, ChargeType type) {
        super(CHARGE_RADIUS, Color.RED);
        this.startingIndex = startingIndex;
        this.chargeType = type;
        this.setFill(CHARGE_COLOR);
        this.isAlive = true;
        this.velocity = new double[]{0,0,0};
        
        
    }

    public Charge(int startingIndex, ChargeType type, double[] velocity) {
        super(CHARGE_RADIUS, Color.RED);
        this.startingIndex = startingIndex;
        this.chargeType = type;
        this.setFill(CHARGE_COLOR);
        this.isAlive = true;
        this.velocity = new double[]{velocity[0] * Math.cos(angle),velocity[1] * Math.sin(angle),0};
        
        
        
    }

    /**
     * makes the charge move based on the component colliding with it
     *
     * @param component
     */
    public void move(Component component, double scale) {
        if (this.isAlive) {
            switch (checkCollisionType(component)) {
                case Obstacle.TYPE -> {
                    this.isAlive = false;
                }
                case NOTHING -> {
                    this.setTranslateX(this.getTranslateX() + (velocity[0]) * scale);
                    this.setTranslateY(this.getTranslateY() + (velocity[1]) * scale);
                }
                case MagneticField.TYPE -> {
                    double[] strength = ((MagneticField) component).getStrength();
                    double[] newVelocity = (this.chargeType.equals(ChargeType.NEGATIVE)) ?
                            MathFunctions.calcFinalVelocity(MathFunctions.ELECTRON_CONSTANT, MathFunctions.ELECTRON_MASS, velocity, strength) :
                            MathFunctions.calcFinalVelocity(MathFunctions.PROTON_CONSTANT, MathFunctions.PROTON_MASS, velocity, strength);

                    velocity = newVelocity;
                    this.setTranslateX(this.getTranslateX() + (velocity[0]) * scale);
                    this.setTranslateY(this.getTranslateY() + (velocity[1]) * scale);
                }
                default -> {
                    this.setTranslateX(this.getTranslateX() + (velocity[0]) * scale);
                    this.setTranslateY(this.getTranslateY() + (velocity[1]) * scale);
                }
            }
        }
    }

    /**
     * returns the component intersecting with this charge
     *
     * @param component
     * @return a string type of component
     */
    public String checkCollisionType(Component component) {
        return (component != null && this.intersects(component.getBody().getLayoutBounds())) ?
                component.getType() :
                Charge.NOTHING;
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
        return Charge.TYPE;
    }

    @Override
    public Shape getBody() {
        return this;
    }

    @Override
    public Component clone() {
        return new Charge(this.startingIndex, this.chargeType, this.velocity);
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public int getStartingIndex() {
        return this.startingIndex;
    }

    public void setStartingIndex(int index) {
        this.startingIndex = index;
    }
    
    public double getAngle(){
        return angle;
    }
    
    public void setAngle(double angle){
        this.angle = angle;
    }
    
    public double[] getVelocity(){
        return velocity;
    }
    
    public void setVelocity(double[] velocity){
        this.velocity = velocity;
    }
}
