package com.polytech4A.CSPS.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.model.Vector;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

/**
 * @author Alexandre
 *         29/04/2015
 */
public class SolutionUtil {

    public static Solution makeSolvable(Context context, Solution solution) {
        return makeSolvable(context, solution, null);
    }

    public static Solution makeSolvable(Context context, Solution solution, IVerificationMethod verificationMethod) {
        Solution s = new Solution();
        s.setSolution(solution);
        // Récupération id de la liste d'images qui doivent être présent
        int nbImage = context.getImages().size();
        ArrayList<Image> listIdImg = new ArrayList<Image>();
        for (int i = 0; i < nbImage; i++) {
            listIdImg.add(new Image(context.getImages().get(i).getId(), new Vector(context.getImages().get(i).getSize().getX(), context.getImages().get(i).getSize().getY()),
                    context.getImages().get(i).getAmount()));
        }

        // Récupèration les images manquantes
        Iterator itr = listIdImg.iterator();
        while (itr.hasNext()) {
            Image e = (Image) itr.next();
            for (int j = 0; j < s.getPatterns().size(); j++) {
                int k;
                for (k = 0; k < s.getPatterns().get(j).getListImg().size(); k++) {
                    if ((s.getPatterns().get(j).getListImg().get(k).getId() == e.getId()) && (s.getPatterns().get(j).getListImg().get(k).getAmount() > 0)) {
                        itr.remove();

                        break;
                    }
                }
                if (k < s.getPatterns().get(j).getListImg().size()) {
                    break;
                }
            }
        }
        if (listIdImg.size() == 0)
            return s;

        // Ajoute les images manquantes

        Iterator iter = listIdImg.iterator();

        while (iter.hasNext()) {
            Image e = (Image) iter.next();
            int i = 0;
            while (i < s.getPatterns().size()) {
                Pattern testPattern;
                testPattern = (Pattern) s.getPatterns().get(i).clone();
                testPattern.addImg(e);
                if (verificationMethod.getPlacedPatternRecursive(testPattern, 0) != null) {
                    s.getPatterns().get(i).addImg(e);
                    iter.remove();
                    break;
                } else {
                    i++;
                }
            }

            // création d'un nouveau pattern
            // System.out.println("i : "+i);
            if (i >= s.getPatterns().size()) {
                // ArrayList<Image> images = new ArrayList<Image>();
                // for (Image image : context.getImages()) {
                // try {
                // images.add((Image) image.clone());
                // } catch (CloneNotSupportedException e1) {
                // e1.printStackTrace();
                // }
                // }

                // Pattern pattern = new
                // Pattern(s.getPatterns().get(0).getSize(), images);
                // pattern.addImg(e);
                // if(verificationMethod.getPlacedPatternRecursive(pattern,0) ==
                // null){
                // System.out.println("NULL, c'est vraimment NULL");
                // }else{
                // System.out.println("SO good ! ;-)");
                // }
                Pattern pattern = new Pattern(new Vector(s.getPatterns().get(0).getSize().getX(), s.getPatterns().get(0).getSize().getY()), context.getCopyImages());
                pattern.addImg(e);
                if (verificationMethod.getPlacedPatternRecursive(pattern, 0) != null) {

                    s.addPattern(pattern);
                    iter.remove();
                }
                // s.addPattern(pattern);
                // iter.remove();
            }

        }

        // boolean bool = (verificationMethod.getPlaced(solution) !=null);
        // if((bool ==false) || (listIdImg.size() != 0)){
        // System.out.println("Null - Verif : "+bool+" lineare : "+listIdImg.size());
        // }else{
        // System.out.println("SO good ! ;-)");
        // }

//		if (!isSolvable(context, s)) {
//			System.out.println("makeSolvable non sovable !!");
//		}
//		if (verificationMethod.getPlaced(s) == null) {
//			System.out.println("makeSolvable non packable  !!");
//		}
        // if (listIdImg.size() == 0)
        // return true;

        return s;
    }

    public static Boolean makePackable(Context context, Solution solution, IVerificationMethod verificationMethod) {
    	
    	 Random random = new Random();
    	 
    	// On cherche les patterns non packable, s'il y en a on enlève une image au hasard jusqu'à que ça marche
    	ArrayList<Pattern> listPattern =  (ArrayList<Pattern>) solution.getPatterns().clone();
    	for (int i = 0; i <listPattern.size(); i++) {
    		
    		// Si le pattern n'est pas packable on enlève une image au hasard jusqu'à la rendre packable
    		if(verificationMethod.getPlacedPatternRecursive(solution.getPatterns().get(i), 0) == null){
    			ArrayList<Long> listIdImage = new ArrayList<Long>();
    			
    			// Récupération des images présente dans le pattern
    			for (int j = 0; j < solution.getPatterns().get(i).getListImg().size(); j++) {
    				if(solution.getPatterns().get(i).getListImg().get(j).getAmount()>0){
    					listIdImage.add(solution.getPatterns().get(i).getListImg().get(j).getId());
    				}
				}
    			
    			// Enlève les images jusqu'à ce que le pattern soit packable
    			ArrayList<Long> listIdImageEnleve = new ArrayList<Long>();
    			while(verificationMethod.getPlacedPatternRecursive(solution.getPatterns().get(i), 0) == null){
    				Long idImage = (long) random.nextInt(listIdImage.size()-1);
    				solution.getPatterns().get(i).deleteImg(idImage);
    				listIdImageEnleve.add(idImage);
    			}
    			
    			// On essaye d'insérer les images dans autant de nouveaux patterns qu'il faut
    			 ArrayList<Pattern> patterns = new ArrayList<Pattern>();
    			 Pattern pattern = new Pattern(new Vector(context.getPatternSize().getX(),context.getPatternSize().getY()), context.getCopyImages());
    			 Iterator iter = listIdImageEnleve.iterator();
    		        while (iter.hasNext()) {
    		        	 Long imageId = (Long) iter.next();
    		        	if (!PatternUtil.addImage(pattern, imageId, verificationMethod)) {
    		                patterns.add((Pattern) pattern.clone());
    		                pattern = new Pattern(context.getPatternSize(), context.getCopyImages());
    		                PatternUtil.addImage(pattern, imageId, verificationMethod);
    		                iter.remove();
    		            }
    		            if (listIdImageEnleve.size() == 0) {
    		                patterns.add((Pattern) pattern.clone());
    		            }
		            }    	    	
    		}
		}
    	solution.setSolution(makeSolvable(context, solution,verificationMethod));

//      solution.setSolution(getRandomViableSolution2(context, verificationMethod));
//		if (!isSolvable(context, solution)) {
//			System.out.println("makeSolvable non sovable !!");
//		}
//		if (verificationMethod.getPlaced(solution) == null) {
//			System.out.println("makeSolvable non packable  !!");
//		}
        // if(verificationMethod.getPlaced(solution) ==null)
        // System.out.println("Pas packable, mais enfin !!");
        return true;
    }

    public static Solution getRandomViableSolution(Context context, IVerificationMethod verificationMethod) {
        Random random = new Random();
        Integer amountOfPattern = random.nextInt(context.getMaxPattern() - context.getMinPattern()) + context.getMinPattern();
        Solution solution = new Solution();
        ArrayList<Pattern> patterns = new ArrayList<>();
        for (int i = 0; i < amountOfPattern; i++) {
            patterns.add(new Pattern(context.getPatternSize(), context.getImages()));
        }
        List<Integer> patternIndexes = new ArrayList<>();
        for (Integer i = 0; i < patterns.size(); i++)
            patternIndexes.add(i);
        List<Long> imageIds = context.getImages().stream().map(Image::getId).collect(Collectors.toList());
        Boolean suceed = Boolean.FALSE;
        Collections.shuffle(imageIds);
        for (Long imageId : imageIds) {
            do {
                Collections.shuffle(patternIndexes);
                for (Integer index : patternIndexes) {
                    if (PatternUtil.addImage(patterns.get(index), imageId, verificationMethod)) {
                        suceed = Boolean.TRUE;
                        break;
                    }
                }
                if (!suceed) {
                    patterns.add(new Pattern(context.getPatternSize()));
                    patternIndexes.add(patternIndexes.size());
                }
            } while (!suceed);
        }
        for (Integer index : patternIndexes) {
            /*
			 * for(int i = 0; i < 4; i++) {
			 * PatternUtil.addImage(patterns.get(index),
			 * imageIds.get(random.nextInt(imageIds.size())),
			 * verificationMethod); }
			 */
            Pattern p = patterns.get(index);
            while (PatternUtil.addImage(p, imageIds.get(random.nextInt(imageIds.size())), verificationMethod)) {
                patterns.set(index, p);
            }
        }
        solution.setPatterns(patterns);
        return solution;
    }

    public static Solution getRandomViableSolution2(Context context, IVerificationMethod verificationMethod) {
        int nbRandomImageMin = 1;
        int nbRandomImageMax = context.getImages().size();
        Solution s = new Solution();
        Random random = new Random();
        int nbImageRandom = 0;
        // int id;
        ArrayList<Image> images = new ArrayList<Image>();

        for (Image image : context.getImages()) {
            images.add((Image) image.clone());
        }
        // Generate random list of image
        nbImageRandom = random.nextInt(nbRandomImageMax - nbRandomImageMin + 1) + nbRandomImageMin;
        // for (int i = 0; i < nbImageRandom; i++) {
        // id = random.nextInt(context.getImages().size());
        // images.get(id).incrementAmoutByOne();
        // }
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        Pattern pattern = new Pattern(context.getPatternSize(), context.getCopyImages());
        int imageId = random.nextInt(images.size());

        // Add every image one time
        // for (Image image : context.getImages()) {
        // if (!PatternUtil.addImage(pattern, image.getId(),
        // verificationMethod)) {
        // patterns.add((Pattern) pattern.clone());
        // pattern = new Pattern(context.getPatternSize(),
        // context.getImages());
        // PatternUtil.addImage(pattern, images.get(imageId).getId(),
        // verificationMethod);
        // }
        // }

        // Add the random images, either until every is added or until we
        // reach the maximum number of patterns
        // for (; 0 != images.size() && patterns.size() <
        // context.getMaxPattern(); imageId = random.nextInt(images.size()))
        // {
        // if (!PatternUtil.addImage(pattern, images.get(imageId).getId(),
        // verificationMethod)) {
        // patterns.add((Pattern) pattern.clone());
        // pattern = new Pattern(context.getPatternSize(),
        // context.getImages());
        // PatternUtil.addImage(pattern, images.get(imageId).getId(),
        // verificationMethod);
        // images.get(imageId).decrementAmoutByOne();
        // if (images.get(imageId).getAmount() == 0) {
        // images.remove(imageId);
        // }
        // }
        // }

        // int i = 0;
        for (int i = 0; i < nbImageRandom; imageId = random.nextInt(images.size())) {
            if (!PatternUtil.addImage(pattern, images.get(imageId).getId(), verificationMethod)) {
                patterns.add((Pattern) pattern.clone());
                pattern = new Pattern(context.getPatternSize(), context.getCopyImages());
                PatternUtil.addImage(pattern, images.get(imageId).getId(), verificationMethod);
                i++;
            }
            if (i == nbImageRandom) {
                patterns.add((Pattern) pattern.clone());
            }
        }
        s.setPatterns(patterns);

//		if (verificationMethod.getPlaced(s) == null) {
//			System.out.println("makeSolvable non packable  !!");
//		}
        s.setSolution(makeSolvable(context, s, verificationMethod));
//		if (!isSolvable(context, s)) {
//			System.out.println("makeSolvable non sovable !!");
//		}
//		if (verificationMethod.getPlaced(s) == null) {
//			System.out.println("makeSolvable non packable  !!");
//		}
        // Solution tmp = verificationMethod.getPlaced(s);
        // if(tmp == null){
        // System.out.println("Solution Random : non packable \n");
        // }else{
        // System.out.println("Solution Random : packable \n");
        // }
        return s;
    }

    public static void removeUselessPatterns(Solution solution) {
        List<Integer> indexes = new ArrayList<>();
        Boolean toDelete;
        for (int i = 0; i < solution.getPatterns().size(); i++) {
            toDelete = Boolean.TRUE;
            for (Image image : solution.getPatterns().get(i).getListImg()) {
                if (image.getAmount() != 0) {
                    toDelete = Boolean.FALSE;
                    break;
                }
            }
            if (Boolean.TRUE.equals(toDelete))
                indexes.add(i);
        }
        Collections.sort(indexes);
        for (int i = indexes.size() - 1; i >= 0; i--) {
            solution.getPatterns().remove(indexes.get(i));
        }
    }

    /**
     * Supprime une image aléatoirement dans un pattern aléatoire de la solution. La solutino finale reste viable.
     *
     * @param solution
     * @param verificationMethod
     * @return solution avec l'image en moins
     */
    public static Solution getViableSupressNeighbor(Solution solution, IVerificationMethod verificationMethod) {
        Random r = new Random();
        Solution s = new Solution(solution);
        int patternIndex;
        int imageIndex;

        patternIndex = r.nextInt(s.getPatterns().size());
        imageIndex = r.nextInt(s.getPatterns().get(patternIndex).getListImg().size());
        while (s.getPatterns().get(patternIndex).getListImg().get(imageIndex).getAmount() == 0) {
            imageIndex = r.nextInt(s.getPatterns().get(patternIndex).getListImg().size());
        }
        PatternUtil.supressImage(s.getPatterns().get(patternIndex), s.getPatterns().get(patternIndex).getListImg().get(imageIndex).getId(), verificationMethod);
        return s;
    }

    public static Solution getViableAddNeighbor(Solution solution, IVerificationMethod verificationMethod) {
        Random r = new Random();
        Solution s = new Solution(solution);
        int patternIndex;
        int imageIndex;

        patternIndex = r.nextInt(s.getPatterns().size());
        imageIndex = r.nextInt(s.getPatterns().get(patternIndex).getListImg().size());
        PatternUtil.addImage(s.getPatterns().get(patternIndex), s.getPatterns().get(patternIndex).getListImg().get(imageIndex).getId(), verificationMethod);

        return s;
    }

    public static Solution getViableCrossedNeighbor(Solution solution, IVerificationMethod verificationMethod) {
        Random r = new Random();
        Solution s = new Solution(solution);
        int pattern1Index;
        int pattern2Index;
        int image1Index;
        int image2Index;
        pattern1Index = r.nextInt(s.getPatterns().size());
        image2Index = r.nextInt(s.getPatterns().get(pattern1Index).getListImg().size());
        while (s.getPatterns().get(pattern1Index).getListImg().get(image2Index).getAmount() == 0) {
            image2Index = r.nextInt(s.getPatterns().get(pattern1Index).getListImg().size());
        }
        pattern2Index = r.nextInt(s.getPatterns().size());
        image1Index = r.nextInt(s.getPatterns().get(pattern2Index).getListImg().size());
        while (s.getPatterns().get(pattern2Index).getListImg().get(image1Index).getAmount() == 0) {
            image1Index = r.nextInt(s.getPatterns().get(pattern2Index).getListImg().size());
        }

        PatternUtil.crossImage(s.getPatterns().get(pattern1Index), s.getPatterns().get(pattern2Index), s.getPatterns().get(pattern2Index).getListImg().get(image1Index).getId(), s
                .getPatterns().get(pattern1Index).getListImg().get(image2Index).getId(), verificationMethod);
        return s;

    }

    public static Solution getViableExchangeNeighbor(Solution solution, IVerificationMethod verificationMethod) {
        Random r = new Random();
        Solution s = new Solution(solution);
        int originPattern;
        int destinationPattern;
        int imageIndex;

        originPattern = r.nextInt(s.getPatterns().size());
        destinationPattern = r.nextInt(s.getPatterns().size());
        imageIndex = r.nextInt(s.getPatterns().get(originPattern).getListImg().size());
        while (s.getPatterns().get(originPattern).getListImg().get(imageIndex).getAmount() == 0) {
            imageIndex = r.nextInt(s.getPatterns().get(originPattern).getListImg().size());
        }

        PatternUtil.exchangeImage(s.getPatterns().get(originPattern), s.getPatterns().get(destinationPattern), s.getPatterns().get(originPattern).getListImg().get(imageIndex)
                .getId(), verificationMethod);
        return s;
    }

    public static Boolean isSolvable(Context context, Solution solution) {

        // Récupération id de la liste d'images qui doivent être présent
        int nbImage = context.getImages().size();
        ArrayList<Image> listIdImg = new ArrayList<Image>();
        for (int i = 0; i < nbImage; i++) {
            listIdImg.add(new Image(context.getImages().get(i).getId(), (Vector) (context.getImages().get(i).getSize().clone()), context.getImages().get(i).getAmount()));
        }

        Iterator itr = listIdImg.iterator();
        while (itr.hasNext()) {
            Image e = (Image) itr.next();
            for (int j = 0; j < solution.getPatterns().size(); j++) {
                int k;
                for (k = 0; k < solution.getPatterns().get(j).getListImg().size(); k++) {
                    if ((solution.getPatterns().get(j).getListImg().get(k).getId() == e.getId()) && (solution.getPatterns().get(j).getListImg().get(k).getAmount() > 0)) {
                        itr.remove();
                        break;
                    }
                }
                if (k < solution.getPatterns().get(j).getListImg().size()) {
                    break;
                }
            }
        }
        return (listIdImg.size() == 0);
    }
}