package com.polytech4A.CSPS.core.method.verification;

import com.polytech4A.CSPS.core.model.*;

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
	private ArrayList<PatternWithCoord> listPattern;

	/**
	 * Liste les images à placer sur un pattern de la plus grande taille à la
	 * plus petite
	 */
	private ArrayList<Image> listImg;

	public VerificationMethodImpl() {
		this.listPattern = new ArrayList<PatternWithCoord>();
		this.listImg = new ArrayList<Image>();
	}

	public ArrayList<PatternWithCoord> getListPattern() {
		return listPattern;
	}

	public void setListPattern(ArrayList<PatternWithCoord> listPattern) {
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

	/**
	 * decoupagePattern() add et remove ceux avec img TODO place l'image en bas
	 * à droite du pattern puis découpe pour guillotine => listPattern =
	 * plusieurs pattern
	 */
	public Pattern getPlacedPattern(Pattern pattern) {

		// Initialisation des variables pour placer un pattern
		PatternWithCoord newPattern = new PatternWithCoord(pattern.getSize(),
				pattern.getAmount());
		listPattern.add(newPattern);
		listImg = (ArrayList<Image>) pattern.getListImg().clone();
		boolean isPlaced = false;

		// Reset position des images
		for (int i = 0; i < listImg.size(); i++) {
			if (listImg.get(i).getPositions().size() > 0) {
				listImg.get(i).getPositions().clear();
			}
		}
		
		this.getImgOrderDesc();

		int i = 0;
		while (i < listImg.size()) {
			Long amount = listImg.get(i).getAmount();
			while (amount > 0) {
				int j = 0;

				ListIterator<PatternWithCoord> iterator = listPattern
						.listIterator();
				while (iterator.hasNext()) {
					PatternWithCoord element = iterator.next();
					if (listImg.get(i).getArea() < element.getArea()) {
						if (placementImage(element, i) == true) {
//							this.decoupagePattern(element, i);
							listPattern.remove(element);
							this.getPatternsOrderAsc();
							isPlaced = true;
							break;
						}
					}
					j++;
				}

				// Si l'image est implaçable
				if (isPlaced == false && listImg.get(i).getAmount() > 0) {
					return null;
				}
				isPlaced = false;

				amount--;
			}
			i++;
		}

		// Fabrication du Patter
		newPattern.setListImg(listImg);

		return newPattern;
	}

	/**
	 * Verif si hauteur & largeur rentre (pattern-image) (en placent en bas à
	 * droite) Si oui, on place, sinon rotation image 90° et reteste
	 * 
	 * @param p
	 * @param iImage
	 */
	protected boolean placementImage(PatternWithCoord p, int iImage) { // Bas Droite
		Position position = null; // image courrente
		boolean isRotate = false;

		// Verification si rentre dans le Pattern sinon on tourne l'image de
		// 90°C, on place si possible (met coord image)
		if ((p.getSize().getX() > listImg.get(iImage).getSize().getX() && (p
				.getSize().getY() > listImg.get(iImage).getSize().getY()))
				&& isRotate != true) { // Hauteur Largeur
			position = new Position(p.getCoord().getX(), p.getCoord().getY());
		} else {
			isRotate = true;
		}

		if ((p.getSize().getX() > listImg.get(iImage).getSize().getY() && p
				.getSize().getY() > listImg.get(iImage).getSize().getX())
				&& isRotate == true) { // Hauteur Largeur
			position = new Position(p.getCoord().getX(), p.getCoord().getY(), true);
		} else {
			isRotate = false;
		}

		// Si l'image est placé on découpe le pattern
		if (position != null) {
			PatternWithCoord n1, n2;
			
			// Découpage horizontal
			if (!position.isRotated()) {
				
				n1 = new PatternWithCoord(new Vector(p.getSize().getX()
						- listImg.get(iImage).getSize().getX(), p.getSize().getY()), new Vector(p.getCoord().getX()+listImg.get(iImage).getSize().getX(),p.getCoord().getY()));
				n2 = new PatternWithCoord(new Vector(p.getSize().getX(), p
						.getSize().getY()
						- listImg.get(iImage).getSize().getY()), new Vector(p.getCoord().getX(),listImg.get(iImage).getSize().getY()+p.getCoord().getY()));
			} else {
				n1 = new PatternWithCoord(new Vector(p.getSize().getX()
						- listImg.get(iImage).getSize().getY(), p.getSize().getY()), new Vector(p.getCoord().getX()+listImg.get(iImage).getSize().getY(),p.getCoord().getY()));
				n2 = new PatternWithCoord(new Vector(p.getSize().getX(), p
						.getSize().getY()
						- listImg.get(iImage).getSize().getX()), new Vector(p.getCoord().getX(),listImg.get(iImage).getSize().getX()+p.getCoord().getY()));
			}
			this.addListPattern(n1);
			this.addListPattern(n2);

			listImg.get(iImage).getPositions().add(position);

			return true;
		}
		return false;
	}

	private void addListPattern(PatternWithCoord p) {
		if (p.getCoord() != null) {
			listPattern.add(p);
		}
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
