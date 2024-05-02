package com.example.magnetai;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Superconductor extends Rectangle implements Component{
	static final String TYPE = "superconductor";
	int index;
	public static double square_size = Simulation.SQUARE_SIZE;
	
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
	public int[] getPosition(){
		return new int[0];
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
