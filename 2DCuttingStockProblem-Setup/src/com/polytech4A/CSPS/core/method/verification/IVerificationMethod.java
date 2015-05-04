package com.polytech4A.CSPS.core.method.verification;

import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;

public interface IVerificationMethod {
	
	/**
	 * 
	 * @param solution
	 * @return
	 */
	default boolean isViable(Solution solution) {
		return getPlaced(solution) != null;
	}

	/**
	 * Place les images dans les patterns si c'est possible
	 * @param solution
	 * @return Solution si on arrive pas à placer les images dans le pattern, sinon 'null'
	 */
	public Solution getPlaced(Solution solution);
	
	/**
	 * Donne le placement des images dans 1 Pattern si c'est possible, sinon
	 * renvoie 'null'. Pour information, le placement de l'image est fixé en bas
	 * à gauche & le placement de l'image ce fait verticalement (en priorité).
	 * 
	 * @param pattern
	 * @return Pattern si c'est possible, 'null' sinon
	 */
	public Pattern getPlacedPattern(Pattern pattern);
	
	/**
	 * Donne le placement des images dans 1 Pattern si c'est possible, sinon
	 * renvoie 'null'. Pour information, le placement de l'image est fixé en bas
	 * à gauche & le placement de l'image ce fait verticalement (en priorité).
	 * 
	 * @param pattern
	 * @return Pattern si c'est possible, 'null' sinon
	 */
	public Pattern getPlacedPatternRecursive(Pattern pattern, int maxEssais);
}
