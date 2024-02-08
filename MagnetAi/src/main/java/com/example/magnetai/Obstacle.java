package com.example.magnetai;

import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle implements Component {






    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public int[] getPosition() {
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
