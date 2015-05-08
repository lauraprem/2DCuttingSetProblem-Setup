package com.polytech4A.CSPS.core.method.strategy.util;

import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;
import com.polytech4A.CSPS.core.util.PatternUtil;
import com.polytech4A.CSPS.core.util.SolutionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Alexandre
 *         29/04/2015
 */
public class GeneticUtil extends SolutionUtil {

    public static Solution getViableCrossedSolution(Context context, IVerificationMethod verificationMethod, Solution s1, Solution s2) {
        Random random = new Random();
        Long time;
        //Long numberOfGenes = getNumberOfGenes(s1, s2);
        Long maxTentatives = 100L, tentative = 0L;
        Solution solution;
        Boolean isPackable;
        do {

            /*Boolean evenNumber = Boolean.TRUE;
            if (numberOfGenes % 2 != 0) {
                numberOfGenes--;
                evenNumber = Boolean.FALSE;
            }
            List<Boolean> first = new ArrayList<>();
            Long i;
            for (i = 0L; i < numberOfGenes / 2; i++) {
                first.add(Boolean.TRUE);
            }
            for (i = numberOfGenes / 2; i < numberOfGenes; i++) {
                first.add(Boolean.FALSE);
            }
            if (!evenNumber) {
                first.add(new Random().nextBoolean());
                numberOfGenes++;
            }

            if (first.size() != numberOfGenes) throw new RuntimeException();

            Collections.shuffle(first);
            */
            Long start = System.nanoTime();
            Integer maxPatterns = Math.max(s1.getPatterns().size(), s2.getPatterns().size());
            Integer nbImages = context.getImages().size();
            ArrayList<Image> base = context.getImages();
            solution = new Solution();
            List<Pattern> patterns = //Collections.synchronizedList(new ArrayList<>());
                                    new ArrayList<>();

            for (int k = 0; k < maxPatterns; k++) {
                patterns.add(new Pattern(context.getPatternSize(), (ArrayList<Image>) base.clone()));
            }
            for (int j = 0; j < nbImages; j++) {
                for (int k = 0; k < maxPatterns; k++) {
                    long
                            amount1 = s1.getPatterns().size() <= k ? 0L : s1.getPatterns().get(k).getListImg().get(j).getAmount(),
                            amount2 = s2.getPatterns().size() <= k ? 0L : s2.getPatterns().get(k).getListImg().get(j).getAmount(),
                            maxAmount = Math.max(amount1, amount2);
                    long imageId = context.getImages().get(j).getId();
                    for (int l = 0; l < maxAmount; l++) {
                        /*Boolean choice = first.get(first.size() - 1);
                        first.remove(first.size() - 1);*/
                        Boolean choice = random.nextBoolean();
                        Boolean choice1 = (amount1 >= l), choice2 = (amount2 >= l);
                        if (choice && choice1 || !choice && choice2)
                            PatternUtil.addImage(patterns.get(k), imageId, null);
                    }
                }
            }
            for(Pattern p : patterns) solution.addPattern(p);
            makeSolvable(context, solution);
            SolutionUtil.removeUselessPatterns(solution);
            tentative++;
            isPackable = isSolvable(context, solution) && verificationMethod.isViable(solution);
            time = System.nanoTime() - start;
        } while (tentative <= maxTentatives && !isPackable);
        return !isPackable ? null : solution;
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
                numberOfGenes += Math.max(s1.getPatterns().size() <= i ? 0L : s1.getPatterns().get(i).getListImg().get(j).getAmount(),
                        s2.getPatterns().size() <= i ? 0L : s2.getPatterns().get(i).getListImg().get(j).getAmount());
            }
        }
        return numberOfGenes;
    }
}
