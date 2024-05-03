package com.example.magnetai;


import javafx.scene.shape.Shape;

/**
 * the interface for all the components
 */
public interface Component{
	
	int getIndex();
	
	String getType(); // can also be char
	
	Shape getBody();
	
	Component clone();
	
	
}
