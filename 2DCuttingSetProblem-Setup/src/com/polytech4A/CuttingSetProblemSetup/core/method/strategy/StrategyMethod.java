package com.polytech4A.CuttingSetProblemSetup.core.method.strategy;

import java.util.ArrayList;

import com.polytech4A.CuttingSetProblemSetup.core.method.LinearResolutionMethod;
import com.polytech4A.CuttingSetProblemSetup.core.method.verification.IVerificationMethod;

public abstract class StrategyMethod extends Thread{
	private ArrayList<IVerificationMethod> listVerifMethode;
	private ArrayList<LinearResolutionMethod> listResolMethode;
}
