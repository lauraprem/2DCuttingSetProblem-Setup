package com.polytech4A.CSPS.core.tests;

import com.polytech4A.CSPS.core.method.LinearResolutionMethod;
import com.polytech4A.CSPS.core.method.strategy.Genetic;
import com.polytech4A.CSPS.core.method.verification.VerificationMethodImpl;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.Resolution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;
import com.polytech4A.CSPS.core.resolution.util.file.ToPNG;

import java.util.ArrayList;

/**
 * @author Alexandre 09/04/2015
 */
public class TestsCorinne {
    public static void main(String[] args) {
        VerificationMethodImpl v = new VerificationMethodImpl();

        // Tests tests = new Tests();
        // Context context = tests.getContext(0);
        // Pattern pattern = tests.getSolution(0).getPatterns().get(0);
        // Image img = pattern.getListImg().get(0);
        // Solution solution = tests.getSolution(0);
        // img.toString();
        // LinearResolutionMethod linearResolutionMethod = new
        // LinearResolutionMethod(context);
        // ArrayList<Long> count =
        // linearResolutionMethod.getCount(tests.getSolution(0));
        // count.stream().forEach(c -> System.out.println(c));
        // LinearResolutionMethod.check(count, context, solution);
        // Resolution resolution = new Resolution(context);
        // resolution.setSolution(solution);

        // ArrayList<Pattern> listPattern = new ArrayList<Pattern>();
        // listPattern.add(v.getPlacedPattern(tests.getSolution(0).getPatterns().get(3)));
        // Solution s = new Solution(listPattern);
        // resolution.setSolution(s);

        // Solution s = v.getPlaced(tests.getSolution(0));
        // LinearResolutionMethod.check(count, context, s);
        // resolution.setSolution(s);
        // new ToPNG().save("test", resolution);

        Tests tests = new Tests();
        Context context = tests.getContext(0);
        Genetic genetic = new Genetic(context, new VerificationMethodImpl(),
                20, 10);

        Long temps1 = System.currentTimeMillis();
        genetic.run();
        Solution solution = genetic.getBestSolution();
        Long temps2 = System.currentTimeMillis();
        System.out.println("Temps d'éxécution : " + (temps2 - temps1) + " millisecondes");

        LinearResolutionMethod linearResolutionMethod = new LinearResolutionMethod(
                context);
        ArrayList<Long> count = linearResolutionMethod.getCount(solution);
        LinearResolutionMethod.check(count, context, solution);
        Resolution resolution = new Resolution(context);
        Solution tmp = v.getPlaced(solution);
        tmp.setFitness(genetic.getFitness(tmp));
        System.out.println("Fitness : " + tmp.getFitness());
        resolution.setSolution(tmp);
        new ToPNG().save("genetic", resolution);
    }
}
