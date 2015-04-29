package com.polytech4A.CSPS.core.resolution;

import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

/**
 * @author Alexandre
 *         13/03/2015
 */
public class Resolution extends Thread {
	private Context context;
	private Solution solution;

	public Resolution(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	@Override public void run() {

	}
}
