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
	private ArrayList<Image> listImg;

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

	public ArrayList<Image> getListImg() {
		return listImg;
	}

	public void setListImg(ArrayList<Image> listImg) {
		this.listImg = listImg;
	}
	
	@Override
	public String toString() {
		String listImgString="";
		String patternString="PATTERN\n"
				+"Taille du pattern : "+size.toString()+"\n"
				+"Nombre de fois qu\'il faut imprimer le pattern : "+amout+"\n"
				+"Les images présente dans le pattern : \n";
		
		for (int i = 0; i < listImg.size(); i++) {
			listImgString =  listImgString+listImg.get(i).toString()+"\n";
		}
		patternString = patternString+listImgString;
		
		return patternString;
	}
}
