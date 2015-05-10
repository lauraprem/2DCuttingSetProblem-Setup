package com.polytech4A.CSPS.core.resolution.util.context;

import java.util.ArrayList;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Vector;
import com.sun.istack.internal.NotNull;

/**
 * @version 1.0
 *          <p>
 *          Context Objet for Serialization in XML file.
 */
public class Context {

	/**
	 * Id of the context
	 */
	private String label;

	/**
	 * Cost of a Pattern in .
	 */
	private int patternCost;

	/**
	 * Cost of a sheet in .
	 */
	private int sheetCost;

	/**
	 * Size of a Pattern.
	 */
	private Vector patternSize;

	private Integer minPattern;

	private Integer maxPattern;

	private static Long milliStart = null;

	/**
	 * Boxes with size and amount to print, not the amount per pattern.
	 */
	@NotNull
	private ArrayList<Image> images;

	public Context(String label, int patternCost, int sheetCost, @NotNull ArrayList<Image> images, Vector patternSize) {
		this(label, patternCost, sheetCost, images, patternSize, 1L);
	}

	public Context(String label, int patternCost, int sheetCost, @NotNull ArrayList<Image> images, Vector patternSize, Long scale) {
		if (milliStart == null)
			milliStart = System.currentTimeMillis();
		this.label = label;
		this.patternCost = patternCost;
		this.sheetCost = sheetCost;
		this.images = images;
		this.patternSize = patternSize;
		maxPattern = images.size();
		Double imagesAera = 0.0;
		Long patternAera = patternSize.getX() * patternSize.getY();
		for (Image i : this.images) {
			imagesAera += i.getArea();
		}
		minPattern = ((Double) Math.ceil(imagesAera / patternAera)).intValue();
	}

	public synchronized ArrayList<Image> getCopyImages() {
		ArrayList<Image> listImgCopy = new ArrayList<Image>();
		for (int i = 0; i < images.size(); i++) {
			// try {
			listImgCopy.add(new Image(images.get(i).getId(), new Vector(images.get(i).getSize().getX(), images.get(i).getSize().getY()), images.get(i).getGoal()));
			// } catch (CloneNotSupportedException e) {
			// e.printStackTrace();
			// }
		}
		return listImgCopy;
	}

	public synchronized String getLabel() {
		return label;
	}

	public int getPatternCost() {
		return patternCost;
	}

	public int getSheetCost() {
		return sheetCost;
	}

	public synchronized ArrayList<Image> getImages() {
		return images;
	}

	public synchronized Vector getPatternSize() {
		return patternSize;
	}

	public synchronized Integer getMinPattern() {
		return minPattern;
	}

	public synchronized Integer getMaxPattern() {
		return maxPattern;
	}

	public static Long getMilliStart() {
		return milliStart;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Context{");
		sb.append("label='").append(label).append('\'');
		sb.append(", patternCost=").append(patternCost);
		sb.append(", sheetCost=").append(sheetCost);
		sb.append(", patternSize=").append(patternSize);
		sb.append(", minPattern=").append(minPattern);
		sb.append(", maxPattern=").append(maxPattern);
		sb.append(", images=[");
		for (Image i : images) {
			sb.append("Image{");
			sb.append("id=").append(i.getId());
			sb.append(", size=").append(i.getSize());
			sb.append(", goal=").append(i.getGoal());
			sb.append("}\n");
		}
		sb.append("]");
		sb.append("}\n");
		return sb.toString();
	}
}
