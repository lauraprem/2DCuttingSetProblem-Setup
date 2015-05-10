package com.polytech4A.CSPS.core.method.strategy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.GeneticSolution;
import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;
import com.polytech4A.CSPS.core.util.PatternUtil;
import com.polytech4A.CSPS.core.util.SolutionUtil;

/**
 * @author Alexandre
 *         29/04/2015
 */
public class GeneticUtil extends SolutionUtil {

	public static Solution getViableCrossedSolution2(Context context, IVerificationMethod verificationMethod, Solution s1, Solution s2) {
		Solution solution;
		Boolean isPackable;
		Long maxTentatives = 100L, tentative = 0L;
		Random random = new Random();
		GeneticSolution.setBasePattern(context);
		GeneticSolution gs1 = GeneticSolution.encode(s1), gs2 = GeneticSolution.encode(s2);
		GeneticSolution.normalizeCouple(gs1, gs2);
		GeneticSolution gs = new GeneticSolution();
		while (gs.size() < gs1.size())
			gs.addPattern();
		do {
			for (int i = 0; i < gs.size(); i++) {
				ArrayList<ArrayList<Boolean>> patterns = gs1.get(i);
				for (int j = 0; j < patterns.size(); j++) {
					ArrayList<Boolean> images = patterns.get(j);
					for (int k = 0; k < images.size(); k++)
						gs.get(i).get(j).add(random.nextBoolean() ? gs1.get(i).get(j).get(k) : gs2.get(i).get(j).get(k));
				}
			}
			solution = GeneticSolution.decode(gs);
			makePackable(context, solution, verificationMethod);
			removeUselessPatterns(solution);
			isPackable = verificationMethod.isViable(solution);
			tentative++;
		} while (tentative <= maxTentatives && !isPackable);

		return !isPackable ? null : solution;
	}

	public static Solution getViableCrossedSolution(Context context, IVerificationMethod verificationMethod, Solution s1, Solution s2) {
		Random random = new Random();
		Long time;
		// Long numberOfGenes = getNumberOfGenes(s1, s2);
		Long maxTentatives = 100L, tentative = 0L;
		Solution solution;
		Boolean isPackable;
		do {

			/*
			 * Boolean evenNumber = Boolean.TRUE; if (numberOfGenes % 2 != 0) {
			 * numberOfGenes--; evenNumber = Boolean.FALSE; } List<Boolean>
			 * first = new ArrayList<>(); Long i; for (i = 0L; i < numberOfGenes
			 * / 2; i++) { first.add(Boolean.TRUE); } for (i = numberOfGenes /
			 * 2; i < numberOfGenes; i++) { first.add(Boolean.FALSE); } if
			 * (!evenNumber) { first.add(new Random().nextBoolean());
			 * numberOfGenes++; }
			 * 
			 * if (first.size() != numberOfGenes) throw new RuntimeException();
			 * 
			 * Collections.shuffle(first);
			 */
			Integer maxPatterns = Math.max(s1.getPatterns().size(), s2.getPatterns().size());
			Integer nbImages = context.getImages().size();
			ArrayList<Image> base = context.getImages();
			solution = new Solution();
			List<Pattern> patterns = // Collections.synchronizedList(new
										// ArrayList<>());
			new ArrayList<>();

			for (int k = 0; k < maxPatterns; k++) {
				patterns.add(new Pattern(context.getPatternSize(), (ArrayList<Image>) base.clone()));
			}
			for (int j = 0; j < nbImages; j++) {
				for (int k = 0; k < maxPatterns; k++) {
					long amount1 = s1.getPatterns().size() <= k ? 0L : s1.getPatterns().get(k).getListImg().get(j).getAmount(), amount2 = s2.getPatterns().size() <= k ? 0L : s2
							.getPatterns().get(k).getListImg().get(j).getAmount(), maxAmount = Math.max(amount1, amount2);
					long imageId = context.getImages().get(j).getId();
					for (int l = 0; l < maxAmount; l++) {
						/*
						 * Boolean choice = first.get(first.size() - 1);
						 * first.remove(first.size() - 1);
						 */
						Boolean choice = random.nextBoolean();
						Boolean choice1 = (amount1 >= l), choice2 = (amount2 >= l);
						if (choice && choice1 || !choice && choice2)
							PatternUtil.addImage(patterns.get(k), imageId, null);
					}
				}
			}
			for (Pattern p : patterns)
				solution.addPattern(p);
			makeSolvable(context, solution, verificationMethod);
			SolutionUtil.removeUselessPatterns(solution);
			tentative++;
			isPackable = verificationMethod.isViable(solution);
		} while (tentative <= maxTentatives && !(verificationMethod.getPlaced(solution) == null));

		// if(verificationMethod.getPlaced(solution) == null){
		makePackable(context, solution, verificationMethod);

		// if(verificationMethod.getPlaced(solution) == null){
		// System.out.println("Null");
		// }
		// }
		// if(verificationMethod.getPlaced(solution) == null){
		// System.out.println("Null");
		// }

		// TODO Test
		// Récupération id de la liste d'images qui doivent être présent
		int nbImage = context.getImages().size();
		ArrayList<Image> listIdImg = new ArrayList<Image>();
		for (int i = 0; i < nbImage; i++) {
			listIdImg.add(new Image(context.getImages().get(i).getId(), context.getImages().get(i).getSize(), context.getImages().get(i).getAmount()));
		}

		// Solution s = new Solution();
		// for(int k=0;k <11; k++){
		// Pattern p = new Pattern(new
		// Vector(context.getPatternSize().getX(),context.getPatternSize().getY()),listIdImg);
		// if(k==0){
		// p.addImg(7L);
		// p.addImg(16L);
		// p.addImg(19L);
		// }
		// if(k==1){
		// p.addImg(1L);
		// p.addImg(20L);
		// p.addImg(19L);
		// }
		// if(k==2){
		// p.addImg(0L);
		// p.addImg(10L);
		// p.addImg(16L);
		// p.addImg(28L);
		// }
		// if(k==3){
		// p.addImg(5L);
		// p.addImg(14L);
		// p.addImg(18L);
		// p.addImg(21L);
		// p.addImg(27L);
		// }
		// if(k==4){
		// p.addImg(1L);
		// p.addImg(10L);
		// p.addImg(14L);
		// p.addImg(22L);
		// p.addImg(24L);
		// }
		// if(k==5){
		// p.addImg(3L);
		// p.addImg(6L);
		// }
		// if(k==6){
		// p.addImg(0L);
		// p.addImg(9L);
		// p.addImg(26L);
		// }
		// if(k==7){
		// p.addImg(1L);
		// p.addImg(5L);
		// p.addImg(13L);
		// p.addImg(22L);
		// }
		// if(k==8){
		// p.addImg(10L);
		// p.addImg(12L);
		// p.addImg(14L);
		// p.addImg(16L);
		// p.addImg(18L);
		// p.addImg(29L);
		// }
		// if(k==9){
		// p.addImg(3L);
		// p.addImg(4L);
		// p.addImg(17L);
		// p.addImg(23L);
		// }
		// if(k==10){
		// p.addImg(5L);
		// p.addImg(8L);
		// p.addImg(9L);
		// p.addImg(14L);
		// }
		// if(k==11){
		// p.addImg(2L);
		// p.addImg(11L);
		// p.addImg(15L);
		// p.addImg(24L);
		// p.addImg(24L);
		// p.addImg(27L);
		// }
		// s.addPattern(p);
		// }
		// solution.setSolution(s);
		//
		// ----------------------

		if (!isSolvable(context, solution)) {
			System.out.println("Pas solvable cross !!");
		}
		return solution;
	}

	public static Solution getViableMutatedSolution(Context context, IVerificationMethod verificationMethod, Solution solution) {
		Random r = new Random();
		int method = r.nextInt(4);
		Solution s = null;
		switch (method) {
			case 0:
				s = GeneticUtil.getViableAddNeighbor(solution, verificationMethod);
				makePackable(context, s, verificationMethod);
				return s;
			case 1:
				s = GeneticUtil.getViableCrossedNeighbor(solution, verificationMethod);
				makePackable(context, s, verificationMethod);
				return s;
			case 2:
				s = GeneticUtil.getViableExchangeNeighbor(solution, verificationMethod);
				makePackable(context, s, verificationMethod);
				return s;
			default:
				s = GeneticUtil.getViableSupressNeighbor(solution, verificationMethod);
				makePackable(context, s, verificationMethod);
				return s;
		}
	}

	private static Long getNumberOfGenes(Solution s1, Solution s2) {
		Integer maxPatterns = Math.max(s1.getPatterns().size(), s2.getPatterns().size());
		Integer nbImages = s1.getPatterns().get(0).getListImg().size();
		Long numberOfGenes = 0L;
		for (int i = 0; i < maxPatterns; i++) {
			for (int j = 0; j < nbImages; j++) {
				numberOfGenes += Math.max(s1.getPatterns().size() <= i ? 0L : s1.getPatterns().get(i).getListImg().get(j).getAmount(), s2.getPatterns().size() <= i ? 0L : s2
						.getPatterns().get(i).getListImg().get(j).getAmount());
			}
		}
		return numberOfGenes;
	}
}
