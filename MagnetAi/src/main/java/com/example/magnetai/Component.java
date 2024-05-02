package com.example.magnetai;


import javafx.scene.shape.Shape;

public interface Component{
	
	int getIndex();
	
	int[] getPosition();
	
	String getType(); // can also be char
	
	Shape getBody();
	
	Component clone();
	
	
}
