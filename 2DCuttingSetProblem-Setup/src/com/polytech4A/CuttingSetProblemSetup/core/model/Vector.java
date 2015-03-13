package com.polytech4A.CuttingSetProblemSetup.core.model;

/**
 * Ensemble de deux valeurs qui peuvent former des coordonn�es ou une taille en deux dimensions
 * @author Laura
 *
 */
public class Vector {

	/**
	 * Valeur de X
	 */
	private Long X;
	
	/**
	 * Valeur de Y
	 */
	private Long Y;

	public Vector(Long x, Long y) {
		X = x;
		Y = y;
	}

	public Long getX() {
		return X;
	}

	public void setX(Long x) {
		X = x;
	}

	public Long getY() {
		return Y;
	}

	public void setY(Long y) {
		Y = y;
	}
	
	
	
}
