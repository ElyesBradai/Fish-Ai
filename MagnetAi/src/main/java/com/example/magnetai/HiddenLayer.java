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
    public double[] activate(double[] input) {
        Component[] output = new Component[weights.length];
        for (int i = 0; i < weights.length; i++) {
//
//            double activationValue = calculateActivationValue(input, weights[i]);
//            double activatedValue = ActivationFunctions.sigmoid(activationValue); // Apply sigmoid activation
//            output[i] = applyMagneticFieldDecision(activatedValue);
        }
        return null;
    }

    public MagneticField applyMagneticFieldDecision(double activationValue) {
        
        final double threshold = 0.5;
        if (random.nextDouble() < threshold) {
            // Add magnetic field to empty square based on activation value
            if (activationValue > 0) {
//                return Component.MAGNETIC_FIELD_OUTWARDS;
            }
            else {
//                return Component.MAGNETIC_FIELD_INWARDS;
            }
        }
        else {
//            return Component.EMPTY;
        }
        return null;
    }
    
   
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