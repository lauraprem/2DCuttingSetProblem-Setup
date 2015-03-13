package com.polytech4A.CuttingSetProblemSetup.core.method.verification;

import com.polytech4A.CuttingSetProblemSetup.core.model.Solution;

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
