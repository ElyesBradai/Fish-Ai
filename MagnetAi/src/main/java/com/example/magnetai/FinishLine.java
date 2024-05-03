package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * the finishLine class
 */
public class FinishLine extends Rectangle implements Component{
	static final String TYPE = "finishLine";
	int index;
	public static double square_size = Simulation.SQUARE_SIZE;
	
	/**
	 * creates a finishLine with the specified index.
	 * @param index the index where the FinishLine will be placed
	 */
	public FinishLine(int index){
		super(square_size, square_size, Color.YELLOW); //creates a square of size 50 by 50
		this.index = index;
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(3);
		this.setMouseTransparent(true);
	}
	
	@Override
	public Component clone(){
		return new FinishLine(this.getIndex());
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
