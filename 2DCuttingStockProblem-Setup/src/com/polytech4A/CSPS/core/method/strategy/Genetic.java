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
import com.polytech4A.CSPS.core.util.Report;
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
	private List<Solution> generation = Collections.<Solution> synchronizedList(new ArrayList<>());

	// NOTE : Mutations multiples (P)

	public Genetic(Context context, IVerificationMethod verificationMethod, Integer populationSize, Integer amountOfGeneration, Double bestPartPercentage, Double mutationFrequency) {
		super(context, verificationMethod);
		this.populationSize = populationSize;
		this.bestPartPercentage = bestPartPercentage;
		this.mutationFrequency = mutationFrequency;
		this.amountOfGeneration = amountOfGeneration;
		random = new Random();
	}

	public Genetic(Context context, IVerificationMethod verificationMethod, Integer populationSize, Integer amountOfGeneration, Double bestPartPercentage) {
		this(context, verificationMethod, populationSize, amountOfGeneration, bestPartPercentage, 3.0 / 100.0);

	}

	public Genetic(Context context, IVerificationMethod verificationMethod, Integer populationSize, Integer amountOfGeneration) {
		this(context, verificationMethod, populationSize, amountOfGeneration, 30.0 / 100.0, 3.0 / 100.0);
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
		List<Long>[] statistics = new ArrayList[3];
		statistics[0] = new ArrayList<>();
		statistics[1] = new ArrayList<>();
		statistics[2] = new ArrayList<>();
		while (generation.size() < populationSize) {
			generation.add(new Solution());
		}
		Semaphore semaphore = new Semaphore(-populationSize + 1);
		for (int index = 0; index < generation.size(); index++) {
			// new RandomSolution(getContext(), getVerificationMethod(),
			// generation, index, semaphore).start();
		
				generation.set(index, (Solution)SolutionUtil.getRandomViableSolution2(getContext(), getVerificationMethod()).clone());
			
			// generation.set(index,
			// SolutionUtil.getRandomViableSolution2(getContext(),
			// getVerificationMethod(), 0, 10));
			semaphore.release();
		}
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			for (Integer i = 0; i < amountOfGeneration; i++) {
				generation = generation.parallelStream().sorted((s1, s2) -> {
					Long fit1 = s1.getFitness(), fit2 = s2.getFitness();
					if (fit1 == -1L) {
//						if (!SolutionUtil.isSolvable(getContext(), s1)) {
//							System.out.println("makeSolvable non sovable !!");
//						}
//						if (getVerificationMethod().getPlaced(s1) == null) {
//							System.out.println("makeSolvable non packable  !!");
//						}
						s1.setFitness(getFitness(s1));
						fit1 = s1.getFitness();
					}
					if (fit2 == -1L) {
//						if (!SolutionUtil.isSolvable(getContext(), s2)) {
//							System.out.println("makeSolvable non sovable !!");
//						}
//						if (getVerificationMethod().getPlaced(s2) == null) {
//							System.out.println("makeSolvable non packable  !!");
//						}
						s2.setFitness(getFitness(s2));
						fit2 = s2.getFitness();
					}
					if (fit1 > fit2)
						return 1;
					if (fit2 > fit1)
						return -1;
					return 0;
				}).collect(Collectors.toList()).subList(0, (int) (generation.size() * bestPartPercentage));
				statistics[0].add(generation.stream().mapToLong(value -> value.getFitness()).min().getAsLong());
				statistics[1].add(generation.stream().mapToLong(value -> value.getFitness()).sum() / generation.size());
				statistics[2].add(generation.stream().mapToLong(value -> value.getFitness()).max().getAsLong());
				CoupleIterator coupleIterator = new CoupleIterator(generation);
				Couple c;
				Solution s;
				while (generation.size() < populationSize) {
					c = coupleIterator.next();
					s = getViableCrossedSolution(c);
					if (s != null) {
						// TODO : Décommenter quand fonctionnel
						// if (random.nextDouble() * 100 <= mutationFrequency) s
						// = getViableMutatedSolution(s);
						generation.add(s);
					}
				}
				/*
				 * generation.parallelStream().forEach(solution -> { if
				 * (random.nextDouble() <= mutationFrequency) solution =
				 * getViableMutatedSolution(solution); });
				 */
				generation.stream().filter(tempSolution -> random.nextDouble() <= mutationFrequency).forEach(solution -> solution = getViableMutatedSolution((Solution)solution.clone()));
				System.out.println("Génération : " + i + ", Fitness : " + bestSolution.getFitness());
			}
		} finally {
			Resolution resolution = new Resolution(getContext());
			resolution.setSolution(bestSolution);
			String filepath = new ToPNG().save("genetic", resolution);
			Report.makeStatisticReport(filepath, statistics[0], statistics[1], statistics[2]);
		}
	}

	public Long getFitness(Solution solution) {
//		if (!SolutionUtil.isSolvable(getContext(), solution)) {
//			System.out.println("makeSolvable non sovable !!");
//		}
//		if (getVerificationMethod().getPlaced(solution) == null) {
//			System.out.println("makeSolvable non packable  !!");
//		}
		Long fit = getLinearResolutionMethod().getFitnessAndRemoveUseless(solution, (long) getContext().getPatternCost(), (long) getContext().getSheetCost());
		solution.setFitness(fit);
		if (bestSolution == null || solution.getFitness() < bestSolution.getFitness()) {
			// if(getVerificationMethod().getPlaced(solution)==null){
			// System.out.println("Non packable");
			// }
			bestSolution = getVerificationMethod().getPlaced(solution);
			Resolution resolution = new Resolution(getContext());
			resolution.setSolution(bestSolution);
			new ToPNG().save("genetic-inter", resolution);
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
