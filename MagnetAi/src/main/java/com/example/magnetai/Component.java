package com.example.magnetai;


public interface Component {


    public final int SQUARE_SIZE = Simulation.SQUARE_SIZE;

    int getIndex();
    int[] getPosition();
    String getType(); // can also be char
    







}
