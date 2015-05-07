package com.polytech4A.CSPS.core.util;

import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.Bin;
import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Alexandre
 *         29/04/2015
 */
public class SolutionUtil {

    public static Boolean makeSolvable(Context context, Solution solution) {
        return makeSolvable(context, solution, null);
    }

    public static Boolean makeSolvable(Context context, Solution solution, IVerificationMethod verificationMethod) {
         
    	// Récupération id de la liste d'images qui doivent être présent
    	int nbImage = context.getImages().size();
    	ArrayList<Image> listIdImg = new ArrayList<Image>();
    	for (int i = 0; i <nbImage; i++) {
    		listIdImg.add(new Image(context.getImages().get(i).getId(),context.getImages().get(i).getSize(),context.getImages().get(i).getAmount()));
    	}
         
    	Iterator itr = listIdImg.iterator(); 
    	while(itr.hasNext()) {
           Image e = (Image)itr.next();
            for (int j = 0; j < solution.getPatterns().size(); j++) {
            	int k;
            	for(k=0;k < solution.getPatterns().get(j).getListImg().size(); k++){
                    if((solution.getPatterns().get(j).getListImg().get(k).getId() == e.getId())
                    		&& (solution.getPatterns().get(j).getListImg().get(k).getAmount()>0)){
                    	itr.remove();
                    	break;
                    }
            	}
            	if(k < solution.getPatterns().get(j).getListImg().size()){
            		break;
            	}
            }
        }
    	if(listIdImg.size() == 0) return true;
    	
    	// Ajoute les images manquantes
    	int i=0;
    	Iterator iter = listIdImg.iterator();
    	while(iter.hasNext()) {
           Image e = (Image)iter.next();
	    	while(i < solution.getPatterns().size()) {
	    		Pattern testPattern;
				try {
					testPattern = (Pattern) solution.getPatterns().get(i).clone();
					testPattern.addImg(e);
					if(verificationMethod.getPlacedPattern(testPattern)!=null){
						solution.getPatterns().get(i).addImg(e);
						iter.remove();
						break;
					}else{
						i++;
					}
				} catch (CloneNotSupportedException ex) {
					ex.printStackTrace();
				}
	    	}
	    	
	    	// création d'un nouveau pattern
	    	if(i >= solution.getPatterns().size()){
	    		solution.addPattern(new Pattern(solution.getPatterns().get(0).getSize(),context.getImages()));
	    		iter.remove();
	    	}
    	}
    	if(listIdImg.size() == 0) return true;
    	
    	return false;
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
		Set<Long> ids = Collections.<Long> synchronizedSet(new HashSet<>());
		solution.getPatterns().forEach(p -> p.getListImg().forEach(i -> {
			if (i.getAmount() != 0L)
				ids.add(i.getId());
		}));
		return ids.size() < context.getImages().size();
	}
}