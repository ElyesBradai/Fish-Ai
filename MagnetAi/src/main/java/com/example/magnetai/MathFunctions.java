package com.example.magnetai;


public class MathFunctions {
    /**
     * A proton's charge in coulombs.
     */
    public static final double PROTON_CONSTANT= 1.602176634 * Math.pow(10,-19);
    /**
     * A proton's mass in kilograms.
     */
    public static final double PROTON_MASS = 1.6726219236910 * Math.pow(10,-27);
    /**
     * An electron's charge in coulombs.
     */
    public static final double ELECTRON_CONSTANT= -1.602176634 * Math.pow(10,-19);
    /**
     * An electron's mass in kilograms.
     */
    public static final double ELECTRON_MASS = 9.1093837015 * Math.pow(10,-31);

    /**
     *
     * @param charge
     * @param mass
     * @param magneticFieldStrength
     * @param velocity
     * @return the final velocity of the charge.
     */
    public static double[] calcFinalVelocity(double charge,double mass, double[] magneticFieldStrength, double [] velocity){
       double[] acceleration = calcAcceleration(charge,mass,velocity,magneticFieldStrength);
       return createVec(velocity[0] + acceleration[0],
                        velocity[1] + acceleration[1],
                        velocity[2] + acceleration[2]);
    }

    /**
     *
     * @param charge
     * @param mass
     * @param velocity
     * @param magneticFieldStrength
     * @return the current acceleration of the charge.
     */
    private static double[] calcAcceleration(double charge,double mass, double[] velocity, double[] magneticFieldStrength){
        double[] force = createVec(charge * crossProduct(velocity, magneticFieldStrength)[0],
                                   charge * crossProduct(velocity, magneticFieldStrength)[1],
                                   charge * crossProduct(velocity, magneticFieldStrength)[2]);
       return createVec(force[0] / mass,force[1] / mass,force[2] / mass);
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return a vector of the chosen components.
     */
    public static double[] createVec(double x,double y,double z){
        return new double[]{z,y,z};
    }

    /**
     *
     * @param vec1
     * @param vec2
     * @return the cross-product of vec1 and vec2 respectively.
     */
    public static double[] crossProduct(double[] vec1, double[] vec2){
        if(vec1 == null || vec2 == null){
            throw new IllegalArgumentException();
        }
       return createVec(      vec1[1] * vec2[2] - vec2[1] * vec1[2],
                        -1 * (vec1[0] * vec2[2] - vec2[0] * vec1[2]),
                              vec1[0] * vec2[1] - vec2[0] * vec1[1]);
    }

    /**
     *
     * @param a
     * @param b
     * @return a vector due to the matrix multiplication of both vectors.
     */
   public static double multiplyVectors(double[] a, double[] b) {

        if (a.length != b.length) {
            throw new IllegalArgumentException("Input vectors must have the same length");
        }

        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }

        return result;
    }



}
