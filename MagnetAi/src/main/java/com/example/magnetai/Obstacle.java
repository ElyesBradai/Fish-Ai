package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Obstacle extends Rectangle implements Component{
	static final String TYPE = "obstacle";
	int index;
	public static double square_size = Simulation.SQUARE_SIZE;
	
	/**
	 * @param index
	 */
	public Obstacle(int index){
		super(square_size, square_size, Color.BLUE); //creates a square of size 50 by 50
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
	public int[] getPosition(){
		return new int[0];
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
