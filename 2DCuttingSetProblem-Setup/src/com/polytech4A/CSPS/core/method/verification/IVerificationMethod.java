package com.polytech4A.CSPS.core.method.verification;

import com.polytech4A.CSPS.core.model.Solution;

public interface IVerificationMethod {
	
	/**
	 * 
	 * @param solution
	 * @return
	 */
	public boolean isViable(Solution solution);
	
	/**
	 * 
	 * @param solution
	 * @return
	 */
	public Solution getPlaced(Solution solution);
}
