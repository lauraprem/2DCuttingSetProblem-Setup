package com.polytech4A.CSPS.core.model;

import com.polytech4A.CSPS.core.resolution.util.context.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandre
 *         09/05/2015
 */
public class GeneticSolution extends ArrayList<ArrayList<ArrayList<Boolean>>> {
    private static ArrayList<ArrayList<Boolean>> basePattern = new ArrayList<>();
    private static Context context;

    public static GeneticSolution encode(Solution s) {
        GeneticSolution gs = new GeneticSolution();
        for (int i = 0; i < s.getPatterns().size(); i++) {
            gs.addPattern();
            Pattern pattern = s.getPatterns().get(i);
            for (int j = 0; j < pattern.getListImg().size(); j++) {
                Image image = pattern.getListImg().get(j);
                for (int k = 0; k < image.getAmount(); k++)
                    gs.get(i)
                            .get(j)
                            .add(Boolean.TRUE);
            }
        }
        return gs;
    }

    public static Solution decode(GeneticSolution gs) {
        final List<Boolean> booleans = new ArrayList<>();
        booleans.add(Boolean.FALSE);
        gs.parallelStream().forEach(p -> p.parallelStream().forEach(i -> i.removeAll(booleans)));
        Solution s = new Solution();
        for (int i = 0; i < gs.size(); i++) {
            ArrayList<ArrayList<Boolean>> patterns = gs.get(i);
            s.addPattern(new Pattern(context.getPatternSize(), context.getImages()));
            if (patterns.size() != 20)
                return null;
            for (int j = 0; j < patterns.size(); j++) {
                ArrayList<Boolean> images = patterns.get(j);
                s.getPatterns()
                        .get(i).getListImg()
                        .get(j).setAmount((long) images.size());
            }
        }
        return s;
    }

    public static void setBasePattern(Context context) {
        GeneticSolution.context = context;
        for (int i = 0; i < context.getImages().size(); i++) basePattern.add(new ArrayList<>());
    }

    public static void normalizeCouple(GeneticSolution gs1, GeneticSolution gs2) {
        while (gs1.size() < gs2.size()) gs1.addPattern();
        while (gs2.size() < gs1.size()) gs2.addPattern();
        int patternSize = gs1.size(), imageSize = context.getImages().size();
        for (int i = 0; i < patternSize; i++) {
            for (int j = 0; j < imageSize; j++) {
                while (gs1.get(i).get(j).size() < gs2.get(i).get(j).size()) gs1.get(i).get(j).add(Boolean.FALSE);
                while (gs2.get(i).get(j).size() < gs1.get(i).get(j).size()) gs2.get(i).get(j).add(Boolean.FALSE);
            }
        }
    }

    public void addPattern() {
        ArrayList<ArrayList<Boolean>> patterns = new ArrayList<>();
        basePattern.forEach(p -> patterns.add(new ArrayList<>()));
        add(patterns);
    }
}