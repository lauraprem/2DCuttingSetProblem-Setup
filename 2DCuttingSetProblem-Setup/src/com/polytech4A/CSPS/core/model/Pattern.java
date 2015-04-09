package com.polytech4A.CSPS.core.model;

import java.util.ArrayList;

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
	
	/**
	 * Les images présente dans le pattern
	 */
	private ArrayList<Image> listImage;

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

	public ArrayList<Image> getListImage() {
		return listImage;
	}

	public void setListImage(ArrayList<Image> listImage) {
		this.listImage = listImage;
	}
	
	
	
//	/**
//	 * taille du pattern
//	 */
//	private Vector size;
//	
//	/**
//	 * Nombre de fois qu'il faut imprimer le pattern
//	 */
//	private Long amout;
//	
//	/**
//	 * Les images présente dans le pattern
//	 */
//	private ArrayList<Image> listImage;
//	
//	@Override
//	public String toString() {
//		return Pattern;
//	}
}
