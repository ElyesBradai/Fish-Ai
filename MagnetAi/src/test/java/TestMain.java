import com.example.magnetai.ActivationFunctions;
import com.example.magnetai.MathFunctions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
public class TestMain {
    @Test
    public void testCrossProduct1() {
        double[] result = MathFunctions.crossProduct(new double[]{0,0,1},new double[]{1,0,0});
        assertArrayEquals(new double[]{0,1,0}, result, "cross product result should be {0,1,0}");
    }
    @Test
    public void testCrossProduct2() {
        double[] result = MathFunctions.crossProduct(new double[]{0,0,0},new double[]{0,0,0});
        assertArrayEquals(new double[]{0,0,0}, result,0.00001, "cross product result should be {0,0,0}");
    }
    @Test
    public void testCrossProduct3() {
        assertThrows(IllegalArgumentException.class, () -> {
            MathFunctions.crossProduct(null, new double[]{1, 0, 0});
        },"cross product result should throw an exception");
    }
    @Test
    public void testCalcFinalVelocity1() {
        double charge = MathFunctions.PROTON_CONSTANT;
        double mass = MathFunctions.PROTON_MASS;
        double[] magneticFieldStrength = {0, 0, 1}; // Magnetic field along z-axis
        double[] velocity = {1, 0, 0}; // Initial velocity along x-axis
        double[] result = MathFunctions.calcFinalVelocity(charge, mass, velocity, magneticFieldStrength);
        assertArrayEquals(new double[]{1.0, -1.0, 0.0}, result, 0.0001, "final velocity should be {0,-9.57883315593791E7, 0}");
    }
    @Test
    public void testCalcFinalVelocity2() {
        double charge = MathFunctions.ELECTRON_CONSTANT;
        double mass = MathFunctions.ELECTRON_MASS;
        double[] magneticFieldStrength = {0, 0, 1}; // Magnetic field along z-axis
        double[] velocity = {1, 0, 0}; // Initial velocity along x-axis
        double[] result = MathFunctions.calcFinalVelocity(charge, mass, velocity, magneticFieldStrength);
        assertArrayEquals(new double[]{1, 1, 0}, result, 0.0001, "final velocity should be {0, 1.7588200107721634E11, 0}");
    }
    @Test
    public void testMultiplyVectors1() {
        double[] a = {1, 2, 3};
        double[] b = {4, 5, 6};
        double result = MathFunctions.multiplyVectors(a, b);
        assertEquals(32, result, 1e-6, "result should be 32");
    }
    @Test
    public void testMultiplyVectors2() {
        double[] a = {0, 0, 0};
        double[] b = {1, 2, 3};
        double result = MathFunctions.multiplyVectors(a, b);
        assertEquals(0, result, 1e-6, "result should be 0");
    }
    @Test
    public void testMultiplyVectors3() {
        assertThrows(IllegalArgumentException.class, () -> {
            MathFunctions.multiplyVectors(new double[]{1, 2}, new double[]{3, 4, 5});
        }, "multiplyVectors should throw an exception for input vectors with different lengths");
    }
    @Test
    public void testSigmoid() {
        assertEquals(0.5, ActivationFunctions.sigmoid(0), 0.001);
        assertEquals(0.731, ActivationFunctions.sigmoid(1), 0.001);
        assertEquals(0.268, ActivationFunctions.sigmoid(-1), 0.001);
        assertEquals(1.0, ActivationFunctions.sigmoid(100), 0.001);
        assertEquals(0.0, ActivationFunctions.sigmoid(-100), 0.001);
        assertEquals(0.920, ActivationFunctions.sigmoid(2.5), 0.01);
    }
    
    @Test
    public void testReLU() {
        assertEquals(0.0, ActivationFunctions.relu(-1), 0.001);
        assertEquals(1.0, ActivationFunctions.relu(1), 0.001);
        assertEquals(0.0, ActivationFunctions.relu(0), 0.001);
        assertEquals(100, ActivationFunctions.relu(100), 0.001);
        assertEquals(0.0, ActivationFunctions.relu(-100), 0.001);
        assertEquals(2.5, ActivationFunctions.relu(2.5), 0.001);
    }
    
    @Test
    public void testTanh() {
        assertEquals(0.0, ActivationFunctions.tanh(0), 0.001);
        assertEquals(0.761, ActivationFunctions.tanh(1), 0.001);
        assertEquals(-0.761, ActivationFunctions.tanh(-1), 0.001);
        assertEquals(1.0, ActivationFunctions.tanh(100), 0.001);
        assertEquals(-1.0, ActivationFunctions.tanh(-100), 0.001);
        assertEquals(0.986, ActivationFunctions.tanh(2.5), 0.001);
    }
}
