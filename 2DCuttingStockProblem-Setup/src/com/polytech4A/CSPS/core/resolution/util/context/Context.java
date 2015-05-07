package com.polytech4A.CSPS.core.resolution.util.context;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Vector;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

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

	/**
	 * Boxes with size and amount to print, not the amount per pattern.
	 */
	@NotNull private ArrayList<Image> images;

	public Context(String label, int patternCost, int sheetCost, @NotNull ArrayList<Image> images, Vector patternSize) {
		this(label, patternCost, sheetCost, images, patternSize, 1L);
	}

	public Context(String label, int patternCost, int sheetCost, @NotNull ArrayList<Image> images, Vector patternSize, Long scale) {
		this.label = label;
		this.patternCost = patternCost;
		this.sheetCost = sheetCost;
		this.images = images;
		this.patternSize = patternSize;
		maxPattern = images.size();
		Double imagesAera = 0.0;
		Long patternAera = patternSize.getX()*patternSize.getY();
		for(Image i : this.images) {
			imagesAera += i.getArea();
		}
		minPattern = ((Double) Math.ceil(imagesAera/patternAera)).intValue();
	}

	public String getLabel() {
		return label;
	}

	public int getPatternCost() {
		return patternCost;
	}

	public int getSheetCost() {
		return sheetCost;
	}

	public ArrayList<Image> getImages() {
		return images;
	}

	public Vector getPatternSize() {
		return patternSize;
	}

	public Integer getMinPattern() {
		return minPattern;
	}

	public Integer getMaxPattern() {
		return maxPattern;
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
		for(Image i : images) {
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