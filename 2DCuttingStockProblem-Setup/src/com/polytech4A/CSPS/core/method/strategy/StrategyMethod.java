package com.polytech4A.CSPS.core.method.strategy;

import com.polytech4A.CSPS.core.method.LinearResolutionMethod;
import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

/**
 * Strategies de resolution de probleme
 *
 * @author Alexandre & Corinne & Laura
 */

public abstract class StrategyMethod implements Runnable {

    /**
     * @param solution : meilleure solution rencontrée
     * @
     * @return Solution
     */
    protected Solution bestSolution;
    /**
     * Context
     */
    private Context context;
    /**
     * Permet de verifie si on peut placer les images dans les patterns
     */
    private IVerificationMethod verificationMethod;
    /**
     * Permet de calculer la fitness d'une solution
     */
    private LinearResolutionMethod linearResolutionMethod;

    public StrategyMethod(Context context, IVerificationMethod verificationMethod) {
        this.context = context;
        this.verificationMethod = verificationMethod;
        linearResolutionMethod = new LinearResolutionMethod(context);
    }

    /**
     * @param context  : conditions initiale
     * @param solution : solution initiale
     * @return Solution
     * @
     */
    public Solution getSolution(Context context, Solution solution) {
        bestSolution = new Solution(solution);
        run();
        return bestSolution;
    }

    /**
     * @param context : conditions initiale
     * @return Solution
     */
    public Solution getSolution(Context context) {
        return getSolution(context, new Solution());
    }

    protected Context getContext() {
        return context;
    }

    protected IVerificationMethod getVerificationMethod() {
        return verificationMethod;
    }

    protected LinearResolutionMethod getLinearResolutionMethod() {
        return linearResolutionMethod;
    }

    public Solution getBestSolution() {
        return bestSolution;
    }
}
