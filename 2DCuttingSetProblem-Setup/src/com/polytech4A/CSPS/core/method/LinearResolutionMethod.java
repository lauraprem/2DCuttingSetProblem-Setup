package com.polytech4A.CSPS.core.method;

import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.util.Log;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class LinearResolutionMethod {
	public ArrayList<Integer> getCount(Solution solution) {
		// TODO
		throw new NotImplementedException();
	}

	public Long getFitness(Solution solution, Long costOfPattern,
			Long costOfPrinting) {
		ArrayList<Integer> count = getCount(solution);
		Long prints = count
				.parallelStream()
				.mapToLong(i -> i.longValue()).sum();
		Long fitness = prints * costOfPrinting + count.size() * costOfPattern;
		Log.log.trace(String.format("fitness = %s", fitness));
		return fitness;
	}
}
