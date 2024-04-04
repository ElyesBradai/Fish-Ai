package com.example.magnetai;


public class MathFunctions {
    /**
     * A proton's mass in kilograms.
     */
    // public static final double PROTON_MASS = 1.6726219236910 * Math.pow(10, -27);
    public static final double PROTON_MASS = 0.000001;
    /**
     * A proton's charge in coulombs.
     */
    public static final double PROTON_CONSTANT = 0.00001;
    //public static final double PROTON_CONSTANT= 1.602176634 * Math.pow(10, -19);

    /**
     * An electron's charge in coulombs.
     */
    // public static final double ELECTRON_CONSTANT= -1.602176634 * Math.pow(10, -19);
    public static final double ELECTRON_CONSTANT = 1;
    // An electron's mass in kilograms.
    // public static final double ELECTRON_MASS = 9.1093837015 * Math.pow(10, -31);
    public static final double ELECTRON_MASS = 1;

    /**
     * @param charge
     * @param mass
     * @param magneticFieldStrength
     * @param velocity
     * @return the final velocity of the charge.
     */
    public static double[] calcFinalVelocity(double charge, double mass, double[] velocity, double[] magneticFieldStrength) {
        double[] acceleration = calcAcceleration(charge, mass, velocity, magneticFieldStrength);
        return createVec((velocity[0] + acceleration[0]),
                (velocity[1] + acceleration[1]),
                (velocity[2] + acceleration[2]));
    }

    /**
     * @param charge
     * @param mass
     * @param velocity
     * @param magneticFieldStrength
     * @return the current acceleration of the charge.
     */
    private static double[] calcAcceleration(double charge, double mass, double[] velocity, double[] magneticFieldStrength) {
//        double[] cross = crossProduct(velocity,magneticFieldStrength); // force per coulomb
//        System.out.println(cross[0]+","+cross[1]+","+cross[2]);
//        double ratio = charge/mass; // coulomb per kg
//        return createVec((cross[0]/ratio),(cross[1]/ratio),(cross[2]/ratio));
        double[] force = createVec(charge * crossProduct(velocity, magneticFieldStrength)[0],
                charge * crossProduct(velocity, magneticFieldStrength)[1],
                charge * crossProduct(velocity, magneticFieldStrength)[2]);
        return createVec(force[0] / mass, force[1] / mass, force[2] / mass);
    }

    /**
     * @param x
     * @param y
     * @param z
     * @return a vector of the chosen components.
     */
    public static double[] createVec(double x, double y, double z) {
        return new double[]{x, y, z};
    }

    /**
     * @param vec1
     * @param vec2
     * @return the cross-product of vec1 and vec2 respectively.
     */
    public static double[] crossProduct(double[] vec1, double[] vec2) {
        if (vec1 == null || vec2 == null) {
            throw new IllegalArgumentException();
        }
        return createVec(vec1[1] * vec2[2] - vec2[1] * vec1[2],
                -(vec1[0] * vec2[2] - vec2[0] * vec1[2]),
                vec1[0] * vec2[1] - vec2[0] * vec1[1]);
    }


    /**
     * @param vec1
     * @param vec2
     * @return vec1 vector due to the matrix multiplication of both vectors.
     */
    public static double multiplyVectors(double[] vec1, double[] vec2) {
        if (vec1.length != vec2.length) {
            throw new IllegalArgumentException("Input vectors must have the same length");
        }
        double result = 0;
        for (int i = 0; i < vec1.length; i++) {
            result += vec1[i] * vec2[i];
        }
        return result;
    }
}