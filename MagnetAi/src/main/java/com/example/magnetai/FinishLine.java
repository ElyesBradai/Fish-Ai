package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class FinishLine extends Rectangle implements Component {
    final static String type = "finishLine";
    int index;

    /**
     *
     * @param index
     */
    public FinishLine(int index) {
        super(SQUARE_SIZE, SQUARE_SIZE, Color.YELLOW); //creates a square of size 50 by 50
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
        //TODO FINISH LATER
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
        return new FinishLine(this.getIndex());
    }

}
