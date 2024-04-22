package com.example.magnetai;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Simulation {
    public static final int GRID_SIZE_X = 7;
    public static final int GRID_SIZE_Y = 7;
    public static final int SQUARE_SIZE = 100;
    public static ArrayList<Simulation> simulationList = new ArrayList();
    private final myTimer timer = new myTimer(); // timer is a singleton within the class
    private final int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    Component[][] map;
    Pane simPane;//not sure if i should use pane or other :/
    Rectangle player = new Rectangle(50, 50);
    ArrayList<Rectangle> squareList;
    private NeuralNetwork brain;
    int fitnessScore;

    public Simulation() {
        map = new Component[GRID_SIZE_X][GRID_SIZE_Y];
        simPane = new Pane();
        squareList = new ArrayList<Rectangle>();
        simulationList.add(this);
        fitnessScore = Integer.MAX_VALUE;
        bckg();
    }

    public static ArrayList<Simulation> getSimulationList() {
        return simulationList;
    }

    /**
     * draws a background of tiles
     */
    void bckg() {
        boolean isColored = true;
        for (int i = 0; i < map.length; i++) {
            if (map[0].length % 2 == 0) {
                isColored = !isColored;
            }

            for (int j = 0; j < map[i].length; j++) {
                isColored = !isColored;
                Rectangle temp = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, isColored ? Color.BROWN : Color.BEIGE);
                simPane.getChildren().add(temp);
                squareList.add(temp);
                temp.setTranslateX(i * SQUARE_SIZE);
                temp.setTranslateY(j * SQUARE_SIZE);
            }
        }
    }

    /**
     * add the specified component at the specified index
     *
     * @param component
     * @param index
     */
    public void addToMap(Component component, int index) {
        int[] pos = indexToPos(index);
        if (component.getType().equals(Charge.TYPE) && findCharge() != null) {
            Charge charge = findCharge();
            int[] posOfCharge = indexToPos(charge.getIndex());
            simPane.getChildren().remove(charge.getBody());
            map[posOfCharge[0]][posOfCharge[1]] = null;
        }

        if (component.getType().equals(FinishLine.TYPE) && findFinish() != null) {
            FinishLine fl = findFinish();
            int[] posOfCharge = indexToPos(fl.getIndex());
            simPane.getChildren().remove(fl.getBody());
            map[posOfCharge[0]][posOfCharge[1]] = null;

        }

        if (map[pos[0]][pos[1]] == null) {

            simPane.getChildren().add(component.getBody());
        } else {
            simPane.getChildren().remove(map[pos[0]][pos[1]].getBody());
            simPane.getChildren().add(component.getBody());
        }
        map[pos[0]][pos[1]] = component;

        boolean isObstacle = component.getType().equals(Obstacle.TYPE)
                || component.getType().equals(Superconductor.TYPE)
                || component.getType().equals(FinishLine.TYPE)
                || component.getType().equals(MagneticField.TYPE);

        component.getBody().setTranslateX(isObstacle ? pos[0] * SQUARE_SIZE : pos[0] * SQUARE_SIZE + SQUARE_SIZE / 2);
        component.getBody().setTranslateY(isObstacle ? pos[1] * SQUARE_SIZE : pos[1] * SQUARE_SIZE + SQUARE_SIZE / 2);
    }

    /**
     * takes an index and returns its corresponding 2d position
     *
     * @param index
     * @return an int array of position (x,y)
     */
    public int[] indexToPos(int index) {
        return new int[]{index / map[0].length, index % map[0].length};
    }

    /**
     * finds the charge in the simulation
     *
     * @return charge of this simulation
     */
    public Charge findCharge() {
        for (Component[] row : this.map) {
            for (Component comp : row) {
                if (comp != null && comp.getType().equals(Charge.TYPE)) {
                    return (Charge) comp;
                }
            }
        }
        return null;
    }

    public FinishLine findFinish() {
        for (Component[] row : this.map) {
            for (Component comp : row) {
                if (comp != null && comp.getType().equals(FinishLine.TYPE)) {
                    return (FinishLine) comp;
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
    public int[] absolutePosToGridPosDisplay(double translateX, double translateY) {
        return new int[]{(int) (translateX / SQUARE_SIZE), (int) (translateY / SQUARE_SIZE)};
    }

    /**
     * Calls the move method for the charge in all simulations
     */
    public void moveAllCharges() {
        for (Simulation sim : simulationList) {
            for (Component[] row : sim.map) {
                for (Component charge : row) {
                    if (charge != null && charge.getType().equals(Charge.TYPE) && ((Charge) charge).isAlive()) {
                        Charge tempCharge = (Charge) charge;
                        tempCharge.move(sim.checkCollision(), calculateScale());

                        if (tempCharge.getTranslateX() < 0 || tempCharge.getTranslateX() > (SQUARE_SIZE * calculateScale()) * GRID_SIZE_X) {
                            tempCharge.setAlive(false);
                        }
                        if (tempCharge.getTranslateY() < 0 || tempCharge.getTranslateY() > (SQUARE_SIZE * calculateScale()) * GRID_SIZE_Y) {
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
    public Component checkCollision() {
        Charge charge = this.findCharge();
        int[] componentPos = absolutePosToGridPos(charge.getTranslateX(), charge.getTranslateY());
        //if(map[componentPos[0]][componentPos[1]] != null){
//        if (this.map[componentPos[0]][componentPos[1]] != null && this.map[componentPos[0]][componentPos[1]].getType().equals(Obstacle.TYPE))
//            System.out.println("The fitness score is " + calcutateFitnessScore() + " in sim " + simulationList.indexOf(this));
        return this.map[componentPos[0]][componentPos[1]];
        //}
        //return null;
    }

    public boolean checkAllAlive() {
        boolean alive = false;
        for (Simulation sim : simulationList) {
            if (sim.findCharge().isAlive) {
                alive = true;
            }
        }
        return alive;
    }

    public void setupNextGeneration() {
        if (!checkAllAlive()) {
            for (Simulation sim : simulationList) {
                sim.setFitnessScore(sim.calcutateFitnessScore());
            }
            resetAllSim();
            mutateAllSim();
        }
    }

    public void mutateAllSim() {
        int bestFitnessValue = Integer.MAX_VALUE;
        Simulation bestFitnessSim = null;

        for (Simulation sim : simulationList) {
            int currentFitness = sim.getFitnessScore();
            if (currentFitness < bestFitnessValue) {
                bestFitnessSim = sim;
                bestFitnessValue = currentFitness;
            }
        }

        for (Simulation sim : simulationList) {

            if (!sim.equals(bestFitnessSim)) {
                sim.getBrain().mutate();
                sim.setFitnessScore(Integer.MAX_VALUE);
            }
        }
    }

    public void resetAllSim() {
        //resets all charges
        for (Simulation sim : simulationList) {
            Charge charge = sim.findCharge();
            int[] pos = indexToPos(charge.getIndex());
            charge.getBody().setTranslateX((pos[0] * SQUARE_SIZE + SQUARE_SIZE / 2) * calculateScale());
            charge.getBody().setTranslateY((pos[1] * SQUARE_SIZE + SQUARE_SIZE / 2) * calculateScale());
            charge.setAlive(true);
            //removes all magnetic fields
            for (Component[] row : sim.map) {

                for (Component component : row) {

                    if (component != null && component.getType().equals(MagneticField.TYPE)) {
                        int[] componentPos = indexToPos(component.getIndex());
                        map[componentPos[0]][componentPos[1]] = null;
                        sim.getSimPane().getChildren().remove(component.getBody());
                    }
                }
            }
        }
    }

    public int calcutateFitnessScore() {
        Charge charge = this.findCharge();
        int[] endPosition = findNearestEmpty(charge);
        int fitness = calculateShortestPath(endPosition);
        return fitness;
    }

    public int[] findNearestEmpty(Charge charge) {
        return absolutePosToGridPos(charge.getTranslateX() - charge.getVelocity()[0] * calculateScale(),
                                    charge.getTranslateY() - charge.getVelocity()[1] * calculateScale());
    }

    public int calculateShortestPath(int[] endPostion) {
        int[][] distance = new int[GRID_SIZE_X][GRID_SIZE_Y];
        int finalDist = -1;
        for (int[] row : distance)
            Arrays.fill(row, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]); // {row, col, distance}


        pq.offer(new int[]{endPostion[0], endPostion[1], 0});
        distance[endPostion[0]][endPostion[1]] = 0;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int row = current[0];
            int col = current[1];
            int dist = current[2];

            if (map[row][col] != null && map[row][col].getType().equals(FinishLine.TYPE)) // Reached finish line
                finalDist = dist;

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                if (isValid(newRow, newCol) &&
                        (map[newRow][newCol] == null ||
                                map[newRow][newCol].getType().equals(Charge.TYPE) ||
                                map[newRow][newCol].getType().equals(FinishLine.TYPE) ||
                                map[newRow][newCol].getType().equals(MagneticField.TYPE))) {
                    int newDist = dist + 1;
                    if (newDist < distance[newRow][newCol]) {
                        distance[newRow][newCol] = newDist;
                        pq.offer(new int[]{newRow, newCol, newDist});
                    }
                }
            }
        }

        // Highlight shortest path
        int[] current = indexToPos(findFinish().getIndex());
        int steps = distance[current[0]][current[1]];
        while (steps > 0) {
            //	System.out.println(steps);
            for (int[] dir : directions) {
                int newRow = current[0] + dir[0];
                int newCol = current[1] + dir[1];
                if (isValid(newRow, newCol) && distance[newRow][newCol] == steps - 1) {
                    this.squareList.get(posToIndex(new int[]{newRow, newCol})).setFill(Color.YELLOW);
                    current = new int[]{newRow, newCol};
                    steps--;
                    break;
                }
            }
        }
        return finalDist;
    }

    /**
     * Returns a grid position arr(x,y) using absolute positions on the screen
     *
     * @param translateX
     * @param translateY
     * @return int array with two components
     */
    public int[] absolutePosToGridPos(double translateX, double translateY) {
        //TODO MAKE IT WORK THIS METHOD BREAKS EVERYTHING
        return new int[]{(int) (translateX / (SQUARE_SIZE * calculateScale())), (int) (translateY / (SQUARE_SIZE * calculateScale()))};
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < GRID_SIZE_X && col >= 0 && col < GRID_SIZE_Y;
    }

    /**
     * takes an int array of position (x,y) and returns its corresponding index
     *
     * @param pos
     * @return an int index
     */
    int posToIndex(int[] pos) {
        return pos[0] * GRID_SIZE_Y + pos[1];
    }

    /**
     * calculates the scale depening on the number of simulations
     *
     * @return double array(scaleX,scaleY)
     */
    public double calculateScale() {
        Screen screen = Screen.getPrimary();
        
        // Get the bounds of the primary screen
        Rectangle2D bounds = screen.getVisualBounds();
        
        double screenWidth = bounds.getWidth();
        double screenHeight = bounds.getHeight();
        
        double realSquareSizeW = screenWidth/ GRID_SIZE_X;
        double realSquareSizeH = screenHeight/ GRID_SIZE_Y;
        
        double minSquareSize = Math.min(realSquareSizeH, realSquareSizeW);
        
        double ratioSquareSize = minSquareSize/ SQUARE_SIZE;
        
        double ratio = screenWidth/screenHeight;
        
        int numSimulations = Simulation.getSimulationList().size();
        double numRows = Math.sqrt(numSimulations);
        double numCols = Math.ceil((double) numSimulations / numRows);

        // Determine the maximum number of rows or columns based on the x/y ratioSquareSize
        double minDimension = Math.max(numRows, numCols);

        double scale = 1.0 / minDimension;
        

        return ratioSquareSize * scale;
    }

    Component checkRightValue(int index) {
        return posToValue(indexToPos(++index));
    }

    /**
     * takes the 2d position on the grid and returns its corresponding component
     *
     * @param pos
     * @return an component
     */
    Component posToValue(int[] pos) {
        return map[pos[0]][pos[1]];
    }

    Component checkLeftValue(int index) {
        return posToValue(indexToPos(--index));
    }

    Component checkUpValue(int index) {
        index -= GRID_SIZE_Y;
        return posToValue(indexToPos(index));
    }

    Component checkDownValue(int index) {
        index += GRID_SIZE_Y;
        return posToValue(indexToPos(index));
    }

    //getters and setters (will later be removed using lombok)
    public Pane getSimPane() {
        return this.simPane;
    }

    public void setSimPane(Pane simPane) {
        this.simPane = simPane;
    }

    public NeuralNetwork getBrain() {
        return this.brain;
    }

    public int getFitnessScore() {
        return this.fitnessScore;
    }

    public void setFitnessScore(int fitnessScore) {
        this.fitnessScore = fitnessScore;
    }

    public void setBrain(NeuralNetwork brain) {
        this.brain = brain;
    }

    public myTimer getTimerInstance() {
        return (timer == null) ? new myTimer() : timer;
    }

    public class myTimer extends AnimationTimer {
        @Override
        public void handle(long now) {
            moveAllCharges();
            setupNextGeneration();
        }
    }
}
