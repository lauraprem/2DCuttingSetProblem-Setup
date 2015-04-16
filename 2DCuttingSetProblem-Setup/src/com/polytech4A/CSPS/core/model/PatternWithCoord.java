package com.polytech4A.CSPS.core.model;

public class PatternWithCoord extends Pattern implements Cloneable{
	
	/**
	 * Représente l'origine du pattern
	 */
	private Vector coord;
	public PatternWithCoord(Vector size, Long amount) {
		super(size, amount);
		coord = new Vector(0L,0L);
	}

	public PatternWithCoord(Vector size) {
		super(size);
		coord = new Vector(0L,0L);
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

	@Override
	public String toString() {
		return super.toString()+"Coord : "+coord+"\n";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		PatternWithCoord newPattern = (PatternWithCoord) super.clone();
		newPattern.setCoord((Vector)coord.clone());
		return super.clone();
	}

}
