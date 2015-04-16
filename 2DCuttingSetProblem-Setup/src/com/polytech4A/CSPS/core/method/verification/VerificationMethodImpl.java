package com.polytech4A.CSPS.core.method.verification;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.model.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

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

	public VerificationMethodImpl() {
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
		Solution newSolution = new Solution();

		for (int i = 0; i < solution.getPatterns().size(); i++) {
			Pattern p = this.getPlacedPattern(solution.getPatterns().get(i));
			if (p != null) {
				newSolution.addPattern(p);
			} else {
				return null;
			}
		}
		return newSolution;
	}

	public Pattern getPlacedPattern(Pattern pattern) {
		
		// Initialisation des variables pour placer un pattern
		Pattern newPattern = new Pattern(pattern.getSize(), pattern.getAmount());
		listPattern.add(newPattern);
		listImg = (ArrayList<Image>) pattern.getListImg().clone();
		this.getImgOrderDesc();
		
		for (int i = 0; i < listImg.size(); i++) {
			int j = 0;
			//listPattern
			ListIterator<Pattern> iterator = listPattern.listIterator();
	        while(iterator.hasNext()){
	            Pattern element = iterator.next();
				if (listImg.get(i).getArea() < element.getArea()) {
					element = placementImage(element, listImg.get(i));
//					 decoupagePattern() add et remove ceux avec img
					// TODO place l'image en bas à droite du pattern puis
					// découpe
					// pour guillotine => listPattern = plusieurs pattern
				}
				this.getImgOrderDesc();
				this.getPatternsOrderAsc();
				j++;
			}
		}
		return null;
	}

	protected Pattern placementImage(Pattern p, Image i) {
		// Verif si hauteur & largeur rentre (pattern-image) (en placent en bas
		// à droite)
		// => Si oui, on place, sinon rotation image 90° et reteste
		
		// Verification si rentre dans le Pattern sinon on tourne l'image de 90°C
		if((p.getSize().getX() > i.getSize().getX() && (p.getSize().getY() > i.getSize().getY())) && i.isRotated()!=true){ // Hauteur Largeur
//			i.setPositions(new Vector(p.get, y)); // Positionne l'image
		}else{
			i.setRotated(true);
		}
		
		if(p.getSize().getY() < i.getSize().getY() || (p.getSize().getX() < i.getSize().getX()) && i.isRotated()==true){ // Hauteur Largeur
			i.setRotated(false);
			return null;
		}
//		if
		return null;
	}

	protected ArrayList<Pattern> decoupagePattern(Pattern p, Image i) {
		ArrayList<Pattern> listPattern = new ArrayList<Pattern>();
		Pattern n1, n2, n3;

		if (!i.isRotated()) {
			// Haut Droite Bas
			n1 = new Pattern(
					new Vector(p.getSize().getX() - i.getSize().getX(), i
							.getSize().getY()));
			n2 = new Pattern(new Vector(
					p.getSize().getX() - i.getSize().getX(), p.getSize().getX()
							- i.getSize().getY()));
			n3 = new Pattern(new Vector(p.getSize().getX(), p.getSize().getY()
					- i.getSize().getY()));
		} else {
			n1 = new Pattern(
					new Vector(p.getSize().getY() - i.getSize().getY(), i
							.getSize().getX()));
			n2 = new Pattern(new Vector(
					p.getSize().getY() - i.getSize().getY(), p.getSize().getY()
							- i.getSize().getX()));
			n3 = new Pattern(new Vector(p.getSize().getY(), p.getSize().getX()
					- i.getSize().getX()));
		}
		listPattern.add(n1);
		listPattern.add(n2);
		listPattern.add(n3);

		return listPattern;
	}

	/**
	 * Tri la liste des patterns par ordre croissant (de la plus petite taille à
	 * la plus grande)
	 */
	protected void getPatternsOrderAsc() {
		Collections.sort(listPattern, Pattern.PatternNameComparator);
	}

	/**
	 * Tri la liste des images par ordre décroissant (de la plus grande taille à
	 * la plus petite)
	 */
	protected void getImgOrderDesc() {
		Collections.sort(listImg, Image.ImageNameComparator);
	}
}
