package com.polytech4A.CuttingSetProblemSetup.core.model;

import java.util.List;


/**
 * Image � positionner dans un pattern
 * @author Laura
 *
 */
public class Image {
	/**
	 * nombre de fois que l'image est pr�sente dans un pattern
	 */
	private Long amout;
	
	/**
	 * Permet de savoir si l'image est tourn�e
	 */
	private boolean rotated;
	
	/**
	 * Liste des positions pour chaque fois que l'image est pr�sente dans un pattern (amout)
	 */
	private List<Vector> positions;
	
	/**
	 * Taille de l'image
	 */
	private Vector size;
	
	public Image(Long _amout, boolean _rotated, List<Vector> _positions, Vector _size){
		amout=_amout;
		rotated=_rotated;
		positions=_positions;
		size=_size;
	}

	public Long getAmout() {
		return amout;
	}

	public void setAmout(Long amout) {
		this.amout = amout;
	}

	public boolean isRotated() {
		return rotated;
	}

	public void setRotated(boolean rotated) {
		this.rotated = rotated;
	}

	public List<Vector> getPositions() {
		return positions;
	}

	public void setPositions(List<Vector> positions) {
		this.positions = positions;
	}

	public Vector getSize() {
		return size;
	}

	public void setSize(Vector size) {
		this.size = size;
	}
	
	
	
}
