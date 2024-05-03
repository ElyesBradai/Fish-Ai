package com.example.magnetai;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.*;

/**
 * the simulation class manages every simulation and simulation pane.
 */
public class Simulation {
    public static int GRID_SIZE_X;
    public static int GRID_SIZE_Y;
    public static double SQUARE_SIZE;
    public static int generationCounter = 0;
    public static int[] layerInput;
    public static float mutationRate;
    private static boolean isSolved = false;
    public static double universalScale;
    public static boolean isScaleCalculated = false;
    private static boolean isEndScreenShown = false;
    private static Simulation bestFitnessSim = null;
    public static FlowPane root;
    public static NeuralDisplay neuralDisplay;
    public static ArrayList<Simulation> simulationList = new ArrayList();
    private final myTimer timer = new myTimer(); // timer is a singleton within the class
    private final int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    Component[][] map;
    Pane simPane;
    public static double[] dimensions = new double[2];
    

    ArrayList<Rectangle> squareList;
    private NeuralNetwork brain;
    int fitnessScore;

    /**
     * creates an object of Simulation.
     */
    public Simulation() {
        map = new Component[GRID_SIZE_X][GRID_SIZE_Y];
        simPane = new Pane();
        squareList = new ArrayList<Rectangle>();
        simulationList.add(this);
        fitnessScore = Integer.MAX_VALUE;
        brain = null;
    }

    public static ArrayList<Simulation> getSimulationList() {
        return simulationList;
    }

    /**
     * draws a background of tiles.
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
     * add the specified component at the specified index.
     * a charge and a finishline can only be added once.
     * @param component the component to be added
     * @param index the index where the component will be added
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
     * takes an index and returns its corresponding 2d position.
     * @param index the index to be converted to a position
     * @return an int array of position (x,y).
     */
    public int[] indexToPos(int index) {
        return new int[]{index / map[0].length, index % map[0].length};
    }

    /**
     * finds the charge in the simulation.
     *
     * @return charge of this simulation.
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

    /**
     * finds the finishLine in the simulation.
     * @return the finishLine in the simulation.
     */
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
     * returns a grid position arr(x,y) using absolute positions on the screen, only used for sim display.
     *
     * @param translateX the translateX of the node on the simulation display pane
     * @param translateY the translateY of the node on the simulation display pane
     * @return an int array of position (x,y).
     */
    public int[] absolutePosToGridPosDisplay(double translateX, double translateY) {
        return new int[]{(int) (translateX / SQUARE_SIZE), (int) (translateY / SQUARE_SIZE)};
    }

    /**
     * calls the move method for the charge in all simulations.
     */
    public void moveAllCharges() {
        for (Simulation sim : simulationList) {
            for (Component[] row : sim.map) {
                for (Component charge : row) {
                    if (charge != null && charge.getType().equals(Charge.TYPE) && ((Charge) charge).isAlive()) {
                        Charge tempCharge = (Charge) charge;
                        tempCharge.move(sim.checkCollision());

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
     * returns the component colliding with the charge.
     *
     * @return the component colliding with the charge.
     */
    public Component checkCollision() {
        Charge charge = this.findCharge();
        int[] componentPos = absolutePosToGridPos(charge.getTranslateX(), charge.getTranslateY());
        return this.map[componentPos[0]][componentPos[1]];
    }

    /**
     * checks of all the charges are alive.
     * @return true if atleast one charge is alive amongst all the simulations in the simulation list.
     */
    public boolean checkAllAlive() {
        boolean alive = false;
        for (Simulation sim : simulationList) {
            if (sim.findCharge().isAlive) {
                alive = true;
            }
        }
        return alive;
    }

    /**
     * sets up the following generation of simulations when all the charges are dead.
     */
    public void setupNextGeneration() {
        if (!checkAllAlive()) {
            for (Simulation sim : simulationList) {
                sim.setFitnessScore(sim.calculateFitnessScore());
            }
            resetAllSim();
            if (!isSolved) {
                mutateAllSim();
                createBrains();
                System.out.println(++generationCounter);
            }
            else {
                showEndScreen();
            }
        }
    }

    /**
     * mutates all the simulations according to the best performing simulation.
     * scales the learning rate according to the performance of the best simulation.
     */
    public void mutateAllSim() {
        int bestFitnessValue = Integer.MAX_VALUE;
        Simulation solvedSim = null;
        for (Simulation sim : simulationList) {
            int currentFitness = sim.getFitnessScore();
            if (currentFitness < bestFitnessValue) {
                bestFitnessSim = sim;
                bestFitnessValue = currentFitness;
                if (sim.findCharge().isFinished()) {
                    solvedSim = sim;
                    bestFitnessSim = sim;
                    break;
                }
            }
        }
        float baseLearningRate = bestFitnessSim.getBrain().getLearningRate();
        float minLearningRate = 0.05f;
        float scaledLearningRate = baseLearningRate / (fitnessScore + minLearningRate);
        // Ensure the learning rate doesn't exceed the base learning rate or go below the minimum
        scaledLearningRate = Math.max(minLearningRate, scaledLearningRate);
        scaledLearningRate = Math.min(baseLearningRate, scaledLearningRate);
        for (Simulation sim : simulationList) {
            if (sim != bestFitnessSim && sim != solvedSim) {
                sim.setBrain(solvedSim == null ? bestFitnessSim.getBrain().clone(scaledLearningRate) : solvedSim.getBrain().clone(scaledLearningRate));
                sim.getBrain().mutate();
                sim.setFitnessScore(Integer.MAX_VALUE);
            }
        }
    }

    /**
     * creates the neural network for every simulation.
     * adds the magnetic fields to the simulation.
     */
    public static void createBrains() {
        //this part creates decides the values (0 if empty or 1 if other)
        int emptyCounter = 0;
        Deque<Double> inputMap = new ArrayDeque();
        for (Component[] row : simulationList.get(0).map) {
            for (Component component: row) {
                if (component == null || component.getType().equals(MagneticField.TYPE)){
                    inputMap.add(-1.0);
                    emptyCounter++;
                 }
                else {
                    inputMap.add(1.0);
                }
            }
        }

        //this part creates the brain (neural network) and call the predict method
        for (Simulation sim : simulationList) {
            int counter = 1; //to use within the empty array
            if (sim.getBrain() == null)
                {
                    Deque<Integer> allLayersList = new ArrayDeque<Integer>();
                    if (layerInput != null)
                    {
                        for (int nbNeurons : layerInput) {
                            allLayersList.add(nbNeurons);
                        }
                    }
                    allLayersList.addFirst(GRID_SIZE_X * GRID_SIZE_Y);
                    allLayersList.addLast(emptyCounter + 1);
                    int[] input = allLayersList.stream().mapToInt(Integer::intValue).toArray();
                    sim.setBrain(new NeuralNetwork(mutationRate, input));
                }
            double[] predictions = sim.getBrain().predict(inputMap.stream().mapToDouble(Double::doubleValue).toArray());
            double angle = predictions[0] * Math.PI; //index 0 is the angle for the charge and the rest is the strength
            Charge charge = sim.findCharge();
            charge.setAngle(angle);
            charge.setNewVelocity(angle);
            int index = 0;
            for (Component[] row : sim.map) {
                for (Component component : row) {
                    if (component == null || component.getType().equals(MagneticField.TYPE)) {
                        component = new MagneticField(index, new double[] {0, 0, predictions[counter]*MagneticField.STRENGTH_COEFFICIENT});
                        sim.addToMap(component, component.getIndex());
                        double scale = calculateScale();
                        ((Rectangle) component.getBody()).setWidth(((Rectangle) component.getBody()).getWidth() * scale);
                        ((Rectangle) component.getBody()).setHeight(((Rectangle) component.getBody()).getHeight() * scale);
                        component.getBody().setTranslateX(component.getBody().getTranslateX() * scale);
                        component.getBody().setTranslateY(component.getBody().getTranslateY() * scale);
                        component.getBody().setStrokeWidth(component.getBody().getStrokeWidth() * scale);
                        if (((MagneticField)component).getStrength()[2] > 0) {
                            component.getBody().setFill(Color.DARKGREEN);
                        }
                        counter++;
                    }
                    sim.findCharge().toFront();
                    index++;
                }
            }
        }
    }

    /**
     * resets all the simulations.
     */
    public void resetAllSim() {
        //resets all charges
        for (Simulation sim : simulationList) {
            Charge charge = sim.findCharge();
            int[] pos = indexToPos(charge.getIndex());
            charge.getBody().setTranslateX((pos[0] * SQUARE_SIZE + SQUARE_SIZE / 2) * calculateScale());
            charge.getBody().setTranslateY((pos[1] * SQUARE_SIZE + SQUARE_SIZE / 2) * calculateScale());
            charge.setAlive(true);
            charge.setSpeed(charge.getSpeed());
            charge.setNewVelocity(charge.getAngle());
        }
    }

    /**
     * calculates the shortest path to the finishLine form the Charge.
     * @return the number of squares in the shortest path to the finishLine from the charge.
     */
    public int calculateFitnessScore() {
        Charge charge = this.findCharge();
        int[] endPosition = findNearestEmpty(charge);
        return calculateShortestPath(endPosition);
    }

    /**
     * checks if it is impossible to reach the finishLine in the simulation display.
     * @return true if there is a possible path to the finishLine in the simulation display.
     */
    public boolean checkValidPathDisplay() {
        boolean isValid = true;
        Charge charge = this.findCharge();
        int[] endPosition = absolutePosToGridPosDisplay(charge.getTranslateX(), charge.getTranslateY());
        if (calculateShortestPath(endPosition) == -1){
            isValid = false;
        }
        return isValid;
    }

    /**
     * finds the nearest square in which the charge was on before it died.
     * @param charge the charge in the simulation
     * @return an array of (x,y) position.
     */
    public int[] findNearestEmpty(Charge charge) {
        return absolutePosToGridPos(charge.getTranslateX() - charge.getVelocity()[0] * calculateScale(),
                                    charge.getTranslateY() - charge.getVelocity()[1] * calculateScale());
    }

    /**
     * finds the shortest path to the finishLine from the charge.
     * @param endPosition the final position of the charge before it died
     * @return the number of squares in the shortest path from the charge to the finishLine.
     */
    public int calculateShortestPath(int[] endPosition) {
        int[][] distance = new int[GRID_SIZE_X][GRID_SIZE_Y];
        int finalDist = -1;
        for (int[] row : distance) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]); // {row, col, distance}


        pq.offer(new int[]{endPosition[0], endPosition[1], 0});
        distance[endPosition[0]][endPosition[1]] = 0;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int row = current[0];
            int col = current[1];
            int dist = current[2];

            // Reached finish line
            if (map[row][col] != null && map[row][col].getType().equals(FinishLine.TYPE)) {
                finalDist = dist;
            }

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                if (isValid(newRow, newCol) &&
                        (map[newRow][newCol] == null ||
                                map[newRow][newCol].getType().equals(Charge.TYPE) ||
                                map[newRow][newCol].getType().equals(FinishLine.TYPE) ||
                                map[newRow][newCol].getType().equals(Superconductor.TYPE) ||
                                map[newRow][newCol].getType().equals(MagneticField.TYPE))) {
                    int newDist = dist + 1;
                    if (newDist < distance[newRow][newCol]) {
                        distance[newRow][newCol] = newDist;
                        pq.offer(new int[]{newRow, newCol, newDist});
                    }
                }
            }
        }
        int maxLoop = 0;
        int[] current = indexToPos(findFinish().getIndex());
        int steps = distance[current[0]][current[1]];
        while (steps > 0) {
            if (maxLoop >= Math.pow(GRID_SIZE_X * GRID_SIZE_Y + 1,2)) {
                break;
            }
            //	System.out.println(steps);
            for (int[] dir : directions) {
                int newRow = current[0] + dir[0];
                int newCol = current[1] + dir[1];
                if (isValid(newRow, newCol) && distance[newRow][newCol] == steps - 1) {
                    current = new int[]{newRow, newCol};
                    steps--;
                    break;
                }
            }
            maxLoop++;
        }
        return finalDist;
    }
    
    /**
     * returns a grid position arr(x,y) using absolute positions on the screen, used for all simulations.
     *
     * @param translateX the translateX of the node on the simulation pane
     * @param translateY the translateY of the node on the simulation pane
     * @return an int array of position (x,y).
     */
    public int[] absolutePosToGridPos(double translateX, double translateY) {
        return new int[]{(int) (translateX / (SQUARE_SIZE * calculateScale())), (int) (translateY / (SQUARE_SIZE * calculateScale()))};
    }
    
    /**
     * checks whether there is a square in a certain position.
     * @param row the row of the position of the square being checked
     * @param col the column of the position of the square being checked
     * @return true if the row and column are in the simulation.
     */
    private boolean isValid(int row, int col) {
        return row >= 0 && row < GRID_SIZE_X && col >= 0 && col < GRID_SIZE_Y;
    }

    /**
     * takes an int array of position (x,y) and returns its corresponding index.
     *
     * @param pos the position of the component
     * @return an int index of the component.
     */
    int posToIndex(int[] pos) {
        return pos[0] * GRID_SIZE_Y + pos[1];
    }

    /**
     * calculates the scale depending on the number of simulations.
     *
     * @return double array(scaleX,scaleY).
     */
    public static double calculateScale() {
        if (!isScaleCalculated) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double screenWidth = bounds.getWidth();
        double screenHeight = bounds.getHeight();
        double realSquareSizeW = screenWidth/ GRID_SIZE_X;
        double realSquareSizeH = screenHeight/ GRID_SIZE_Y;
        double minSquareSize = Math.min(realSquareSizeH, realSquareSizeW);
        double ratioSquareSize = minSquareSize/ SQUARE_SIZE;
        int numSimulations = Simulation.getSimulationList().size();
        double numRows = Math.sqrt(numSimulations);
        double numCols = ((double) numSimulations / numRows);
        double maxDimension = Math.max(numRows, numCols);
        double scale = 1.0 / maxDimension;
        
        isScaleCalculated = true;
        universalScale = ratioSquareSize * scale;
        return ratioSquareSize * scale;
        }
        else return universalScale;
    }
    
    /**
     * shows the neural display of the simulation.
     * @param sim the simulation that succeeds
     * @param displayRoot the pane in which the neural display will be added
     * @param slider the zoom slider
     */
    private static void showNeuralDisplay(Simulation sim, ScrollPane displayRoot, Slider slider) {
        if (sim != null) {
            if (displayRoot.getContent() != null) {
                displayRoot.setContent(null);
            }
            neuralDisplay = new NeuralDisplay(sim);
            displayRoot.setContent(neuralDisplay);
            neuralDisplay.setMinSize(NeuralDisplay.width, NeuralDisplay.height);

            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            slider.setMajorTickUnit(0.1);
            slider.setBlockIncrement(0.1);
            slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                neuralDisplay.setScaleX(newValue.doubleValue());
                neuralDisplay.setScaleY(newValue.doubleValue());
            });
            displayRoot.setPrefViewportWidth(500);
            displayRoot.setPrefViewportHeight(500);
        }
    }
    
    /**
     * shows the end screen that displays the successful simulation.
     */
    public static void showEndScreen() {
        if (!isEndScreenShown) {
            isEndScreenShown = true;
            //find the best simulation
            //show end screen
            Simulation showedSim = null;
            for (Simulation sim : simulationList) {
                Charge charge = sim.findCharge();
                if (charge.isFinished()) {
                    showedSim = sim;
                }
            }

            Text endText = new Text("The Ai solved the maze! Here is the best attempt.\n" +
                    "Use the slider to see the neural network!");
            endText.setFont(new Font("SansSerif",25));
            VBox endVbox = new VBox(endText, showedSim.getSimPane());
            endVbox.setSpacing(20);
            endVbox.setAlignment(Pos.CENTER);
            ScrollPane endScroll = new ScrollPane();
            endScroll.getStylesheets().add(Simulation.class.getResource("Style.css").toExternalForm());
            Slider slider = new Slider(0.1, 1, 1);
            showNeuralDisplay(showedSim,endScroll,slider);
            FlowPane endRoot = new FlowPane(endVbox,slider,endScroll);
            endScroll.setMinHeight(400);
            endScroll.setMinWidth(400);
            endRoot.setAlignment(Pos.CENTER);
            endRoot.setVgap(30);
            simulationList = new ArrayList<>();
            simulationList.add(showedSim);
            Scene scene = new Scene(endRoot);
            scene.getStylesheets().add(Simulation.class.getResource("Style.css").toExternalForm());
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setFullScreen(true);
            stage.setTitle("Magnet Ai: Training simulations");
            stage.setScene(scene);
            stage.setFullScreen(true);

        }
    }
    
    /**
     * sets the square size of the simulations to fit the screen after they all start.
     */
    public static void calculateSquareSize(){
        double width = dimensions[0];
        double height = dimensions[1];
        double realSquareSizeW = width / GRID_SIZE_X;
        double realSquareSizeH = height / GRID_SIZE_Y;
        double minSquareSize = Math.min(realSquareSizeH, realSquareSizeW);
        Simulation.SQUARE_SIZE = minSquareSize;
        MagneticField.square_size = SQUARE_SIZE;
        FinishLine.square_size = SQUARE_SIZE;
        Obstacle.square_size = SQUARE_SIZE;
        Superconductor.square_size = SQUARE_SIZE;
        Charge.CHARGE_RADIUS = SQUARE_SIZE / 4;
    }

    public Pane getSimPane() {
        return this.simPane;
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

    public static void setSolved(boolean solved) {
        isSolved = solved;
    }

    public static boolean isSolved() {
        return isSolved;
    }

    public void setBrain(NeuralNetwork brain) {
        this.brain = brain;
    }

    public myTimer getTimerInstance() {
        return (timer == null) ? new myTimer() : timer;
    }
    
    public ArrayList<Rectangle> getSquareList(){
        return squareList;
    }

    public class myTimer extends AnimationTimer {
        @Override
        public void handle(long now) {
            moveAllCharges();
            setupNextGeneration();
        }
    }
    
}
