package com.polytech4A.CSPS.core.method.verification;

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
}
