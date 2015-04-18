package com.polytech4A.CSPS.core.method.verification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import com.polytech4A.CSPS.core.model.Bin;
import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Position;
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
	private ArrayList<Bin> listBinHoriz;

	private ArrayList<Bin> listBinVerti;

	private int compteur;

	/**
	 * Liste les images à placer sur un pattern de la plus grande taille à la
	 * plus petite
	 */
	private ArrayList<Image> listImg;

	public VerificationMethodImpl() {
		this.listBinHoriz = new ArrayList<Bin>();
		this.listBinVerti = new ArrayList<Bin>();
		this.listImg = new ArrayList<Image>();
		compteur = 0;
	}

	public ArrayList<Bin> getListPattern() {
		return listBinHoriz;
	}

	public void setListPattern(ArrayList<Bin> listPattern) {
		this.listBinHoriz = listPattern;
	}

	public ArrayList<Image> getListImg() {
		return listImg;
	}

	public void setListImg(ArrayList<Image> listImg) {
		this.listImg = listImg;
	}

	public ArrayList<Bin> getListBinHoriz() {
		return listBinHoriz;
	}

	public void setListBinHoriz(ArrayList<Bin> listBinHoriz) {
		this.listBinHoriz = listBinHoriz;
	}

	public ArrayList<Bin> getListBinVerti() {
		return listBinVerti;
	}

	public void setListBinVerti(ArrayList<Bin> listBinVerti) {
		this.listBinVerti = listBinVerti;
	}

	public void addListBinHoriz(Bin BinHoriz) {
		if (BinHoriz != null)
			this.listBinHoriz.add(BinHoriz);
	}

	public void addListBinVerti(Bin BinVerti) {
		if (BinVerti != null)
			this.listBinVerti.add(BinVerti);
	}
	
	public void removeListBinHoriz(int BinHorizId) {
		int index =0;
		if (BinHorizId >=0 && listBinHoriz!=null){
			for (int i = 0; i < listBinHoriz.size(); i++) {
				if(BinHorizId==listBinHoriz.get(i).getId()){
					index = i;
					break;
				}
			}
			this.listBinHoriz.remove(index);
		}
	}

	public void removeListBinVerti(int BinVertiInt) {
		int index =0;
		if (BinVertiInt >=0 && listBinVerti!=null){
			for (int i = 0; i < listBinVerti.size(); i++) {
				if(BinVertiInt==listBinVerti.get(i).getId()){
					index = i;
					break;
				}
			}
			this.listBinVerti.remove(index);
		}
	}

	@Override
	public Solution getPlaced(Solution solution) {
		Solution newSolution = new Solution();

		for (int i = 0; i < solution.getPatterns().size(); i++) {
			Pattern p = this.getPlacedPattern(solution.getPatterns().get(i));
			System.out.println(solution.getPatterns().get(i));
			if (p != null) {
				newSolution.addPattern(p);
			}/* else {
				return null;
			}*/
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
		Bin newBin = new Bin(pattern.getSize(), pattern.getAmount());
		if (listBinHoriz.size() > 0) {
			listBinHoriz.clear();
		}
		listBinHoriz.add(newBin);
		
		if (listBinVerti.size() > 0) {
			listBinVerti.clear();
		}
		try {
			listBinVerti.add((Bin) newBin.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		listImg = (ArrayList<Image>) pattern.getListImg().clone();
		compteur = 1;

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
				if (processPlacement(i, true) == true) {
				} else { // change les bin
					if (processPlacement(i, false) == false) {
//						return null;
					}
				}
				amount--;
			}
			i++;
		}

		// Fabrication du Pattern
		newBin.setListImg(listImg);

		return newBin;
	}

	private boolean processPlacement(int i, boolean isHoriz) {
		ListIterator<Bin> iterator;

		if (isHoriz == true) {
			iterator = ((ArrayList<Bin>)listBinHoriz.clone()).listIterator();
		} else {
			iterator = ((ArrayList<Bin>)listBinVerti.clone()).listIterator();
		}

		while (iterator.hasNext()) {
			Bin element = iterator.next();
			if (listImg.get(i).getArea() < element.getArea()) {
				if (placementImage(element, i) == true) {
					this.mergeBin(element, isHoriz);
					
					// this.getPatternsOrderAsc();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Merge les Bins des deux liste de Bins (Horizontal et verticale)
	 * 
	 * @return
	 */
	private void mergeBin(Bin element, boolean isHoriz) {
		if (isHoriz == true) {
			// Suppression des Bins Verticaux
			ArrayList<Bin> cloneListBinV = (ArrayList<Bin>)listBinVerti.clone();
			for (int i = 0; i < cloneListBinV.size(); i++) {
				if (cloneListBinV.get(i).getClasse() == element.getClasse()) {
					this.removeListBinVerti(cloneListBinV.get(i).getId());
				}
			}

			// Ajout du Bin Horizontal s'il existe
			ArrayList<Bin> cloneListBinH = (ArrayList<Bin>)listBinHoriz.clone();
			for (int i = 0; i < cloneListBinH.size(); i++) {
				if (cloneListBinH.get(i).getId() != element.getId()) {
					if (cloneListBinH.get(i).getClasse() == element.getClasse())
					this.addListBinVerti(cloneListBinH.get(i));
				}else{
					// Suppression dans le horizontal
					this.removeListBinHoriz(element.getId());
				}
			}
		} else {
			// Suppression des Bins Verticaux
			ArrayList<Bin> cloneListBinH = (ArrayList<Bin>)listBinHoriz.clone();
			for (int i = 0; i < cloneListBinH.size(); i++) {
				if (cloneListBinH.get(i).getClasse() == element.getClasse()) {
					this.removeListBinHoriz(cloneListBinH.get(i).getId());
				}
			}

			// Ajout du Bin Horizontal s'il existe
			ArrayList<Bin> cloneListBinV = (ArrayList<Bin>)listBinVerti.clone();
			for (int i = 0; i < cloneListBinH.size(); i++) {
				if (cloneListBinV.get(i).getId() != element.getId()) {
					if (cloneListBinV.get(i).getClasse() == element.getClasse())
						this.addListBinHoriz(cloneListBinV.get(i));
				}else{
					// Suppression dans le verticale
					this.removeListBinVerti(cloneListBinV.get(i).getId());
				}
			}
		}
		getListBinHorizOrderAsc();
		getListBinVertiOrderAsc();
	}

	/**
	 * Verif si hauteur & largeur rentre (pattern-image) (en placent en bas à
	 * droite) Si oui, on place, sinon rotation image 90° et reteste
	 * 
	 * @param p
	 * @param iImage
	 */
	protected boolean placementImage(Bin p, int iImage) { // Bas Droite
		Position position = null; // image courrente

		// Verification si rentre dans le Pattern sinon on tourne l'image de
		// 90°C, on place si possible (met coord image)
		if ((p.getSize().getX() > listImg.get(iImage).getSize().getX() && (p
				.getSize().getY() > listImg.get(iImage).getSize().getY()))) {
			position = new Position(p.getCoord().getX(), p.getCoord().getY());
		} else if ((p.getSize().getX() > listImg.get(iImage).getSize().getY() && p
				.getSize().getY() > listImg.get(iImage).getSize().getX())) { // ROTATION
			position = new Position(p.getCoord().getX(), p.getCoord().getY(),
					true);
		}

		// Si l'image est placé on découpe le pattern
		if (position != null) {
			Bin bin1, bin2, bin3, bin4;

			// Découpage horizontal
			if (!position.isRotated()) {

				bin1 = new Bin(new Vector(p.getSize().getX()
						- listImg.get(iImage).getSize().getX(), p.getSize()
						.getY()), new Vector(p.getCoord().getX()
						+ listImg.get(iImage).getSize().getX(), p.getCoord()
						.getY()));
				bin2 = new Bin(new Vector(listImg.get(iImage).getSize().getX(),
						p.getSize().getY()
								- listImg.get(iImage).getSize().getY()),
						new Vector(p.getCoord().getX(), listImg.get(iImage)
								.getSize().getY()
								+ p.getCoord().getY()));
			} else { // Rotation
				bin1 = new Bin(new Vector(p.getSize().getX()
						- listImg.get(iImage).getSize().getY(), p.getSize()
						.getY()), new Vector(p.getCoord().getX()
						+ listImg.get(iImage).getSize().getY(), p.getCoord()
						.getY()));
				bin2 = new Bin(new Vector(listImg.get(iImage).getSize().getY(),
						p.getSize().getY()
								- listImg.get(iImage).getSize().getX()),
						new Vector(p.getCoord().getX(), listImg.get(iImage)
								.getSize().getX()
								+ p.getCoord().getY()));
			}
			bin1.setClasse(p.getClasse() + 1);
			bin2.setClasse(p.getClasse() + 1);
			int id1 = compteur++;
			bin1.setId(id1);
			int id2 = compteur++;
			bin2.setId(id2);
			this.addListBinHoriz(bin1);
			this.addListBinHoriz(bin2);

			// Découpage verticale
			if (!position.isRotated()) {

				bin3 = new Bin(new Vector(p.getSize().getX()
						- listImg.get(iImage).getSize().getX(), listImg
						.get(iImage).getSize().getY()), new Vector(p.getCoord()
						.getX() + listImg.get(iImage).getSize().getX(), p
						.getCoord().getY()));
				bin4 = new Bin(new Vector(p.getSize().getX(), p.getSize()
						.getY() - listImg.get(iImage).getSize().getY()),
						new Vector(p.getCoord().getX(), listImg.get(iImage)
								.getSize().getY()
								+ p.getCoord().getY()));
			} else { // Rotation
				bin3 = new Bin(new Vector(p.getSize().getX()
						- listImg.get(iImage).getSize().getY(), listImg
						.get(iImage).getSize().getX()), new Vector(p.getCoord()
						.getX() + listImg.get(iImage).getSize().getX(), p
						.getCoord().getY()));
				bin4 = new Bin(new Vector(p.getSize().getX(), p.getSize()
						.getY() - listImg.get(iImage).getSize().getX()),
						new Vector(p.getCoord().getX(), listImg.get(iImage)
								.getSize().getY()
								+ p.getCoord().getY()));
			}
			bin3.setClasse(p.getClasse() + 1);
			bin4.setClasse(p.getClasse() + 1);
			bin3.setId(id1);
			bin4.setId(id2);
			this.addListBinVerti(bin3);
			this.addListBinVerti(bin4);

			listImg.get(iImage).getPositions().add(position);

			return true;
		}
		return false;
	}

	/**
	 * Tri la liste des patterns par ordre croissant (de la plus petite taille à
	 * la plus grande)
	 */
	protected void getListBinHorizOrderAsc() {
		Collections.sort(listBinHoriz, Pattern.PatternNameComparator);
	}
	
	/**
	 * Tri la liste des patterns par ordre croissant (de la plus petite taille à
	 * la plus grande)
	 */
	protected void getListBinVertiOrderAsc() {
		Collections.sort(listBinVerti, Pattern.PatternNameComparator);
	}

	/**
	 * Tri la liste des images par ordre décroissant (de la plus grande taille à
	 * la plus petite)
	 */
	protected void getImgOrderDesc() {
		Collections.sort(listImg, Image.ImageNameComparator);
	}
}
