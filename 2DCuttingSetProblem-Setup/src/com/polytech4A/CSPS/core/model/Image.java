package com.polytech4A.CSPS.core.model;

import static com.polytech4A.CSPS.core.util.Util.escToString;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Image à positionner dans un pattern
 *
 * @author Laura
 */
public class Image implements Comparable<Image> {

	/**
	 * nombre de fois que l'image est présente dans un pattern
	 */
	private Long id = 0L;

	/**
	 * nombre de fois que l'image est présente dans un pattern
	 */
	private Long amount = 0L;

	/**
	 * Permet de savoir si l'image est tourn�e
	 */
	private boolean rotated = false;

	/**
	 * Liste des positions pour chaque fois que l'image est pr�sente dans un
	 * pattern (amount)
	 */
	private ArrayList<Vector> positions = new ArrayList<>();

	/**
	 * Taille de l'image
	 */
	private Vector size;

	/**
	 * nombre de fois que l'on veut imprimer l'image au total
	 */
	private Long goal = -1L;

	public Image(Long _id, Long _amount, boolean _rotated,
			ArrayList<Vector> _positions, Vector _size, Long _goal) {
		amount = _amount == null ? 0L : _amount;
		rotated = _rotated;
		positions = _positions == null ? new ArrayList<>() : _positions;
		size = _size;
		goal = _goal;
		id = _id == null ? 0L : _id;
	}

	public Image(Vector size, Long goal) {
		this(null, null, false, null, size, goal);
	}

	public Image(Long id, Vector size, Long goal) {
		this(id, null, false, null, size, goal);
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public boolean isRotated() {
		return rotated;
	}

	public void setRotated(boolean rotated) {
		this.rotated = rotated;
	}

	public ArrayList<Vector> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Vector> positions) {
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

	public Long getId() {
		return id;
	}

	public Long getArea() {
		return size.getX() * size.getY();
	}

	@Override
	public String toString() {
		StringBuilder stringPosition = new StringBuilder("positions : \n");
		String detail = "IMAGE " + id + "\n" + "amount : "
				+ escToString(amount) + "\n" + "rotated : " + rotated + "\n";

		if (positions == null) {
			stringPosition.append("<null>\n");
		} else if (positions.size() == 0) {
			stringPosition.append("<empty>\n");
		} else {
			for (Vector vec : positions) {
				stringPosition.append(escToString(vec));
				stringPosition.append('\n');
			}
		}

		detail = detail + stringPosition + "size : " + size.toString() + "\n"
				+ "goal : " + escToString(goal) + "\n";

		return detail;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Image image = (Image) o;

		if (!id.equals(image.id))
			return false;
		if (!size.equals(image.size))
			return false;
		return goal.equals(image.goal);

	}

	@Override
	public int compareTo(Image o) {
		if (this.getArea() < o.getArea()) {
			return 0;
		}
		return 1;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Image cloned = (Image) super.clone();
		cloned.setAmount(amount);
		cloned.setGoal(goal);
		cloned.setRotated(rotated);
		cloned.positions = new ArrayList<Vector>();
		for (Vector vector : positions) {
			cloned.positions.add((Vector) vector.clone());
		}
		return cloned;
	}

	public static Comparator<Image> ImageNameComparator = new Comparator<Image>() {

		public int compare(Image img1, Image img2) {

			Long ImgArea1 = img1.getArea();
			Long ImgArea2 = img2.getArea();

			// ascending order
//			return ImgArea1.compareTo(ImgArea2);

			// descending order
			 return ImgArea2.compareTo(ImgArea1);
		}

	};
}
