package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * the obstacle class.
 */
public class Obstacle extends Rectangle implements Component{
	static final String TYPE = "obstacle";
	int index;
	public static double square_size = Simulation.SQUARE_SIZE;
	
	/**
	 * creates an obstacle with the specified index.
	 * @param index the index where the obstacle will be placed
	 */
	public Obstacle(int index){
		super(square_size, square_size, Color.BLACK); //creates a square of size 50 by 50
		this.index = index;
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(3);
		this.setMouseTransparent(true);
	}
	
	@Override
	public Component clone(){
		return new Obstacle(this.getIndex());
	}
	
	@Override
	public int getIndex(){
		return this.index;
	}
	
	@Override
	public String getType(){
		return Obstacle.TYPE;
	}
	
	@Override
	public Shape getBody(){
		return this;
	}
}
