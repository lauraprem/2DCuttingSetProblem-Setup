package com.polytech4A.CSPS.core.method.strategy.util;

import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.GeneticSolution;
import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;
import com.polytech4A.CSPS.core.util.PatternUtil;
import com.polytech4A.CSPS.core.util.SolutionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Alexandre
 *         29/04/2015
 */
public class GeneticUtil extends SolutionUtil {

    public static Solution getPackableCrossedSolution2(Context context, IVerificationMethod verificationMethod, Solution s1, Solution s2) {
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
            isPackable = verificationMethod.isPackable(solution);
            tentative++;
        } while (tentative <= maxTentatives && !isPackable);

        return !isPackable ? null : solution;
    }

    public static Solution getPackableCrossedSolution(Context context, IVerificationMethod verificationMethod, Solution s1, Solution s2) {
        Random random = new Random();
        Solution solution;
        Boolean isPackable;
        Integer maxPatterns = Math.max(s1.getPatterns().size(), s2.getPatterns().size());
        Integer nbImages = context.getImages().size();
        ArrayList<Image> base = context.getImages();
        solution = new Solution();
        List<Pattern> patterns = new ArrayList<>();
        List<Boolean> added = new ArrayList<>();

        for (int k = 0; k < maxPatterns; k++) {
            patterns.add(new Pattern(context.getPatternSize(), (ArrayList<Image>) base.clone()));
        }
        for (int j = 0; j < nbImages; j++) {
            added.add(Boolean.FALSE);
            for (int k = 0; k < maxPatterns; k++) {
                long amount1 = s1.getPatterns().size() <= k ? 0L : s1.getPatterns().get(k).getListImg().get(j).getAmount(), amount2 = s2.getPatterns().size() <= k ? 0L : s2
                        .getPatterns().get(k).getListImg().get(j).getAmount(), maxAmount = Math.max(amount1, amount2);
                long imageId = context.getImages().get(j).getId();
                for (int l = 0; l < maxAmount; l++) {
                    Boolean choice = random.nextBoolean();
                    Boolean choice1 = (amount1 >= l), choice2 = (amount2 >= l);
                    if ((choice && choice1 || !choice && choice2 || !choice1 && !choice2) && !PatternUtil.addImage(patterns.get(k), imageId, verificationMethod))
                        break;
                    else added.set(added.size() - 1, Boolean.TRUE);
                }
            }
        }
        for(Integer i = 0; i < added.size(); i++) {
            if(Boolean.FALSE.equals(added.get(i))) {
                for (Pattern p : patterns) {
                    if(PatternUtil.addImage(p, i.longValue(), verificationMethod)) {
                        added.set(i, Boolean.TRUE);
                        break;
                    }
                }
                if(Boolean.FALSE.equals(added.get(i))) {
                    ArrayList<Image> images = (ArrayList<Image>) base.clone();
                    Image image = (Image) images.get(i).clone();
                    image.setAmount(1L);
                    images.set(i, image);
                    patterns.add(new Pattern(context.getPatternSize(), images));
                }
            }
        }
        for (Pattern p : patterns)
            solution.addPattern(p);
        SolutionUtil.removeUselessPatterns(solution);
        if(!SolutionUtil.isSolvable(context, solution) && !verificationMethod.isPackable(solution))
            return null;

        return solution;
    }

    public static Solution getPackableMutatedSolution(Context context, IVerificationMethod verificationMethod, Solution solution) {
        Random r = new Random();
        int method = r.nextInt(6);
        Solution s = null;
        switch (method) {
            case 0:
                s = GeneticUtil.getPackableAddNeighbor(solution, verificationMethod);
                return s;
            case 1:
                s = GeneticUtil.getPackableCrossedNeighbor(solution, verificationMethod);
                return s;
            case 2:
                s = GeneticUtil.getPackableExchangeNeighbor(solution, verificationMethod);
                return s;
            case 3:
                s = GeneticUtil.addPackableRandomPattern(solution, context, verificationMethod);
                return s;
            case 4:
                s = GeneticUtil.getRandomPackableSolution2(context, verificationMethod);
                return s;
            default:
                s = GeneticUtil.getPackableSupressNeighbor(solution, verificationMethod);
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
