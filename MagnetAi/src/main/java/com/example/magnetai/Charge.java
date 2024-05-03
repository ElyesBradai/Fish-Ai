package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class Charge extends Circle implements Component {
    static double CHARGE_RADIUS;
    static final String TYPE = "charge";
    static final String NOTHING = "nothing";
    private static final Color CHARGE_COLOR = Color.RED;
    private double[] velocity;
    private boolean finished;
    
    private double speed;
    ChargeType chargeType;
    int startingIndex;
    boolean isAlive;
    private double angle = 0;
    
    private double scale = Simulation.calculateScale();

    /**
     * creates a charge in the specified index of the specified type
     * @param startingIndex the starting index of the charge
     * @param type the charge type
     */
    public Charge(int startingIndex, ChargeType type) {
        super(CHARGE_RADIUS, Color.RED);
        this.startingIndex = startingIndex;
        this.chargeType = type;
        this.setFill(CHARGE_COLOR);
        this.isAlive = true;
        this.velocity = new double[]{0,0,0};
        this.finished = false;

    }
    
    /**
     * creates a charge in the specified index of the specified type with the specied speed
     * @param speed the speed of the charge
     * @param startingIndex the starting index of the charge
     * @param type the charge type
     */
    public Charge(int startingIndex, ChargeType type, double speed) {
        super(CHARGE_RADIUS, Color.RED);
        this.startingIndex = startingIndex;
        this.chargeType = type;
        this.setFill(CHARGE_COLOR);
        this.isAlive = true;
        this.speed = speed;
        this.velocity = new double[]{speed * Math.cos(angle), speed * Math.sin(angle),0};
        this.finished = false;
        
    }

    /**
     * makes the charge move based on the component colliding with it
     *
     * @param component the component in that charge is colliding with
     */
    public void move(Component component) {
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
                case FinishLine.TYPE -> {
                    this.setAlive(false);
                    this.setFinished(true);
                    Simulation.setSolved(true);
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
     * @param component the component that is colliding
     * @return a string type of component
     */
    public String checkCollisionType(Component component) {
        return (component != null && this.intersects(component.getBody().getLayoutBounds())) ?
                component.getType() :
                Charge.NOTHING;
    }
    
    /**
     * sets the velocity based on the new angle
     * @param newAngle the angle of the velocity
     */
    public void setNewVelocity(double newAngle){
        this.velocity = new double[]{speed * Math.cos(newAngle), speed * Math.sin(newAngle), 0};
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
        return new Charge(this.startingIndex, this.chargeType, this.speed);
    }
    
    public double getSpeed(){
        return speed;
    }
    
    public void setSpeed(double speed){
        this.speed = speed;
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
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    public boolean isFinished() {
        return this.finished;
    }
    
    public void setVelocity(double[] velocity){
        this.velocity = velocity;
    }
}
