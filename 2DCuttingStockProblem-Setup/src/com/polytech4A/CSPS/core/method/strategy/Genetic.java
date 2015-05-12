package com.polytech4A.CSPS.core.method.strategy;

import com.polytech4A.CSPS.core.method.strategy.util.GeneticUtil;
import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.GenerationAction;
import com.polytech4A.CSPS.core.model.ParralelGenerationAction;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.model.couple.Couple;
import com.polytech4A.CSPS.core.model.couple.CoupleIterator;
import com.polytech4A.CSPS.core.resolution.Resolution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;
import com.polytech4A.CSPS.core.resolution.util.file.ToPNG;
import com.polytech4A.CSPS.core.util.Report;

import java.util.*;
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
    private Double mutationFrequency = 1 / 100.0;
    private final Double baseMutationFrequency;
    private Integer generationSinceLast = 0;
    private Integer previousGenerationIndex = -1;
    private List<Solution> previousGeneration;
    private List<Long>[] statistics;
    private List<Solution> generation = Collections.<Solution>synchronizedList(new ArrayList<>());

    // NOTE : Mutations multiples (P)


    public Genetic(Context context, IVerificationMethod verificationMethod,
                   Integer populationSize, Integer amountOfGeneration,
                   Double bestPartPercentage, Double mutationFrequency) {
        this(context, verificationMethod,
                populationSize, amountOfGeneration,
                bestPartPercentage, mutationFrequency,
                null, null, null, null, mutationFrequency);
    }

    public Genetic(Context context, IVerificationMethod verificationMethod,
                   Integer populationSize, Integer amountOfGeneration,
                   Double bestPartPercentage, Double mutationFrequency,
                   Solution bestSolution,
                   List<Solution> previousGeneration, Integer previousGenerationIndex, List<Long>[] statistics,
                   Double baseMutationFrequency) {
        super(context, verificationMethod);
        this.populationSize = populationSize;
        this.bestPartPercentage = bestPartPercentage;
        this.mutationFrequency = mutationFrequency;
        this.baseMutationFrequency = baseMutationFrequency;
        this.amountOfGeneration = amountOfGeneration;
        this.previousGeneration = previousGeneration;
        this.previousGenerationIndex = previousGenerationIndex;
        this.statistics = statistics;
        this.bestSolution = bestSolution;
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
        if (previousGenerationIndex == null) previousGenerationIndex = -1;
        if (statistics == null) {
            statistics = new ArrayList[4];
            statistics[0] = new ArrayList<>();
            statistics[1] = new ArrayList<>();
            statistics[2] = new ArrayList<>();
            statistics[3] = new ArrayList<>();
        }
        ParralelGenerationAction action;
        List<ParralelGenerationAction> parralelGenerationActions = Collections.synchronizedList(new ArrayList<ParralelGenerationAction>());
        if (previousGeneration == null) {
            previousGeneration = Collections.<Solution>synchronizedList(new ArrayList<>());
            while (generation.size() < populationSize) {
                generation.add(new Solution());
            }
            ParralelGenerationAction.setGenerated(0);
            for (int index = 0; index < generation.size(); index++) {
                parralelGenerationActions.add(new ParralelGenerationAction(getContext(), getVerificationMethod(), generation, index, GenerationAction.randomSolution));
            }
            parralelGenerationActions.parallelStream().forEach(parralelGenerationAction -> parralelGenerationAction.run());
        } else {
            previousGeneration.stream().forEach(solution -> generation.add(solution));
        }
        try {
            for (Integer i = (previousGenerationIndex != -1 ? previousGenerationIndex : 0); i < amountOfGeneration; i++) {
                generationSinceLast++;
                generation = generation.parallelStream().sorted((s1, s2) -> {
                    Long fit1 = s1.getFitness(), fit2 = s2.getFitness();
                    if (fit1 == -1L) {
                        s1.setFitness(getFitness(s1));
                        fit1 = s1.getFitness();
                    }
                    if (fit2 == -1L) {
                        s2.setFitness(getFitness(s2));
                        fit2 = s2.getFitness();
                    }
                    if (fit1 > fit2)
                        return 1;
                    if (fit2 > fit1)
                        return -1;
                    return 0;
                }).collect(Collectors.toList());
                if (generationSinceLast == 0) mutationFrequency = baseMutationFrequency;
                else if (generationSinceLast % 10 == 0 && mutationFrequency < 2 * baseMutationFrequency)
                    mutationFrequency += 0.001;

                LongSummaryStatistics stats = generation.parallelStream().mapToLong(value -> value.getFitness()).summaryStatistics();
                statistics[0].add(stats.getMin());
                statistics[1].add(((Double) stats.getAverage()).longValue());
                statistics[2].add(stats.getMax());
                statistics[3].add(bestSolution.getFitness());
                CoupleIterator coupleIterator = new CoupleIterator(generation);

                previousGeneration.clear();
                generation.stream().forEach(solution -> previousGeneration.add((Solution) solution.clone()));
                previousGenerationIndex = i;
                generation = generation.subList(0, (int) (generation.size() * bestPartPercentage));
                ParralelGenerationAction.setGenerated(0);
                parralelGenerationActions.clear();
                for (int index = generation.size(); index < populationSize; index++) {
                    parralelGenerationActions.add(new ParralelGenerationAction(getContext(), getVerificationMethod(),
                            generation, index, coupleIterator.next(), GenerationAction.crossedSolution));
                    generation.add(new Solution());
                }
                parralelGenerationActions.parallelStream().forEach(parralelGenerationAction2 -> parralelGenerationAction2.run());

                ParralelGenerationAction.setGenerated(0);
                parralelGenerationActions.clear();
                for (int index = (int) (generation.size() * bestPartPercentage); index < generation.size(); index++) {
                    //for (int index = 0; index < generation.size(); index++) {
                    if (random.nextDouble() <= mutationFrequency)
                        parralelGenerationActions.add(new ParralelGenerationAction(getContext(), getVerificationMethod(), generation, index, GenerationAction.randomMutation));
                }
                parralelGenerationActions.parallelStream().forEach(parralelGenerationAction -> parralelGenerationAction.run());

                System.out.println("Génération : " + i + ", Fitness : " + bestSolution.getFitness());
            }
            Resolution resolution = new Resolution(getContext());
            resolution.setSolution(bestSolution);
            String filepath = new ToPNG().save("version2.1", resolution);
            Report.makeStatisticReport(filepath, statistics[3], statistics[0], statistics[1], statistics[2]);
        } catch (Exception e) {
            //e.printStackTrace();
            previousGenerationIndex++;
            new Genetic(getContext(), getVerificationMethod(), populationSize, amountOfGeneration, bestPartPercentage, mutationFrequency,
                    bestSolution, previousGeneration, previousGenerationIndex, statistics, baseMutationFrequency).run();
        }
    }

    public Long getFitness(Solution solution) {
        Long fit = getLinearResolutionMethod().getFitnessAndRemoveUseless(solution, (long) getContext().getPatternCost(), (long) getContext().getSheetCost());
        solution.setFitness(fit);
        if (bestSolution == null || solution.getFitness() < bestSolution.getFitness()) {
            // if(getVerificationMethod().getPlaced(solution)==null){
            // System.out.println("Non packable");
            // }
            bestSolution = getVerificationMethod().getPlaced(solution);
            //Resolution resolution = new Resolution(getContext());
            //resolution.setSolution(bestSolution);
            //String filepath = new ToPNG().save("genetic-inter", resolution);
            //Report.makeStatisticReport(filepath, statistics[0], statistics[1], statistics[2]);
            // new ToPNG().save("solution-" + solution.getFitness(), new
            // Resolution(getContext(), solution));
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
