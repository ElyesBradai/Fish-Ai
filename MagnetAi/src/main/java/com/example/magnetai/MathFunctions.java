package com.example.magnetai;

/**
 * class for tmost oif the Math Functions used in the application
 */
public class MathFunctions{
	/**
	 * A proton's mass in kilograms.
	 */
	// public static final double PROTON_MASS = 1.6726219236910 * Math.pow(10, -27);
	public static final double PROTON_MASS = 1;
	/**
	 * A proton's charge in coulombs.
	 */
	public static final double PROTON_CONSTANT = 1;
	//public static final double PROTON_CONSTANT= 1.602176634 * Math.pow(10, -19);
	
	/**
	 * An electron's charge in coulombs.
	 */
	// public static final double ELECTRON_CONSTANT= -1.602176634 * Math.pow(10, -19);
	public static final double ELECTRON_CONSTANT = -1;
	// An electron's mass in kilograms.
	// public static final double ELECTRON_MASS = 9.1093837015 * Math.pow(10, -31);
	public static final double ELECTRON_MASS = 1;
	
	/**
	 * calculates the final velocity for the charge.
	 * @param charge a charge
	 * @param mass the mass of the charge
	 * @param magneticFieldStrength the magnetic field strength array colliding with the charge
	 * @param velocity the velocity array of the charge
	 * @return the final velocity of the charge.
	 */
	public static double[] calcFinalVelocity(double charge, double mass, double[] velocity, double[] magneticFieldStrength){
		double[] acceleration = calcAcceleration(charge, mass, velocity, magneticFieldStrength);
		return createVec((velocity[0] + acceleration[0]),
				(velocity[1] + acceleration[1]),
				(velocity[2] + acceleration[2]));
	}
	
	/**
	 * calculates the accelaration for the charge.
	 * @param charge a charge
	 * @param mass the mass of the charge
	 * @param magneticFieldStrength the magnetic field strength array colliding with the charge
	 * @param velocity the velocity array of the charge
	 * @return the current acceleration of the charge.
	 */
	private static double[] calcAcceleration(double charge, double mass, double[] velocity, double[] magneticFieldStrength){
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
	 * creates a vector array of (x,y,x).
	 * @param x the x component
	 * @param y the y component
	 * @param z the z component
	 * @return a vector of the chosen components.
	 */
	public static double[] createVec(double x, double y, double z){
		return new double[]{x, y, z};
	}
	
	/**
	 * returns the cross product of the two arrays.
	 * @param vec1 a vector array
	 * @param vec2 a vector array
	 * @return the cross-product of vec1 and vec2 respectively.
	 */
	public static double[] crossProduct(double[] vec1, double[] vec2){
		if(vec1 == null || vec2 == null){
			throw new IllegalArgumentException();
		}
		return createVec(vec1[1] * vec2[2] - vec2[1] * vec1[2],
				- (vec1[0] * vec2[2] - vec2[0] * vec1[2]),
				vec1[0] * vec2[1] - vec2[0] * vec1[1]);
	}
	
	
	/**
	 * returns the product of the multiplication.
	 * @param vec1 a vector array
	 * @param vec2 a vector array
	 * @return vec1 vector due to the matrix multiplication of both vectors.
	 */
	public static double multiplyVectors(double[] vec1, double[] vec2){
		if(vec1.length != vec2.length){
			throw new IllegalArgumentException("Input vectors must have the same length");
		}
		double result = 0;
		for (int i = 0; i < vec1.length; i++){
			result += vec1[i] * vec2[i];
		}
		return result;
	}
}