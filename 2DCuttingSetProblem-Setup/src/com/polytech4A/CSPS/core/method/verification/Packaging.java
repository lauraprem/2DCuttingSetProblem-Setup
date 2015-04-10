package com.polytech4A.CSPS.core.method.verification;

import java.util.ArrayList;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;

/**
 * 
 * @author Corinne
 * Placement image en bas à gauche & placement image verticalement (en priorité)

 */
public class Packaging implements IVerificationMethod{
	
	/*
	 * Liste de pattern au fur et à mesure du découpage du pattern initial.
	 * A trier du plus petit au plus grand
	 */
	private ArrayList<Pattern> listPattern;
	
	/**
	 * Liste les images à placer de la plus grande taille à la plus petite 
	 */
	private ArrayList<Image> listImg;
	
	public ArrayList<Pattern> getListPattern() {
		return listPattern;
	}
	
	public void setListPattern(ArrayList<Pattern> listPattern) {
		this.listPattern = listPattern;
	}

	public ArrayList<Image> getListImg() {
		return listImg;
	}
	
	public void setListImg(ArrayList<Image> listImg) {
		this.listImg = listImg;
	}
	
	@Override
	public Solution getPlaced(Solution solution) {
//		Solution newSolution = new Solution(solution.getScale());
		
		for (int i = 0; i < solution.getPatterns().size(); i++) {
//			newSolution.sthis.getPlacedPattern(solution.getPatterns().get(i));
		}
		return null;
	}
	
	/**
	 * Donne le placement des images dans 1 Pattern
	 * @param solution
	 * @return
	 */
	public Pattern getPlacedPattern(Pattern pattern) {
		return null;
	}

}
