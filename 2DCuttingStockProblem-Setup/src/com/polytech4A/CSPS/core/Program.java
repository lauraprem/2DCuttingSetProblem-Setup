package com.polytech4A.CSPS.core;

import com.polytech4A.CSPS.core.method.strategy.Genetic;
import com.polytech4A.CSPS.core.method.strategy.StrategyMethod;
import com.polytech4A.CSPS.core.method.verification.VerificationMethodImpl;
import com.polytech4A.CSPS.core.resolution.util.context.Context;
import com.polytech4A.CSPS.core.resolution.util.context.ContextLoaderUtils;

import java.io.IOException;

/**
 * @author Alexandre
 *         13/03/2015
 */
public class Program {
    // TODO Surcharger tous les toString()
    public static void main(String[] args) {
        String path, algo;
        if (args.length < 2) throw new RuntimeException();
        path = args[0];
        Context context;
        StrategyMethod strategyMethod;
        try {
            context = ContextLoaderUtils.loadContext(path);
            algo = args[1];
            if ("genetic".equals(algo)) {
                Integer
                        populationSize = Integer.parseInt(args[2]),
                        amountOfGeneration = Integer.parseInt(args[3]);
                strategyMethod = new Genetic(context, new VerificationMethodImpl(), populationSize, amountOfGeneration);
                strategyMethod.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}