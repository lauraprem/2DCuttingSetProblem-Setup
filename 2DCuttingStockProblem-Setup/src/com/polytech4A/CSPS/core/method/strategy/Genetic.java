package com.polytech4A.CSPS.core.method.strategy;

import com.polytech4A.CSPS.core.method.strategy.util.GeneticUtil;
import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.model.couple.Couple;
import com.polytech4A.CSPS.core.model.couple.CoupleIterator;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Alexandre
 *         16/04/2015
 */
public class Genetic extends StrategyMethod {
    private Random random;
    private Integer populationSize;
    private Integer amountOfGeneration;
    private Double bestPartPercentage = 30.0 / 100.0;
    private Double mutationFrequency = 3.0 / 100.0;
    private List<Solution> generation =
            Collections.<Solution>synchronizedList(new ArrayList<>());

    // NOTE : Mutations multiples (P)


    public Genetic(Context context, IVerificationMethod verificationMethod,
                   Integer populationSize, Integer amountOfGeneration,
                   Double bestPartPercentage, Double mutationFrequency) {
        super(context, verificationMethod);
        this.populationSize = populationSize;
        this.bestPartPercentage = bestPartPercentage;
        this.mutationFrequency = mutationFrequency;
        this.amountOfGeneration = amountOfGeneration;
        random = new Random();
    }

    public Genetic(Context context, IVerificationMethod verificationMethod,
                   Integer populationSize, Integer amountOfGeneration,
                   Double bestPartPercentage) {
        this(context, verificationMethod,
                populationSize, amountOfGeneration,
                bestPartPercentage, 3.0 / 100.0);

    }

    public Genetic(Context context, IVerificationMethod verificationMethod,
                   Integer populationSize, Integer amountOfGeneration) {
        this(context, verificationMethod,
                populationSize, amountOfGeneration,
                30.0 / 100.0, 3.0 / 100.0);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (generation.size() < populationSize) {
            generation.add(GeneticUtil.getRandomViableSolution(getContext(), getVerificationMethod()));
        }
        for (Integer i = 0; i < amountOfGeneration; i++) {

            generation.parallelStream().forEach(s -> {
                if (s.getFitness() == -1L)
                    s.setFitness(getFitness(s));
            });
            generation = generation.stream()
                    .sorted((o1, o2) -> {
                        Long fit1 = o1.getFitness(), fit2 = o2.getFitness();
                        if (fit1 > fit2) return 1;
                        if (fit2 > fit1) return -1;
                        return 0;
                    })
                    .collect(Collectors.toList())
                    .subList(0, (int) (generation.size() * bestPartPercentage));
            CoupleIterator coupleIterator = new CoupleIterator(generation);
            Couple c;
            Solution s;
            while (generation.size() < populationSize) {
                if (!coupleIterator.hasNext()) coupleIterator.reset();
                c = coupleIterator.next();
                s = getViableCrossedSolution(c);
                if(s != null) {
                    if (random.nextDouble() % 100 <= mutationFrequency) s = getViableMutatedSolution(s);
                    generation.add(s);
                }
            }
            System.out.println("G�n�ration : " + i);
        }
    }

    private Long getFitness(Solution solution) {
        Long fit = getLinearResolutionMethod().getFitness(solution, (long) getContext().getPatternCost(), (long) getContext().getSheetCost());
        solution.setFitness(fit);
        if (bestSolution == null || bestSolution.getFitness() < solution.getFitness()) {
            bestSolution = solution;
        }
        return fit;
    }

    private Solution getViableMutatedSolution(Solution s) {
        return GeneticUtil.getViableMutatedSolution(getContext(), getVerificationMethod(), s);
    }

    private Solution getViableCrossedSolution(Couple c) {
        return GeneticUtil.getViableCrossedSolution(getContext(), getVerificationMethod(), c.getS1(), c.getS2());
    }


    /**
     * Prendre une population de N solutions al�atoires (valides)
     * Prendre les X% meilleures sont crois�es entre elles
     * de mani�re al�atoires avec un facteur de mutation avec une
     * fr�quence F (et une proportion P).
     * Des solutions viables sont produites de jusqu'� obtenir une
     * population de N solutions (les anciennes incluses) et on
     * recommence sur G g�n�rations.
     *
     */
}
