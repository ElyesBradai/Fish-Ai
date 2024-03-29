package com.example.magnetai;


import javafx.scene.shape.Shape;

public interface Component{
	int square_size = Simulation.SQUARE_SIZE;
	
	int getIndex();
	
	int[] getPosition();
	
	String getType(); // can also be char
	
	Shape getBody();
	
	Component clone();
	
	
}
