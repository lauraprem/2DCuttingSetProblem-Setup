package com.polytech4A.CSPS.core.model;

import java.util.List;

/**
 * Image à positionner dans un pattern
 * 
 * @author Laura
 *
 */
public class Image {
	/**
	 * nombre de fois que l'image est présente dans un pattern
	 */
	private Long amout;

	/**
	 * Permet de savoir si l'image est tourn�e
	 */
	private boolean rotated;

	/**
	 * Liste des positions pour chaque fois que l'image est pr�sente dans un
	 * pattern (amout)
	 */
	private List<Vector> positions;

	/**
	 * Taille de l'image
	 */
	private Vector size;

	/**
	 * nombre de fois que l'on veut imprimer l'image au total
	 */
	private Long goal;

	public Image(Long _amout, boolean _rotated, List<Vector> _positions,
			Vector _size, Long _goal) {
		amout = _amout;
		rotated = _rotated;
		positions = _positions;
		size = _size;
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

	public Long getGoal() {
		return goal;
	}

	public void setGoal(Long goal) {
		this.goal = goal;
	}

	public Long getArea(){
		return size.getX()*size.getY();
	}

	@Override
	public String toString() {
		String stringPosition;
		String detail = "IMAGE\n" +
				"Nombre d\'image dans le pattern : "+ amout+"\n"
				+"L\'image est tournée : "+rotated+"\n";
		
		stringPosition = "Liste des positions pour chaque fois que l\'image est présente : \n";
		for (int i = 0; i < positions.size(); i++) {
			stringPosition = stringPosition +positions.toString()+"\n";
		}
		
		detail = detail + stringPosition
				+"Taille de l\'image : "+size.toString()+"\n"
				+"Nombre d\'image à imprimer au total : "+goal+"\n";
		
		return detail;

	}
}
