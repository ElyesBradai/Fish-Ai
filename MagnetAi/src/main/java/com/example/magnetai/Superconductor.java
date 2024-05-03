package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * the superConductor class.
 */
public class Superconductor extends Rectangle implements Component{
	static final String TYPE = "superconductor";
	int index;
	public static double square_size = Simulation.SQUARE_SIZE;
	
	/**
	 * creates a superConductor with the specified index
	 * @param index the index of thre superConductor
	 */
	public Superconductor(int index){
		super(square_size, square_size, Color.CYAN); //creates a square of size 50 by 50
		this.index = index;
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(3);
		this.setMouseTransparent(true);
	}
	
	@Override
	public Component clone(){
		return new Superconductor(this.getIndex());
	}
	
	@Override
	public int getIndex(){
		return this.index;
	}
	
	@Override
	public String getType(){
		return TYPE;
	}
	
	@Override
	public Shape getBody(){
		return this;
	}
	
}
