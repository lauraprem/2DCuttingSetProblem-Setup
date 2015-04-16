package com.polytech4A.CSPS.core.method.verification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import javax.lang.model.element.Element;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.PatternWithCoord;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.model.Vector;

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
	 * decoupagePattern() add et remove ceux avec img
	 * TODO place l'image en bas à droite du pattern puis découpe pour guillotine => listPattern = plusieurs pattern
	 */
	public Pattern getPlacedPattern(Pattern pattern) {
		
		// Initialisation des variables pour placer un pattern
		PatternWithCoord newPattern = new PatternWithCoord(pattern.getSize(), pattern.getAmount());
		listPattern.add(newPattern);
		listImg = (ArrayList<Image>) pattern.getListImg().clone();
		if(listImg.size()!=0) listImg.clear();
		
		
		this.getImgOrderDesc();
		
		int i=0;
		while (i < listImg.size()) {
			Long amount = listImg.get(i).getAmount();
				while(amount>0){
					int j = 0;
					
					ListIterator<PatternWithCoord> iterator = listPattern.listIterator();
			        while(iterator.hasNext()){
			        	PatternWithCoord element = iterator.next();
						if (listImg.get(i).getArea() < element.getArea()) {
							if(placementImage(element, i) == true){
								listPattern.remove(element);
//								this.decoupagePattern();
								break;
							}
							

						}
						this.getImgOrderDesc();
						this.getPatternsOrderAsc();
						j++;
					}
			        amount--;
				}
			i++;
		}
		return null;
	}

	/**
	 * Verif si hauteur & largeur rentre (pattern-image) (en placent en bas  à droite)
	 * Si oui, on place, sinon rotation image 90° et reteste
	 * @param p
	 * @param iImage
	 */
	protected boolean placementImage(PatternWithCoord p, int iImage) {

		// Verification si rentre dans le Pattern sinon on tourne l'image de 90°C, on place si possible (met coord image)
		if((p.getSize().getX() > listImg.get(iImage).getSize().getX() && (p.getSize().getY() > listImg.get(iImage).getSize().getY())) && listImg.get(iImage).isRotated()!=true){ // Hauteur Largeur
			listImg.get(iImage).getPositions().add(new Vector(p.getCoord().getX(), p.getCoord().getY()));
			if(p.getListImg() == null){
				p.setListImg(new ArrayList<Image>());
			}
			listImg.get(iImage).getPositions().add(new Vector(p.getCoord().getX(), p.getCoord().getY()));
			return true;
		}else{
			 listImg.get(iImage).setRotated(true);
		}
		
		if((p.getSize().getY() > listImg.get(iImage).getSize().getY() && p.getSize().getX() >  listImg.get(iImage).getSize().getX()) &&  listImg.get(iImage).isRotated()==true){ // Hauteur Largeur
			listImg.get(iImage).getPositions().add(new Vector(p.getCoord().getX(), p.getCoord().getY()));
			return true;
		}else{
			 listImg.get(iImage).setRotated(false);
		}
		return false;
	}

	protected ArrayList<PatternWithCoord> decoupagePattern(PatternWithCoord p, Image i) {
		PatternWithCoord n1, n2, n3;
		
		if (!i.isRotated()) {
			// Haut Gauche
			n1 = new PatternWithCoord(
					new Vector(p.getSize().getX() - i.getSize().getX(), i
							.getSize().getY()));
			n2 = new PatternWithCoord(new Vector(
					p.getSize().getX() - i.getSize().getX(), p.getSize().getX()
							- i.getSize().getY()));
			n3 = new PatternWithCoord(new Vector(p.getSize().getX(), p.getSize().getY()
					- i.getSize().getY()));
		} else {
			n1 = new PatternWithCoord(
					new Vector(p.getSize().getY() - i.getSize().getY(), i
							.getSize().getX()));
			n2 = new PatternWithCoord(new Vector(
					p.getSize().getY() - i.getSize().getY(), p.getSize().getY()
							- i.getSize().getX()));
			n3 = new PatternWithCoord(new Vector(p.getSize().getY(), p.getSize().getX()
					- i.getSize().getX()));
		}
		this.addListPattern(n1);
		this.addListPattern(n2);
		this.addListPattern(n3);
		
		return listPattern;
	}
	
	private void addListPattern(PatternWithCoord p){
		if(p.getCoord() != null){
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
