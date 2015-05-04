package com.polytech4A.CSPS.core.tests;

import com.polytech4A.CSPS.core.method.LinearResolutionMethod;
import com.polytech4A.CSPS.core.method.strategy.Genetic;
import com.polytech4A.CSPS.core.method.verification.VerificationMethodImpl;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.Resolution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;
import com.polytech4A.CSPS.core.resolution.util.file.ToPNG;
import com.polytech4A.CSPS.core.util.Log;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * @author Alexandre
 *         09/04/2015
 */
public class TestsAlexandre {
    private static final Logger logger = Log.getLogger(TestsAlexandre.class);

    public static void main(String[] args) {
        /*
        Tests tests = new Tests();
        Context context = tests.getContext(0);
        Solution solution = tests.getSolution(0);
        solution = new VerificationMethodImpl().getPlaced(solution);
        LinearResolutionMethod linearResolutionMethod = new LinearResolutionMethod(context);
        ArrayList<Long> count = linearResolutionMethod.getCount(solution);
        count.stream().forEach(c -> System.out.println(c));
        solution = new VerificationMethodImpl().getPlaced(solution);
        LinearResolutionMethod.check(count, context, solution);
        Resolution resolution = new Resolution(context);
        resolution.setSolution(solution);
        logger.trace("test");
        new ToPNG().save("test", resolution);*/
        Tests tests = new Tests();
        Context context = tests.getContext(0);
        Genetic genetic = new Genetic(context, new VerificationMethodImpl(), 10, 10);
        genetic.run();
        Solution solution = genetic.getBestSolution();
        Resolution resolution = new Resolution(context);
        resolution.setSolution(solution);
        new ToPNG().save("genetic", resolution);
    }
}
