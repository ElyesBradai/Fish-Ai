package com.example.magnetai;

import java.util.Random;

/**
 * Class which consists of the hidden layer used in the machine learning model.
 */
public class HiddenLayer {

    private final double[][] weights;
    private final int currentLayerSize;
    private final int previousLayerSize;
    private final Random random = new Random();

    /**
     * @param currentLayerSize
     * @param previousLayerSize
     */
    public HiddenLayer(int currentLayerSize, int previousLayerSize) {
        this.currentLayerSize = currentLayerSize;
        this.previousLayerSize = previousLayerSize;
        weights = new double[currentLayerSize][previousLayerSize];
        initRandom();
    }

    /**
     * @return
     */
    @Override
    public HiddenLayer clone() {
        HiddenLayer hiddenLayer = new HiddenLayer(this.currentLayerSize, this.previousLayerSize);
        for (int i = 0; i < this.currentLayerSize; i++) {
            if (this.previousLayerSize >= 0)
                System.arraycopy(this.weights[i], 0, hiddenLayer.weights[i], 0, this.previousLayerSize);
        }
        return hiddenLayer;
    }

    /**
     * @param learningRate
     */
    public void mutate(float learningRate) {
        for (int i = 0; i < currentLayerSize; i++) {
            for (int j = 0; j < previousLayerSize; j++) {
                if (random.nextDouble() < learningRate) {
                    // Or Randomize the weight completely
                    weights[i][j] = random.nextDouble(-1, 1);
                }
            }
        }
    }

    private void initRandom() {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j] = 2 * Math.random() - 1; // [-1 .. 1)
            }
        }
    }

    /**
     * @param input
     * @return
     */
    public Component[] activate(Component[] input) {
        Component[] output = new Component[weights.length];
        for (int i = 0; i < weights.length; i++) {
            //TODO DETERMINE A WAY OF CHOOSING THE OUTPUT
//            output[i] = ActivationFunctions.tanh(MathFunctions.multiplyVectors(input, weights[i]));
        }

        return output;
    }

    /*
    public Component[] activate(Component[] input) {
    Component[] output = new Component[weights.length];

    // Step 1: Randomly place magnetic fields
    for (int i = 0; i < weights.length; i++) {
        if (random.nextDouble() < 0.5) { // Adjust the probability to control randomness
            output[i] = Component.MAGNETIC_FIELD_OUTWARD;
        }
    }

    // Step 2: Adjust placements based on fitness score
    double initialFitness = calculateFitness(input, output);
    for (int attempt = 0; attempt < MAX_OPTIMIZATION_ATTEMPTS; attempt++) {
        // Generate a mutated version of the current placement
        Component[] mutatedOutput = mutatePlacement(output);

        // Calculate fitness for the mutated placement
        double mutatedFitness = calculateFitness(input, mutatedOutput);

        // If the mutated placement improves fitness, accept it
        if (mutatedFitness > initialFitness) {
            output = mutatedOutput;
            initialFitness = mutatedFitness;
        }
    }

    return output;
}

// Method to calculate fitness score based on input and output placements
private double calculateFitness(Component[] input, Component[] output) {
    // Your fitness calculation logic here
}

// Method to mutate the current placement to explore new configurations
private Component[] mutatePlacement(Component[] placement) {
    Component[] mutatedPlacement = new Component[placement.length];
    System.arraycopy(placement, 0, mutatedPlacement, 0, placement.length);

    // Randomly select a magnetic field to toggle its presence
    int randomIndex = random.nextInt(mutatedPlacement.length);
    mutatedPlacement[randomIndex] = (mutatedPlacement[randomIndex] == Component.MAGNETIC_FIELD_OUTWARD) ?
            Component.EMPTY : Component.MAGNETIC_FIELD_OUTWARD;

    return mutatedPlacement;
}

    * */

    /**
     * @return
     */
    public String toString() {
        String ret = "[";

        for (double[] currentLayerIndex : this.weights) {
            for (double value : currentLayerIndex) {
                ret += value + ", ";
            }
            ret += "]\n";
        }

        return ret;
    }

    /**
     * @return
     */
    public double[][] getWeights() {
        return weights;
    }

}