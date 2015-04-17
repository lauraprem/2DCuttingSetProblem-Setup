package com.polytech4A.CSPS.core.model;

import static com.polytech4A.CSPS.core.util.Util.escToString;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Planche contenant des images
 *
 * @author Laura
 */
public class Pattern implements Comparable<Pattern> {

	/**
	 * taille du pattern
	 */
	private Vector size;

	/**
	 * Nombre de fois qu'il faut imprimer le pattern
	 */
	private Long amount;

	/**
	 * Les images présente dans le pattern
	 */
	private ArrayList<Image> listImg;
	
	public Pattern(Vector size) {
		super();
		this.size = size;
	}
	
	public Pattern(Vector size, Long amount) {
		super();
		this.size = size;
		this.amount = amount;
	}

	public Pattern(Vector size, ArrayList<Image> listImg) {
		this.size = size;
		this.listImg = listImg;
	}

	public Vector getSize() {
		return size;
	}

	public void setSize(Vector size) {
		this.size = size;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public ArrayList<Image> getListImg() {
		return listImg;
	}

	public void setListImg(ArrayList<Image> listImg) {
		this.listImg = listImg;
	}

	public Long getArea() {
		return size.getX() * size.getY();
	}

	@Override
	public String toString() {
		StringBuilder listImgString = new StringBuilder();
		String patternString = "PATTERN\n" + "size : " + escToString(size)
				+ "\n" + "amount : " + escToString(amount) + "\n"
				+ "listImg : \n";

		if (listImg != null) {
			for (Image img : listImg) {
				if (img != null && img.getAmount() != 0) {
					listImgString.append(escToString(img));
					listImgString.append('\n');
				}
			}
		} else {
			listImgString.append(escToString(listImg));
			listImgString.append('\n');
		}

		patternString = patternString + listImgString.toString();

		return patternString;
	}

	public void setPattern(Pattern pattern) {
		this.amount = pattern.getAmount();
		this.listImg = pattern.getListImg();
		this.size = pattern.getSize();
	}

	public Object clone() throws CloneNotSupportedException {
		Pattern cloned = new Pattern(this.size, this.amount);
		cloned.listImg = new ArrayList<Image>();
		for (Image image : this.listImg) {
			cloned.listImg.add((Image) image.clone());
		}
		return cloned;
	}

	@Override
	public int compareTo(Pattern o) {
		if (this.getArea() < o.getArea()) {
			return 0;
		}
		return 1;
	}

	public static Comparator<Pattern> PatternNameComparator = new Comparator<Pattern>() {
		public int compare(Pattern p1, Pattern p2) {

			Long PattArea1 = p1.getArea();
			Long PattArea2 = p2.getArea();

			// ascending order
//			return PattArea1.compareTo(PattArea2);

			// descending order
			 return PattArea2.compareTo(PattArea1);
		}

	};
}
