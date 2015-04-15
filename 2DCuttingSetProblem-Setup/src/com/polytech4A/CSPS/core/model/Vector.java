package com.polytech4A.CSPS.core.model;

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

	@Override
	public String toString() {
		return "VECTOR\n"
		+"X:"+X+" Y:"+Y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Vector vector = (Vector) o;

		if (X != null ? !X.equals(vector.X) : vector.X != null) return false;
		return !(Y != null ? !Y.equals(vector.Y) : vector.Y != null);
	}
}
