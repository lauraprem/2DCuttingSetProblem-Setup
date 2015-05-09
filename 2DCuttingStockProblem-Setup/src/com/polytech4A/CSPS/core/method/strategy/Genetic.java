package com.polytech4A.CSPS.core.method.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import com.polytech4A.CSPS.core.method.strategy.util.GeneticUtil;
import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.model.couple.Couple;
import com.polytech4A.CSPS.core.model.couple.CoupleIterator;
import com.polytech4A.CSPS.core.resolution.Resolution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;
import com.polytech4A.CSPS.core.resolution.util.file.ToPNG;
import com.polytech4A.CSPS.core.util.SolutionUtil;

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
            generation.add(new Solution());
        }
        Semaphore semaphore = new Semaphore(- populationSize + 1);
        for (int index = 0; index < generation.size(); index++) {
            //new RandomSolution(getContext(), getVerificationMethod(), generation, index, semaphore).start();
            generation.set(index, SolutionUtil.getRandomViableSolution2(getContext(), getVerificationMethod()));
            //generation.set(index, SolutionUtil.getRandomViableSolution2(getContext(), getVerificationMethod(), 0, 10));
            semaphore.release();
        }
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Integer i = 0; i < amountOfGeneration; i++) {
            generation = generation.parallelStream()
                    .sorted((s1, s2) -> {
                        Long fit1 = s1.getFitness(), fit2 = s2.getFitness();
                        if(fit1 == -1L) {
                            s1.setFitness(getFitness(s1));
                            fit1 = s1.getFitness();
                        }
                        if(fit2 == -1L) {
                            s2.setFitness(getFitness(s2));
                            fit2 = s2.getFitness();
                        }
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
                if (s != null) {
                    // TODO : Décommenter quand fonctionnel
                    //if (random.nextDouble() * 100 <= mutationFrequency) s = getViableMutatedSolution(s);
                    generation.add(s);
                }
            }
            System.out.println("Génération : " + i + ", Fitness : " + bestSolution.getFitness());
        }

    }
    public void fonctionne() {
    	
    	// Génération de la population de départ
        while (generation.size() < populationSize) {
            generation.add(GeneticUtil.getRandomViableSolution2(getContext(), getVerificationMethod()));
        }
        
        // Génération i
        for (Integer i = 0; i < amountOfGeneration; i++) {
        	
        	// Récupération des meilleurs solutions
            generation.forEach(s -> {
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
            
            // Croisement et mutation
            while (generation.size() < populationSize) {
                if (!coupleIterator.hasNext()) coupleIterator.reset();
                c = coupleIterator.next();
                s = getViableCrossedSolution(c);
                if(s != null) {
                    if (random.nextDouble() % 100 <= mutationFrequency) s = getViableMutatedSolution(s);
                    generation.add(s);
                }
            }
            
            System.out.println("Génération : " + i);
        }
    }

    private Long getFitness(Solution solution) {
        Long fit = getLinearResolutionMethod().getFitnessAndRemoveUseless(solution, (long) getContext().getPatternCost(), (long) getContext().getSheetCost());
        solution.setFitness(fit);
        if (bestSolution == null || solution.getFitness() < bestSolution.getFitness()) {
            bestSolution = solution;
            new ToPNG().save("solution-" + solution.getFitness(), new Resolution(getContext(), solution));
        }
        return fit;
    }

    private Solution getViableMutatedSolution(Solution s) {
        return GeneticUtil.getViableMutatedSolution(getContext(), getVerificationMethod(), s);
    }

    private Solution getViableCrossedSolution(Couple c) {
        return GeneticUtil.getViableCrossedSolution(getContext(), getVerificationMethod(), c.getS1(), c.getS2());
    }

    private Solution getViableCrossedSolution2(Couple c) {
        return GeneticUtil.getViableCrossedSolution2(getContext(), getVerificationMethod(), c.getS1(), c.getS2());
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
