package com.polytech4A.CSPS.core.model;

import java.util.ArrayList;

public class Bin extends Pattern implements Cloneable{
	
	/**
	 * Représente l'origine du pattern
	 */
	private Vector coord;
	private int classe;
	private int id;
	public Bin(Vector size, Long amount) {
		super(size, amount);
		coord = new Vector(0L,0L);
		this.classe = 0;
		this.id=0;
	}

	public Bin(Vector size, Vector coord) {
		super(size);
		this.coord = new Vector(coord.getX(),coord.getY());
		classe = 0;
		this.id=0;
	}
	public Bin(Vector size, Vector coord, int classe, int id) {
		super(size);
		this.coord = coord;
		this.classe = classe;
		this.id = id;
	}

	public Vector getCoord() {
		return coord;
	}

	public void setCoord(Vector coord) {
		if(coord.getX()>0 && coord.getY()>0){
			this.coord = coord;
		}else{
			this.coord = null;
		}
	}
	
	public int getClasse() {
		return classe;
	}

	public void setClasse(int classe) {
		this.classe = classe;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return super.toString()+"Coord : "+coord+"\n"+"classe : "+classe+"\n id : "+id+"\n";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Bin cloned = new Bin(this.size, this.amount);
		cloned.listImg = new ArrayList<Image>();
		if(listImg != null){
			for (int i = 0; i < listImg.size(); i++) {
				cloned.listImg.add((Image) listImg.get(i).clone());
			}
		}
		cloned.coord = (Vector) this.coord.clone();
		cloned.classe=this.classe;
		cloned.id = this.id;
		return cloned;
	}
}
