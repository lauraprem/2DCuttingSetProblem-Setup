package com.polytech4A.CSPS.core.model;

/**
 * Planche contenant des images
 * @author Laura
 *
 */
public class Pattern {

	/**
	 * taille du pattern
	 */
	private Vector size;
	
	/**
	 * Nombre de fois qu'il faut imprimer le pattern
	 */
	private Long amout;

	public Pattern(Vector size, Long amout) {
		super();
		this.size = size;
		this.amout = amout;
	}

	public Vector getSize() {
		return size;
	}

	public void setSize(Vector size) {
		this.size = size;
	}

	public Long getAmout() {
		return amout;
	}

	public void setAmout(Long amout) {
		this.amout = amout;
	}

	// TODO : Ajouter l'ArrayList<Image> en attribut
	
	
}
