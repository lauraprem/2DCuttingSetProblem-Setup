package com.polytech4A.CSPS.core.model;

import java.util.List;

/**
 * Une solution au Cutting Problem
 * @author Laura
 *
 */
public class Solution {
	
	/**
	 * param�tre de conversion des float en long
	 */
	private int scale;
	
	/**
	 * liste des pattern qui forment la solution
	 */
	private List<Pattern> patterns;
	
	public Solution(int scale, List<Pattern> patterns) {
		super();
		this.scale = scale;
		this.patterns = patterns;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public List<Pattern> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<Pattern> patterns) {
		this.patterns = patterns;
	}
	
	
}