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
     * creates a hidden Layer/
     * @param currentLayerSize the size of the current layer
     * @param previousLayerSize the size of the previous layer
     */
    public HiddenLayer(int currentLayerSize, int previousLayerSize) {
        this.currentLayerSize = currentLayerSize;
        this.previousLayerSize = previousLayerSize;
        weights = new double[currentLayerSize][previousLayerSize];
        initRandom();
    }

    /**
     * clones an object of this HiddenLayer.
     * @return a cloned HiddenLayer.
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
     * mutates the weights of the Hidden Layer.
     * @param learningRate the learning rate of the Neural Network
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
    
    /**
     * randomizes the weights.
     */
    private void initRandom() {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j] = 2 * Math.random() - 1; // [-1 .. 1)
            }
        }
    }

    /**
     * returns the values of the next layer using activation functions.
     * @param input the input layer
     * @return the values of the next layer.
     */
    public double[] activate(double[] input) {
        double[] output = new double[weights.length];
        for (int i = 0; i < weights.length; i++) {
            output[i] = ActivationFunctions.tanh(MathFunctions.multiplyVectors(input, weights[i]));
        }
        return output;
    }
    
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
    
    public double[][] getWeights() {
        return weights;
    }

}