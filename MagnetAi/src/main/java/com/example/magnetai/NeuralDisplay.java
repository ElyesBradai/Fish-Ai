package com.example.magnetai;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Screen;


import java.util.ArrayList;

/**
 *
 * This class consists of the visible Neural Display accessible in Simulation.
 */
public class NeuralDisplay extends Pane {
    public static double width;
    public static double height;
    private static double panePadding;
    private Simulation displayedSim;
    private NeuralNetwork neuralNetwork;
    private double[][] activations;
    private HiddenLayer[] layers;
    private int[] nbLayers;
    private ArrayList<Line> lineWeights = new ArrayList();
    private ArrayList<ArrayList<Circle>> neuronList = new ArrayList();

    /**
     * creates a Neural Display for the simulation.
     * @param simulation the Neural Display's simulation
     */
    public NeuralDisplay(Simulation simulation) {
        this.setPrefSize(width, height);
        width = 2.5 * Simulation.SQUARE_SIZE * simulation.getBrain().getLayers().length * calculateNeuralScale();
        height = 2 * Simulation.SQUARE_SIZE * simulation.getBrain().getLayers()[0] * calculateNeuralScale() / 16;
        panePadding = 25 * calculateNeuralScale();
        this.displayedSim = simulation;
        this.activations = displayedSim.getBrain().getActivations();
        this.neuralNetwork = displayedSim.getBrain();
        this.layers = displayedSim.getBrain().getHiddenLayers();

        nbLayers = simulation.getBrain().getLayers();
        generateNeurons();
        generateWeight();
    }
    
    /**
     * generates the neuron circles for the Neural Display.
     */
    private void generateNeurons() {
        int numberOfLayers = activations.length;
        double disposableWidth = width - 2 * panePadding;
        double disposableHeight = height - 2 * panePadding;
        double layerGap = disposableWidth / (numberOfLayers + 1);

        for (int i = 0; i < activations.length; i++) {
            neuronList.add(new ArrayList<>());
            double heightGap = disposableHeight / (activations[i].length + 1);
            for (int j = 0; j < activations[i].length; j++) {

                Label value = new Label();
                value.setId("neuronNet");
                DoubleProperty prop = new SimpleDoubleProperty(activations[i][j]);
                value.textProperty().bind(prop.asString("%.2f"));
                Circle neuron = new Circle(20 * calculateNeuralScale());
                neuron.setUserData(prop);
                neuron.setCenterX(layerGap * (i + 0.5) - (this.getWidth()));
                neuron.setCenterY(heightGap * (j + 0.5) - (this.getHeight()));
                value.setTranslateX(layerGap * (i + 0.5) - (10));
                value.setTranslateY(heightGap * (j + 0.5) - (5));

                this.getChildren().addAll(neuron, value);
                neuronList.get(i).add(neuron);
            }
        }
    }
    
    /**
     * generates the weight lines for the Neural Display.
     */
    private void generateWeight() {
        for (int layer = 0; layer < layers.length; layer++) {
            for (int currNeuron = 0; currNeuron < layers[layer].getWeights().length; currNeuron++) {
                for (int prevNeuron = 0; prevNeuron < activations[layer].length; prevNeuron++) {
                    double k =  layers[layer].getWeights()[currNeuron][prevNeuron];
                    k = Math.abs(k);
                    Line line = new Line();
                    DoubleProperty value = new SimpleDoubleProperty(k);
                    line.setUserData(value);
                    line.startXProperty().bind(neuronList.get(layer+1).get(currNeuron).centerXProperty());
                    line.startYProperty().bind(neuronList.get(layer+1).get(currNeuron).centerYProperty());
                    line.endXProperty().bind(neuronList.get(layer).get(prevNeuron).centerXProperty());
                    line.endYProperty().bind(neuronList.get(layer).get(prevNeuron).centerYProperty());
                    line.opacityProperty().bind((value.divide(2)).multiply(calculateNeuralScale()).add(0.2));
                    line.strokeWidthProperty().bind(((value.divide(1.5)).add(0.5)).multiply(3 * calculateNeuralScale()));
                    line.setOnMouseClicked((e) -> {
                        System.out.println(value.get());
                    });
                    this.getChildren().add(line);
                    line.toBack();
                }
            }
        }
    }
    
    /**
     *
     * @return a double value for the scaling of the Neural Display.
     */
    public double calculateNeuralScale() {
        if (this.displayedSim != null) {
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            double screenWidth = bounds.getWidth();
            double screenHeight = bounds.getHeight();
            double realSquareSizeW = screenWidth/ this.displayedSim.getBrain().getLayers().length;
            double realSquareSizeH = screenHeight/ this.displayedSim.getBrain().getLayers()[0];
            double minSquareSize = Math.min(realSquareSizeH, realSquareSizeW);
            double ratioSquareSize = minSquareSize/ Simulation.SQUARE_SIZE;
            int numSimulations = Simulation.getSimulationList().size();
            double numRows = Math.sqrt(numSimulations);
            double numCols = ((double) numSimulations / numRows);
            double maxDimension = Math.max(numRows, numCols);
            double scale = 1.0 / maxDimension;
            return ratioSquareSize * scale;
            }
        return 1;
    }
}