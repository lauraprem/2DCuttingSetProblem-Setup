package com.polytech4A.CSPS.core.model;

import com.polytech4A.CSPS.core.method.strategy.util.GeneticUtil;
import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author Alexandre
 *         07/05/2015
 */
public class ParralelGenerationAction extends Thread {
    private final int index;
    private final List<Solution> generation;
    private final IVerificationMethod verificationMethod;
    private final Context context;
    private final Semaphore semaphore;
    private final GenerationAction generationAction;
    private static Integer generated;

    public ParralelGenerationAction(Context context, IVerificationMethod verificationMethod, List<Solution> generation, int index, Semaphore semaphore,
                                    GenerationAction generationAction) {
        this.index = index;
        this.generation = generation;
        this.context = context;
        this.verificationMethod = verificationMethod.cloneVerificationMethod();
        this.semaphore = semaphore;
        this.generationAction = generationAction;
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     */
    @Override
    public void run() {
        // TODO : Problème de synchronized pour verificationMethod
        if (GenerationAction.randomSolution.equals(generationAction)) makeRandom();
        else if (GenerationAction.randomMutation.equals(generationAction)) makeMutation();
        setGenerated(getGenerated() + 1);
        semaphore.release();
    }

    private void makeRandom() {
        generation.set(index, (Solution) GeneticUtil.getRandomViableSolution2(context, verificationMethod).clone());
        //if(getGenerated()*10000/generation.size()%100 == 0) System.out.println(getGenerated()*100/generation.size() + "% of population generated");

    }

    private void makeMutation() {
        generation.set(index, (Solution) GeneticUtil.getViableMutatedSolution(context, verificationMethod, (Solution) generation.get(index).clone()).clone());
        //if(getGenerated()*10000/generation.size()%100 == 0) System.out.println(getGenerated()*100/generation.size() + "% mutated generated");
    }

    public static Integer getGenerated() {
        return generated;
    }

    public static void setGenerated(Integer generated) {
        ParralelGenerationAction.generated = generated;
    }
}
