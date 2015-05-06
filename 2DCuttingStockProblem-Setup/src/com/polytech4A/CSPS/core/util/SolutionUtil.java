package com.polytech4A.CSPS.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

/**
 * @author Alexandre
 *         29/04/2015
 */
public class SolutionUtil {

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
		while (!PatternUtil.supressImage(s.getPatterns().get(patternIndex), s.getPatterns().get(patternIndex).getListImg().get(imageIndex).getId(), verificationMethod)) {
			patternIndex = r.nextInt(s.getPatterns().size());
			imageIndex = r.nextInt(s.getPatterns().get(patternIndex).getListImg().size());
			while (s.getPatterns().get(patternIndex).getListImg().get(imageIndex).getAmount() == 0) {
				imageIndex = r.nextInt(s.getPatterns().get(patternIndex).getListImg().size());
			}
		}
		return s;
	}

	public static Solution getViableAddNeighbor(Solution solution, IVerificationMethod verificationMethod) {
		Random r = new Random();
		Solution s = new Solution(solution);
		int patternIndex;
		int imageIndex;

		patternIndex = r.nextInt(s.getPatterns().size());
		imageIndex = r.nextInt(s.getPatterns().get(patternIndex).getListImg().size());
		while (!PatternUtil.addImage(s.getPatterns().get(patternIndex), s.getPatterns().get(patternIndex).getListImg().get(imageIndex).getId(), verificationMethod)) {
			patternIndex = r.nextInt(s.getPatterns().size());
			imageIndex = r.nextInt(s.getPatterns().get(patternIndex).getListImg().size());
		}

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

		while (!PatternUtil.crossImage(s.getPatterns().get(pattern1Index), s.getPatterns().get(pattern2Index), s.getPatterns().get(pattern2Index).getListImg().get(image1Index)
				.getId(), s.getPatterns().get(pattern1Index).getListImg().get(image2Index).getId(), verificationMethod)) {
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
		}
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

		while (!PatternUtil.exchangeImage(s.getPatterns().get(originPattern), s.getPatterns().get(destinationPattern),
				s.getPatterns().get(originPattern).getListImg().get(imageIndex).getId(), verificationMethod)) {
			originPattern = r.nextInt(s.getPatterns().size());
			destinationPattern = r.nextInt(s.getPatterns().size());
			imageIndex = r.nextInt(s.getPatterns().get(originPattern).getListImg().size());
			while (s.getPatterns().get(originPattern).getListImg().get(imageIndex).getAmount() == 0) {
				imageIndex = r.nextInt(s.getPatterns().get(originPattern).getListImg().size());
			}
		}

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
