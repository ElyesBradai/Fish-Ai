package com.example.magnetai;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Simulation{
	public static final int GRID_SIZE_X = 6;
	public static final int GRID_SIZE_Y = 4;
	public static final int SQUARE_SIZE = 100;
	public static ArrayList<Simulation> simulationList = new ArrayList();
	private final NeuralNetwork neuralNetwork;
	private final myTimer timer = new myTimer(); // timer is a singleton within the class
	Component[][] map;
	Pane simPane;//not sure if i should use pane or other :/
	Rectangle player = new Rectangle(50, 50);
	ArrayList<Rectangle> squareList;
	
	public Simulation(){
		neuralNetwork = new NeuralNetwork();
		map = new Component[GRID_SIZE_X][GRID_SIZE_Y];
		simPane = new Pane();
		squareList = new ArrayList<Rectangle>();
		simulationList.add(this);
		bckg();
	}
	
	/**
	 * draws a background of tiles
	 */
	void bckg(){
		boolean isColored = true;
		for (int i = 0; i < map.length; i++){
			if(map[0].length % 2 == 0){
				isColored = ! isColored;
			}
			
			for (int j = 0; j < map[i].length; j++){
				isColored = ! isColored;
				Rectangle temp = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, isColored ? Color.BROWN : Color.BEIGE);
				simPane.getChildren().add(temp);
				squareList.add(temp);
				temp.setTranslateX(i * SQUARE_SIZE);
				temp.setTranslateY(j * SQUARE_SIZE);
			}
		}
	}
	
	/**
	 * takes an int array of position (x,y) and returns its corresponding index
	 *
	 * @param pos
	 * @return an int index
	 */
	int posToIndex(int[] pos){
		return pos[0] * GRID_SIZE_Y + pos[1];
	}
	
	/**
	 * add the specified component at the specified index
	 *
	 * @param component
	 * @param index
	 */
	public void addToMap(Component component, int index){
		int[] pos = indexToPos(index);
		if(component.getType().equals(Charge.TYPE) && findCharge() != null){
			Charge charge = findCharge();
			int[] posOfCharge = indexToPos(charge.getIndex());
			simPane.getChildren().remove(charge.getBody());
			map[posOfCharge[0]][posOfCharge[1]] = null;
			
			
		}
		
		map[pos[0]][pos[1]] = component;
		
		simPane.getChildren().add(component.getBody());
		
		boolean isObstacle = component.getType().equals(Obstacle.TYPE)
				|| component.getType().equals(Superconductor.TYPE)
				|| component.getType().equals(FinishLine.TYPE)
				|| component.getType().equals(MagneticField.TYPE)
				|| component.getType().equals(Charge.TYPE);
		component.getBody().setTranslateX(isObstacle ? pos[0] * SQUARE_SIZE : pos[0] * SQUARE_SIZE + SQUARE_SIZE / 2);
		component.getBody().setTranslateY(isObstacle ? pos[1] * SQUARE_SIZE : pos[1] * SQUARE_SIZE + SQUARE_SIZE / 2);
	}
	
	/**
	 * takes an index and returns its corresponding 2d position
	 *
	 * @param index
	 * @return an int array of position (x,y)
	 */
	int[] indexToPos(int index){
		return new int[]{index / map[0].length, index % map[0].length};
	}
	
	/**
	 * finds the charge in the simulation
	 *
	 * @return charge of this simulation
	 */
	public Charge findCharge(){
		for (Component[] row : this.map){
			for (Component charge : row){
				if(charge != null && charge.getType().equals("charge")){
					return (Charge) charge;
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns a grid position arr(x,y) using absolute positions on the screen, only used for sim display
	 *
	 * @param translateX
	 * @param translateY
	 * @return an int array of position (x,y)
	 */
	public int[] absolutePosToGridPosDisplay(double translateX, double translateY){
		return new int[]{(int) (translateX / SQUARE_SIZE), (int) (translateY / SQUARE_SIZE)};
	}
	
	/**
	 * Calls the move method for the charge in all simulations
	 */
	public void moveAllCharges(){
		for (Simulation sim : simulationList){
			for (Component[] row : sim.map){
				for (Component charge : row){
					if(charge != null && charge.getType().equals(Charge.TYPE) && ((Charge) charge).isAlive()){
						Charge tempCharge = (Charge) charge;
						tempCharge.move(sim.checkCollision());
						
						if(tempCharge.getTranslateX() < 0 || tempCharge.getTranslateX() > SQUARE_SIZE * GRID_SIZE_X){
							tempCharge.setAlive(false);
						}
						if(tempCharge.getTranslateY() < 0 || tempCharge.getTranslateY() > SQUARE_SIZE * GRID_SIZE_Y){
							tempCharge.setAlive(false);
						}
					}
				}
			}
		}
	}
	
	/**
	 * returns the component colliding with the charge
	 *
	 * @return the component colliding with the charge
	 */
	public Component checkCollision(){
		Charge charge = this.findCharge();
		int[] componentPos = absolutePosToGridPos(charge.getTranslateX(), charge.getTranslateY());
		if(map[componentPos[0]][componentPos[1]] != null){
		}
		return this.map[componentPos[0]][componentPos[1]];
	}
	
	/**
	 * Returns a grid position arr(x,y) using absolute positions on the screen
	 *
	 * @param translateX
	 * @param translateY
	 * @return int array with two components
	 */
	public int[] absolutePosToGridPos(double translateX, double translateY){
		//TODO MAKE IT WORK THIS METHOD BREAKS EVERYTHING
		return new int[]{(int) (translateX / (SQUARE_SIZE * calculateScale()[0])), (int) (translateY / (SQUARE_SIZE * calculateScale()[1]))};
	}
	
	/**
	 * calculates the scale depening on the number of simulations
	 *
	 * @return double array(scaleX,scaleY)
	 */
	public double[] calculateScale(){
		int numSimulations = Simulation.getSimulationList().size();
		int numRows = (int) Math.ceil(Math.sqrt(numSimulations));
		int numCols = (int) Math.ceil((double) numSimulations / numRows);
		
		// Determine the maximum number of rows or columns based on the x/y ratio
		int maxDimension = Math.max(numRows, numCols);
		
		double scale = 1.0 / maxDimension;
		
		return new double[]{scale, scale};
	}
	
	public static ArrayList<Simulation> getSimulationList(){
		return simulationList;
	}
	
	Component checkRightValue(int index){
		return posToValue(indexToPos(++ index));
	}
	
	/**
	 * takes the 2d position on the grid and returns its corresponding component
	 *
	 * @param pos
	 * @return an component
	 */
	Component posToValue(int[] pos){
		return map[pos[0]][pos[1]];
	}
	
	Component checkLeftValue(int index){
		return posToValue(indexToPos(-- index));
	}
	
	Component checkUpValue(int index){
		index -= GRID_SIZE_Y;
		return posToValue(indexToPos(index));
	}
	
	Component checkDownValue(int index){
		index += GRID_SIZE_Y;
		return posToValue(indexToPos(index));
	}
	
	//getters and setters (will later be removed using lombok)
	public Pane getSimPane(){
		return simPane;
	}
	
	public void setSimPane(Pane simPane){
		this.simPane = simPane;
	}
	
	public myTimer getTimerInstance(){
		return (timer == null) ? new myTimer() : timer;
	}
	
	public class myTimer extends AnimationTimer{
		@Override
		public void handle(long now){
			moveAllCharges();
		}
	}
}
