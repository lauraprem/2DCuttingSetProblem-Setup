package com.polytech4A.CSPS.core.method.strategy.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
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

	public static Solution getViableCrossedSolution(Context context, IVerificationMethod verificationMethod, Solution s1, Solution s2) {
		Long numberOfGenes = getNumberOfGenes(s1, s2);
		Boolean evenNumber = Boolean.TRUE;
		if (numberOfGenes % 2 != 0) {
			numberOfGenes--;
			evenNumber = Boolean.FALSE;
		}
		List<Boolean> first = new ArrayList<>();
		if (!evenNumber)
			first.add(new Random().nextBoolean());
		long i;
		for (i = 0; i < numberOfGenes / 2; i++) {
			first.add(Boolean.TRUE);
		}
		for (i = numberOfGenes / 2; i < numberOfGenes; i++) {
			first.add(Boolean.FALSE);
		}
		Collections.shuffle(first);
		Integer maxPatterns = Math.max(s1.getPatterns().size(), s2.getPatterns().size());
		Integer nbImages = s1.getPatterns().get(0).getListImg().size();
		ArrayList<Image> base = (ArrayList<Image>) s1.getPatterns().get(0).getListImg().clone();
		base.forEach(image -> image.setAmount(0L));
		Solution solution;
		do {
			solution = new Solution();
			for (i = 0; i < maxPatterns; i++) {
				Pattern pattern = new Pattern(context.getPatternSize(), (ArrayList<Image>) base.clone());
				List<Image> images1;
				List<Image> images2;
				if (s1.getPatterns().size() <= i) {
					images1 = (ArrayList<Image>) base.clone();
					images2 = (ArrayList<Image>) s2.getPatterns().get((int) i).getListImg().clone();
				} else if (s2.getPatterns().size() <= i) {
					images1 = (ArrayList<Image>) s1.getPatterns().get((int) i).getListImg().clone();
					images2 = (ArrayList<Image>) base.clone();
				} else {
					images1 = (ArrayList<Image>) s1.getPatterns().get((int) i).getListImg().clone();
					images2 = (ArrayList<Image>) s2.getPatterns().get((int) i).getListImg().clone();
				}

				for (int j = 0; j < nbImages; j++) {
					long amount1 = images1.get(j).getAmount(), amount2 = images2.get(j).getAmount(), maxAmount = Math.max(amount1, amount2);
					long imageId = images1.get(j).getId();
					for (int k = 0; k < maxAmount; k++) {
						Boolean choice = first.get(first.size() - 1);
						first.remove(first.size() - 1);
						Boolean choice1 = amount1 >= k, choice2 = amount2 >= k;
						if (choice && choice1)
							PatternUtil.addImage(pattern, imageId, verificationMethod);
						else if (!choice && choice2)
							PatternUtil.addImage(pattern, imageId, verificationMethod);
					}
				}
				solution.addPattern(pattern);
				SolutionUtil.removeUselessPatterns(solution);
			}
		} while (isSolvable(context, solution) && !verificationMethod.isViable(solution));
		return solution;
	}

	public static Solution getViableMutatedSolution(Context context, IVerificationMethod verificationMethod, Solution solution) {
		Random r = new Random();
		int method = r.nextInt(4);
		switch (method) {
			case 0:
				return GeneticUtil.getViableAddNeighbor(solution, verificationMethod);
			case 1:
				return GeneticUtil.getViableCrossedNeighbor(solution, verificationMethod);
			case 2:
				return GeneticUtil.getViableExchangeNeighbor(solution, verificationMethod);
			default:
				return GeneticUtil.getViableSupressNeighbor(solution, verificationMethod);
		}
	}

	private static Long getNumberOfGenes(Solution s1, Solution s2) {
		Integer maxPatterns = Math.max(s1.getPatterns().size(), s2.getPatterns().size());
		Integer nbImages = s1.getPatterns().get(0).getListImg().size();
		Long numberOfGenes = 0L;
		for (int i = 0; i < maxPatterns; i++) {
			for (int j = 0; j < nbImages; j++) {
				if (s1.getPatterns().size() <= i)
					numberOfGenes += s2.getPatterns().get(i).getListImg().get(j).getAmount();
				else if (s2.getPatterns().size() <= i)
					numberOfGenes += s1.getPatterns().get(i).getListImg().get(j).getAmount();
				else
					numberOfGenes += Math.max(s1.getPatterns().get(i).getAmount(), s2.getPatterns().get(i).getListImg().get(j).getAmount());
			}
		}
		return numberOfGenes;
	}
}
