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
	public Resolution(Context context, Solution solution) {
		this.context = context;
		this.solution = solution;
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

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Resolution{");
		sb.append("context=").append(context);
		sb.append(", solution=").append(solution);
		sb.append("}\n");
		return sb.toString();
	}
}
