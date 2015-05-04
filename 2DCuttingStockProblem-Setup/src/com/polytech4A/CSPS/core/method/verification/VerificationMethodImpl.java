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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Corinne Faire aussi en fonction de la hauteur
 */
public class VerificationMethodImpl implements IVerificationMethod {

    public enum ImageOrientation {

        ROTATION_0,
        ROTATION_90
    }

    public enum CutOrientation {

        CUT_HORIZONTAL,
        CUT_VERTICAL
    }
    /*
     * Liste de pattern au fur et à mesure du découpage du pattern initial. A
     * trier du plus petit au plus grand
     */
    private ArrayList<Bin> listBinHoriz;

    private ArrayList<Bin> listBinVerti;

    private int compteur;
    private long minArea;
    private long nbSteps;
    private long maxSteps;

	// TODO test
//	private ArrayList<Pattern> listBin;
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
        nbSteps = 0;
        minArea = java.lang.Long.MAX_VALUE;
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
        if (BinHoriz != null) {
            this.listBinHoriz.add(BinHoriz);
        }
    }

    public void addListBinVerti(Bin BinVerti) {
        if (BinVerti != null) {
            this.listBinVerti.add(BinVerti);
        }
    }

    public void removeListBinHoriz(int BinHorizId) {
        int index = 0;
        if (BinHorizId >= 0 && listBinHoriz != null) {
            for (int i = 0; i < listBinHoriz.size(); i++) {
                if (BinHorizId == listBinHoriz.get(i).getId()) {
                    index = i;
                    break;
                }
            }
            this.listBinHoriz.remove(index);
        }
    }
    

    public void removeListBinVerti(int BinVertiInt) {
        int index = 0;
        if (BinVertiInt >= 0 && listBinVerti != null) {
            for (int i = 0; i < listBinVerti.size(); i++) {
                if (BinVertiInt == listBinVerti.get(i).getId()) {
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

        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("VerificationMethodImpl starting...");
		// TODO Test
//		listBin = new ArrayList<Pattern>();

        for (int i = 0; i < solution.getPatterns().size(); i++) {
            System.out.println("Prossessing patern" + i);
            //Pattern p = this.getPlacedPattern(solution.getPatterns().get(i));
            Pattern p = this.getPlacedPatternRecursive(solution.getPatterns().get(i),0);
            System.out.println(solution.getPatterns().get(i));
            if (p != null) {
                newSolution.addPattern(p);
            }
              else { return null; }
             

            // TODO Test
//			if(listBinHoriz.size()>0)
//			for (int j = 0; j < listBin.size(); j++) {
//				ArrayList<Image> listimgTest = new ArrayList<Image>();
//				listimgTest.add(new Image(listBin.get(j).getSize(),1L));
//				listBin.get(j).setListImg(listimgTest);
//				newSolution.addPattern(listBin.get(j));
//				
//			}
//			break;

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
        listImg = (ArrayList<Image>) pattern.getListImg().clone();
        

        System.out.println("Pattern size " + pattern.getSize());
        System.out.println("Images list " + listImg.size());
        int ite = 0;
        for (Image immag : listImg) {
            if (immag.getAmount() > 0) {
                System.out.println("Image nb: " + ite + " size" + immag.getSize());
            }
            ite++;

        }

        ResetBin(newBin);
        
        System.out.println("listBinHoriz" + listBinHoriz.get(0).getArea());

        this.getImgOrderDesc();

        int i = 0;
        while (i < listImg.size()) {
            Long amount = listImg.get(i).getAmount();
            while (amount > 0) {
                if (processPlacement(i, true, false) == true) {
                } else { // change les bin
                    if (processPlacement(i, false, false) == false) {

						// Recommencer à placer en mettant les images
                        // verticalement
                        // ResetBin(newBin);
                        // if (processPlacement(i, true,true) == true) {
                        // } else { // change les bin
                        // if (processPlacement(i, false,true) == false) {
                        // // return null;
                        // }
                        // }
                    }
                }
                amount--;

                // TODO test
//				if(listBinHoriz.size()>0)
//				for (int j = 0; j < listBinHoriz.size(); j++) {
//					listBin.add(listBinHoriz.get(j));
//				}
            }
            i++;
        }

        // Fabrication du Pattern
        newBin.setListImg(listImg);

        return newBin;
    }

    /**
     * decoupagePattern() add et remove ceux avec img TODO place l'image en bas
     * à droite du pattern puis découpe pour guillotine => listPattern =
     * plusieurs pattern
     */
    public Pattern getPlacedPatternRecursive(Pattern pattern, int maxEssais) {
        
        nbSteps = 0;
        // Initialisation des variables pour placer un pattern
        Bin newBin = new Bin(pattern.getSize(), pattern.getAmount());
        listImg = (ArrayList<Image>) pattern.getListImg().clone();

        //System.out.println("Pattern size " + pattern.getSize());
        //System.out.println("Images list " + listImg.size());
        int ite = 0;
        
        int nbTotImage = 0;
        for (Image immag : listImg) {
            
            if( minArea > immag.getArea()){
                minArea = immag.getArea();
            }
            
            nbTotImage += immag.getAmount();
            
            if (immag.getAmount() > 0) {
                System.out.println("Image nb: " + ite + " size" + immag.getSize() + " amount" +  immag.getAmount());
            }
            ite++;

        }
        
        this.maxSteps = maxEssais*nbTotImage;
//        this.maxSteps = 1;

        ResetBin(newBin);

        this.getImgOrderDesc();

        //System.out.println("listBinHoriz" + listBinHoriz.get(0).getArea());
        ///////////////////// Recursive call ///////////////////////////
        ArrayList<Image> result = new ArrayList<Image>();
        if (!processPlacementRecursive(getNextImageIndex(0, -1, listImg), 0, listImg, listBinHoriz, 0, result)) {
            System.out.println("No pattern found");
            return null;
        }else{
            System.out.println("Succeed to match pattern");
        }

                ////////////////////////////////////////////////////////////////
//		int i = 0;
//		while (i < listImg.size()) {
//			Long amount = listImg.get(i).getAmount();
//			while (amount > 0) {
// 				if (processPlacement(i, true, false) == true) {
//				} else { // change les bin
//					if (processPlacement(i, false, false) == false) {
//
//						// Recommencer à placer en mettant les images
//						// verticalement
//						// ResetBin(newBin);
//						// if (processPlacement(i, true,true) == true) {
//						// } else { // change les bin
//						// if (processPlacement(i, false,true) == false) {
//						// // return null;
//						// }
//						// }
//					}
//				}
//				amount--;
//				
//				// TODO test
////				if(listBinHoriz.size()>0)
////				for (int j = 0; j < listBinHoriz.size(); j++) {
////					listBin.add(listBinHoriz.get(j));
////				}
//			}
//			i++;
//		}
//
//		// Fabrication du Pattern
//		newBin.setListImg(listImg);
        newBin.setListImg(result);

        return newBin;
    }

    private void ResetBin(Bin bin) {
        if (listBinHoriz.size() > 0) {
            listBinHoriz.clear();
        }
        listBinHoriz.add(bin);

        if (listBinVerti.size() > 0) {
            listBinVerti.clear();
        }
        try {
            listBinVerti.add((Bin) bin.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        compteur = 1;

        // Reset position des images
        for (int i = 0; i < listImg.size(); i++) {
            if (listImg.get(i).getPositions().size() > 0) {
                listImg.get(i).getPositions().clear();
            }
        }
    }

    private boolean processPlacement(int i, boolean isHoriz, boolean isRotate) {
        ListIterator<Bin> iterator;

        if (isHoriz == true) {
            iterator = ((ArrayList<Bin>) listBinHoriz.clone()).listIterator();
        } else {
            iterator = ((ArrayList<Bin>) listBinVerti.clone()).listIterator();
        }

        while (iterator.hasNext()) {
            Bin element = iterator.next();
//            if (i == 17) {
//                System.out.println("MicprocessPlacement17: " + element.toString());
//            }

            if (listImg.get(i).getArea() <= element.getArea()) {
//                if (i == 17) {
//                    System.out.println("MicprocessPlacement17 found pattern: " + element);
//                    //System.out.println("image size: " + listImg.get(i).getSize() + " bin size:" +  element.getSize());
//                }
                if (isRotate == false) {
                    if (placementImage(element, i) == true) {
                        this.mergeBin(element, isHoriz);

                        // this.getPatternsOrderAsc();
                        return true;
                    }
                } else {
                    if (placementImageTournee(element, i) == true) {
                        this.mergeBin(element, isHoriz);

                        // this.getPatternsOrderAsc();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int getNextImageIndex(int currentImageIndex, int currentImageCount, ArrayList<Image> images) {
        int i = currentImageIndex;
        int currentAmount = ++currentImageCount;
        while (i < images.size()) {
            Long amount = images.get(i).getAmount();
            if (amount > currentAmount) {
                return i;
            }
            currentAmount = 0;
            i++;
        }
        return -1;
    }
    
    public void deepCopyImageList(ArrayList<Image> imagecpy, ArrayList<Image> images){
        imagecpy.clear();
        for(Image p : images) {
            try {
                imagecpy.add(((Image) p.clone()));
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(VerificationMethodImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void deepCopyBinList( ArrayList<Bin> bincpy, ArrayList<Bin> listBin){
        bincpy.clear();
        for(Bin item: listBin) {
            try {
                bincpy.add( (Bin) item.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(VerificationMethodImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
    private boolean processPlacementRecursive(int idCurrentImage, int idCurrentImageCount, ArrayList<Image> images, ArrayList<Bin> listBin, int cpt, ArrayList<Image> imagesResult) {
        //ystem.out.println("processPlacementRecursive idCurrentImage" + idCurrentImage+ " idCurrentImageCount" + idCurrentImageCount + " cpt"+ cpt + " listBinsize" + listBin.size());
        
        //Stop recursion if timeout
        if(maxSteps > 0 && nbSteps++ > maxSteps){
            return false;
        }
        Image currentImage = images.get(idCurrentImage);

        ListIterator<Bin> iterator;
        iterator = ((ArrayList<Bin>) listBin.clone()).listIterator();

        //get next image coordinates
        int nextImageIndex = getNextImageIndex(idCurrentImage, idCurrentImageCount, images);
        int newImageCount = 0;
        if (nextImageIndex == idCurrentImage) {
            newImageCount = idCurrentImageCount + 1;
        } 
        

        while (iterator.hasNext()) {
            Bin element = iterator.next();
            //Boolean boll = currentImage.getArea() <= element.getArea();
            //System.out.println("Iterrate" + element + " sizeOk:" + boll );
            
            if (currentImage.getArea() <= element.getArea()) {
                //System.out.println("Iterrate" + element );
                
                //Check four nodes
                
                //Deep copy lists
                ArrayList<Image> imagecpy = new ArrayList<Image>();
                ArrayList<Bin> bincpy = new ArrayList<Bin>();
                
                deepCopyImageList(imagecpy,images);
                deepCopyBinList(bincpy, listBin);


                //Node: Horizontal cut, rotation 0 
                if (placementImage(element, idCurrentImage, imagecpy, cpt * 2, CutOrientation.CUT_HORIZONTAL, ImageOrientation.ROTATION_0, bincpy)) {
                    //Test if there are more images
                    if (nextImageIndex < 0) {
                        //Succeed, no more images, we copy the image list
                        //imagesResult = imagecpy;
                        imagesResult.clear(); 
                        imagesResult.addAll(imagecpy);
                        //System.out.println("SOLUTION FOUND" + imagecpy);
                        return true;
                    } else {
                        //Recurssive call
                        if (processPlacementRecursive(nextImageIndex, newImageCount, imagecpy, bincpy, cpt + 1, imagesResult)) {
                            return true;
                        } else {
                            //Reset lists for next try
                            deepCopyImageList(imagecpy,images);
                            deepCopyBinList(bincpy, listBin);
                        }
                    }
                }

                //Node: Vertical cut, rotation 0 
                if (placementImage(element, idCurrentImage, imagecpy, cpt * 2, CutOrientation.CUT_VERTICAL, ImageOrientation.ROTATION_0, bincpy)) {
                    //Test if there are more images
                    if (nextImageIndex < 0) {
                        //Succeed, no more images, we copy the image list
                        //imagesResult = imagecpy;
                        imagesResult.clear(); 
                        imagesResult.addAll(imagecpy);
                        
                        return true;
                    } else {
                        //Recurssive call
                        if (processPlacementRecursive(nextImageIndex, newImageCount, imagecpy, bincpy, cpt + 1, imagesResult)) {
                            return true;
                        } else {
                            //Reset lists for next try
                            deepCopyImageList(imagecpy,images);
                            deepCopyBinList(bincpy, listBin);
                        }
                    }
                }

                //Node: Horizontal cut, rotation 90 
                if (placementImage(element, idCurrentImage, imagecpy, cpt * 2, CutOrientation.CUT_HORIZONTAL, ImageOrientation.ROTATION_90, bincpy)) {
                    //Test if there are more images
                    if (nextImageIndex < 0) {
                        //Succeed, no more images, we copy the image list
                        //imagesResult = imagecpy;
                        imagesResult.clear(); 
                        imagesResult.addAll(imagecpy);
                        return true;
                    } else {
                        //Recurssive call
                        if (processPlacementRecursive(nextImageIndex, newImageCount, imagecpy, bincpy, cpt + 1, imagesResult)) {
                            return true;
                        } else {
                            //Reset lists for next branch
                            deepCopyImageList(imagecpy,images);
                            deepCopyBinList(bincpy, listBin);
                        }
                    }
                }

                //Node: Horizontal cut, rotation 0 
                if (placementImage(element, idCurrentImage, imagecpy, cpt * 2, CutOrientation.CUT_VERTICAL, ImageOrientation.ROTATION_90, bincpy)) {
                    //Test if there are more images
                    if (nextImageIndex < 0) {
                        //Succeed, no more images, we copy the image list
                        //imagesResult = imagecpy;
                        imagesResult.clear(); 
                        imagesResult.addAll(imagecpy);
                        return true;
                    } else {
                        //Recurssive call
                        if (processPlacementRecursive(nextImageIndex, newImageCount, imagecpy, bincpy, cpt + 1, imagesResult)) {
                            return true;
                        }
                    }
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
            ArrayList<Bin> cloneListBinV = (ArrayList<Bin>) listBinVerti
                    .clone();
            for (int i = 0; i < cloneListBinV.size(); i++) {
                if (cloneListBinV.get(i).getClasse() == element.getClasse()) {
                    this.removeListBinVerti(cloneListBinV.get(i).getId());
                }
            }

            // Ajout du Bin Horizontal s'il existe
            ArrayList<Bin> cloneListBinH = (ArrayList<Bin>) listBinHoriz
                    .clone();
            for (int i = 0; i < cloneListBinH.size(); i++) {
                if (cloneListBinH.get(i).getId() != element.getId()) {
                    if (cloneListBinH.get(i).getClasse() == element.getClasse()) {
                        this.addListBinVerti(cloneListBinH.get(i));
                    }
                } else {
                    // Suppression dans le horizontal
                    this.removeListBinHoriz(element.getId());
                }
            }

			// assemblages des bins
            // faire pour liste puis break => recommencer (classement du plus grand au plus petit
            // faire pareil pour l'autre (verticalement)
//			ArrayList<Integer> binsUtiliser = new ArrayList<Integer>();
//			ArrayList<Bin> newListBinV = new ArrayList<Bin>();
//			for (int i = 0; i < cloneListBinV.size(); i++) {
//				for (int j = 0; j < cloneListBinV.size(); j++) {
//					if (cloneListBinV.get(i).getCoord().getX() == cloneListBinV
//							.get(j).getCoord().getX()
//							&& cloneListBinV.get(i).getSize().getX() == cloneListBinV.get(j).getSize().getX()) {
//						Bin assemblBin;
//						if(cloneListBinV.get(i).getCoord().getX() > cloneListBinV.get(j).getCoord().getX()){
//							assemblBin = new Bin(new Vector(cloneListBinV.get(i).getSize().getX()+cloneListBinV.get(j).getSize().getX(), cloneListBinV.get(i).getSize().getY()+cloneListBinV.get(j).getSize().getY()),cloneListBinV.get(j).getCoord());
//						}else{
//							assemblBin = new Bin(new Vector(cloneListBinV.get(i).getSize().getX()+cloneListBinV.get(j).getSize().getX(), cloneListBinV.get(i).getSize().getY()+cloneListBinV.get(j).getSize().getY()),cloneListBinV.get(i).getCoord());
//						}
//						binsUtiliser.add(cloneListBinV.get(i).getId());
//						binsUtiliser.add(cloneListBinV.get(j).getId());
//						newListBinV.add(assemblBin);
//					}
//
//				}
//			}
        } else {
            // Suppression des Bins Verticaux
            ArrayList<Bin> cloneListBinH = (ArrayList<Bin>) listBinHoriz
                    .clone();
            for (int i = 0; i < cloneListBinH.size(); i++) {
                if (cloneListBinH.get(i).getClasse() == element.getClasse()) {
                    this.removeListBinHoriz(cloneListBinH.get(i).getId());
                }
            }

            // Ajout du Bin Horizontal s'il existe
            ArrayList<Bin> cloneListBinV = (ArrayList<Bin>) listBinVerti
                    .clone();
            for (int i = 0; i < cloneListBinH.size(); i++) {
                if (cloneListBinV.get(i).getId() != element.getId()) {
                    if (cloneListBinV.get(i).getClasse() == element.getClasse()) {
                        this.addListBinHoriz(cloneListBinV.get(i));
                    }
                } else {
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
    protected boolean placementImage(Bin p, int iImage, ArrayList<Image> listImg, int compteur, CutOrientation cutOrientation, ImageOrientation orientation, ArrayList<Bin> listBin) { // Bas Droite
        Position position = null;
        Bin bin1 = null, bin2 = null;
        //System.out.println("placementImage" + iImage );
        switch (orientation) {
            case ROTATION_0:
                if ((p.getSize().getX() >= listImg.get(iImage).getSize().getX() && (p
                        .getSize().getY() >= listImg.get(iImage).getSize().getY()))) {
                    position = new Position(p.getCoord().getX(), p.getCoord().getY());

                    switch (cutOrientation) {
                        case CUT_HORIZONTAL:

                            // Découpage horizontal
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
                            break;
                        case CUT_VERTICAL:
                            bin1 = new Bin(new Vector(p.getSize().getX()
                                    - listImg.get(iImage).getSize().getX(), listImg
                                    .get(iImage).getSize().getY()), new Vector(p.getCoord()
                                            .getX() + listImg.get(iImage).getSize().getX(), p
                                            .getCoord().getY()));
                            bin2 = new Bin(new Vector(p.getSize().getX(), p.getSize()
                                    .getY() - listImg.get(iImage).getSize().getY()),
                                    new Vector(p.getCoord().getX(), listImg.get(iImage)
                                            .getSize().getY()
                                            + p.getCoord().getY()));
                            break;
                    }
                }
                break;
            case ROTATION_90:
                if ((p.getSize().getX() >= listImg.get(iImage).getSize().getY() && p
                        .getSize().getY() >= listImg.get(iImage).getSize().getX())) { // ROTATION
                    position = new Position(p.getCoord().getX(), p.getCoord().getY(),
                            true);

                    switch (cutOrientation) {
                        case CUT_HORIZONTAL:
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
                            break;
                        case CUT_VERTICAL:
                            bin1 = new Bin(new Vector(p.getSize().getX()
                                    - listImg.get(iImage).getSize().getY(), listImg
                                    .get(iImage).getSize().getX()), new Vector(p.getCoord()
                                            .getX() + listImg.get(iImage).getSize().getY(), p
                                            .getCoord().getY()));
                            bin2 = new Bin(new Vector(p.getSize().getX(), p.getSize()
                                    .getY() - listImg.get(iImage).getSize().getX()),
                                    new Vector(p.getCoord().getX(), listImg.get(iImage)
                                            .getSize().getX()
                                            + p.getCoord().getY()));
                            break;
                    }
                    break;
                }
            break;
        }
        
        //Return bins if founded
        if (position != null && bin1 != null && bin2 != null) {

            //Add the nex bin
            if(bin1.getArea() >= minArea){
                 bin1.setClasse(p.getClasse() + 1);
                 int id1 = compteur++;
                 bin1.setId(id1);
                 listBin.add(bin1);
            }
            
            if(bin2.getArea() >= minArea){
                 bin2.setClasse(p.getClasse() + 1);
                 int id2 = compteur++;
                 bin2.setId(id2);
                 listBin.add(bin2);
            }           
            
            //Remove previous bin
            int index = 0;
            for (int i = 0; i < listBin.size(); i++) {
                if (p.getId() == listBin.get(i).getId()) {
                    index = i;
                    break;
                }
            }
            listBin.remove(index);
            
            if(iImage == 10){
               int y = 0;
               y++;
            }
            listImg.get(iImage).getPositions().add(position);
            return true;
        }else{
            //System.err.println("NULL position or bin1 or bin2");
            return false;
        }
                
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
        if ((p.getSize().getX() >= listImg.get(iImage).getSize().getX() && (p
                .getSize().getY() >= listImg.get(iImage).getSize().getY()))) {
            position = new Position(p.getCoord().getX(), p.getCoord().getY());
        } else if ((p.getSize().getX() >= listImg.get(iImage).getSize().getY() && p
                .getSize().getY() >= listImg.get(iImage).getSize().getX())) { // ROTATION
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
     * Verif si hauteur & largeur rentre (pattern-image) (en placent en bas à
     * droite) Si oui, on place, sinon rotation image 90° et reteste
     *
     * @param p
     * @param iImage
     */
    protected boolean placementImageTournee(Bin p, int iImage) { // Bas Droite
        Position position = null; // image courrente

        // Verification si rentre dans le Pattern sinon on tourne l'image de
        if ((p.getSize().getX() > listImg.get(iImage).getSize().getY() && p
                .getSize().getY() > listImg.get(iImage).getSize().getX())) { // ROTATION
            position = new Position(p.getCoord().getX(), p.getCoord().getY(),
                    true);
        } else if ((p.getSize().getX() > listImg.get(iImage).getSize().getX() && (p
                .getSize().getY() > listImg.get(iImage).getSize().getY()))) {
            position = new Position(p.getCoord().getX(), p.getCoord().getY());
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
