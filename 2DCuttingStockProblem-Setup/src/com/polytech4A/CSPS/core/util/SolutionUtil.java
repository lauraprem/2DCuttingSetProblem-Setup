package com.polytech4A.CSPS.core.util;

import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Alexandre
 *         29/04/2015
 */
public class SolutionUtil {

    public static Solution getRandomViableSolution(Context context, IVerificationMethod verificationMethod) {
        Random random = new Random();
        Integer amountOfPattern =
                random.nextInt(context.getMaxPattern() - context.getMinPattern())
                        + context.getMinPattern();
        Solution solution = new Solution();
        ArrayList<Pattern> patterns = new ArrayList<>();
        for (int i = 0; i < amountOfPattern; i++) {
            patterns.add(new Pattern(context.getPatternSize()));
        }
        List<Integer> patternIndexes = new ArrayList<>();
        for (Integer i = 0; i < patterns.size(); i++) patternIndexes.add(i);
        List<Long> imageIds = context.getImages().stream()
                .map(Image::getId)
                .collect(Collectors.toList());
        do {
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
            // TODO : Endthis
        } while (false);
        return solution;
    }

    public static void removeUselessPatterns(Solution solution) {
        List<Integer> indexes = new ArrayList<>();
        Boolean toDelete;
        for(int i = 0; i < solution.getPatterns().size(); i++) {
            toDelete = Boolean.TRUE;
            for(Image image : solution.getPatterns().get(i).getListImg()) {
                if(image.getAmount() !=0) {
                    toDelete = Boolean.FALSE;
                    break;
                }
            }
        }
        Collections.sort(indexes);
        for(int i = indexes.size() -1; i >= 0; i--) {
            solution.getPatterns().remove(indexes.get(i));
        }
    }
}
