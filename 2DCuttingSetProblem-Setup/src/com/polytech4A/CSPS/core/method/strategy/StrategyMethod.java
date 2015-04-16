package com.polytech4A.CSPS.core.method.strategy;

import com.polytech4A.CSPS.core.method.LinearResolutionMethod;
import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

/**
 * Strategies de resolution de probleme
 * 
 * @author Alexandre & Corinne & Laura
 *
 */

public abstract class StrategyMethod implements Runnable {
	
	/**
	 * cout d'un Pattern
	 */
	private Long costOfPattern;
	
	/**
	 * cout d'une feuille d'impression
	 */
	private Long costOfPrinting;
	
	/**
	 * liste d'image a placer
	 */
	private Image[] goal;
	
	/**
	 * Permet de verifie si on peut placer les images dans les patterns
	 */
	private IVerificationMethod listVerifMethode;
	
	/**
	 * Permet de calculer la fitness d'une solution
	 */
	private LinearResolutionMethod listResolMethode;

	/**
	 *
	 * @param solution : meilleure solution rencontrée
	 * @
	 * @return Solution
	 */
	protected Solution bestSolution;

	/**
	 *
	 * @param context : conditions initiale
	 * @param solution : solution initiale
	 * @
	 * @return Solution
	 */
	public Solution getSolution(Context context, Solution solution) {
		bestSolution = new Solution(solution);
		run();
		return bestSolution;
	}
	/**
	 *
	 * @param context : conditions initiale
	 *
	 * @return Solution
	 */
	public Solution getSolution(Context context) {
		return getSolution(context, new Solution());
	}
}
