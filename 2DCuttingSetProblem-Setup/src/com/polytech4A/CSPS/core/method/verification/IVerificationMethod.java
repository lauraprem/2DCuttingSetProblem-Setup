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
	 *
	 * @param solution
	 * @return
	 */
	public Solution getPlaced(Solution solution);
}
