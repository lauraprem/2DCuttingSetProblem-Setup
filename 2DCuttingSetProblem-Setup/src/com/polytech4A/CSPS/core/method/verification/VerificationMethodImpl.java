package com.polytech4A.CSPS.core.method.verification;

import java.util.ArrayList;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;

/**
 * 
 * @author Corinne
 */
public class VerificationMethodImpl implements IVerificationMethod {

	/*
	 * Liste de pattern au fur et à mesure du découpage du pattern initial. A
	 * trier du plus petit au plus grand
	 */
	private ArrayList<Pattern> listPattern;

	/**
	 * Liste les images à placer sur un pattern de la plus grande taille à la
	 * plus petite
	 */
	private ArrayList<Image> listImg;

	public VerificationMethodImpl(ArrayList<Pattern> listPattern) {
		this.listPattern = new ArrayList<Pattern>();
		this.listImg = new ArrayList<Image>();
	}

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
			Pattern p = this.getPlacedPattern(solution.getPatterns().get(i));
			if (p != null) {
//				newSolution.addPattern(p);
			} else {
				return null;
			}
		}
		return null;//newSolution;
	}

	/**
	 * Donne le placement des images dans 1 Pattern si c'est possible, sinon
	 * renvoie 'null'. Pour information, le placement de l'image est fixé en bas
	 * à gauche & le placement de l'image ce fait verticalement (en priorité).
	 * 
	 * @param solution
	 * @return Pattern si c'est possible, 'null' sinon
	 */
	protected Pattern getPlacedPattern(Pattern pattern) {
		// Initialisation des variables pour placer un pattern
		Pattern newPattern = new Pattern(pattern.getSize(), pattern.getAmount());
		listPattern.add(newPattern);
		listImg = pattern.getListImg(); // .clone
		this.getImgOrderDesc();

		for (int i = 0; i < listImg.size(); i++) {
			int j = 0;
			while (j < listPattern.size()) {
				// if(listImg.get(i).getArea()< listPattern.get(j).getArea()){
				// TODO getAreaPattern()
				// TODO place l'image en bas à droite du pattern puis découpe
				// pour guillotine => listPattern = plusieurs pattern
				// }
//				this.getImgOrderDesc();
//				this.getPatternsOrderAsc();
				j++;
			}
		}
		return null;
	}

	/**
	 * Tri la liste des patterns par ordre croissant (de la plus petite taille à
	 * la plus grande)
	 */
	protected void getPatternsOrderAsc() {
		// listPattern;
	}

	/**
	 * Tri la liste des images par ordre décroissant (de la plus grande taille à
	 * la plus petite)
	 */
	protected void getImgOrderDesc() {
		// listImg;
	}
}
