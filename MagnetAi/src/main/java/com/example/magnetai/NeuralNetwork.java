package com.example.magnetai;

import java.util.Arrays;

/**
 * This class consists of the design of the neuralNetwork used for our project.
 */
public class NeuralNetwork {

    private final HiddenLayer[] hiddenLayers;
    private final double[][] activations; // weights : lets say the layer 1 has X nb of nodes, and layer 2 Y nb of nodes, then the 2d arr is Component[x][y]
    // for each "input" node, there is a Y amount of "output" nodes that has its own weight
    private final float learningRate; // Mutation Rate
    private final int[] layers;

    /**
     * @param learningRate
     * @param layers
     */
    public NeuralNetwork(float learningRate, int[] layers) {
        this.learningRate = learningRate;
        this.layers = layers;
        activations = new double[layers.length][];
        hiddenLayers = new HiddenLayer[layers.length - 1];
        activations[0] = new double[layers[0]];
        for (int i = 1; i < layers.length; i++) {
            activations[i] = new double[layers[i]];
            hiddenLayers[i - 1] = new HiddenLayer(layers[i], layers[i - 1]);
        }
    }

    /**
     * @return
     */
    @Override
    public NeuralNetwork clone() {
        NeuralNetwork neuralNetwork = new NeuralNetwork(this.learningRate, this.layers);
        for (int i = 0; i < this.hiddenLayers.length; i++) {
            neuralNetwork.hiddenLayers[i] = this.hiddenLayers[i].clone();
        }
        return neuralNetwork;
    }
    
    /**
     *
     * @param learningRate
     * @return
     */
    public NeuralNetwork clone(float learningRate) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(learningRate, this.layers);
        for (int i = 0; i < this.hiddenLayers.length; i++) {
            neuralNetwork.hiddenLayers[i] = this.hiddenLayers[i].clone();
        }
        return neuralNetwork;
    }

    /**
     * @param input
     * @return
     */
    public double[] predict(double[] input) {
        activations[0] = input;
        for (int i = 0; i < hiddenLayers.length; i++) {
            activations[i + 1] = hiddenLayers[i].activate(activations[i]);
        }
        return activations[activations.length - 1];
    }

    /**
     *
     */
    public void mutate() {
        for (HiddenLayer layer : this.hiddenLayers) {
            layer.mutate(this.learningRate);
        }
    }

    /**
     * @return
     */
    public double[][] getActivations() {
        return activations;
    }

    /**
     * @return
     */
    public HiddenLayer[] getHiddenLayers() {
        return hiddenLayers;
    }

    public float getLearningRate() {
        return this.learningRate;
    }

    /**
     * @return
     */
    public int[] getLayers() {
        return this.layers;
    }

    /**
     * @return
     */
    public String toString() {
        String ret = "learning rate : " + this.learningRate + "\n";
        ret += "Hidden Layers : " + Arrays.toString(this.hiddenLayers) + "\n";
        for (int i = 0; i < this.layers.length; i++) {
            ret += "Hidden layer " + i + Arrays.toString(this.activations[i]) + "\n";
        }
        ret += "Output layer: " + Arrays.toString(this.activations[this.activations.length - 1]) + "\n";
        return ret;
    }

}
