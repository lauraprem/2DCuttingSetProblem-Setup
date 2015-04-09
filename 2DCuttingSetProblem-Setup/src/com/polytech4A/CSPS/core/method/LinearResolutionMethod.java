package com.polytech4A.CSPS.core.method;

import com.polytech4A.CSPS.core.model.Solution;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class LinearResolutionMethod {
	public static ArrayList<Integer> getCount(Solution solution) {
		// TODO
		throw new NotImplementedException();
	}

	public static Long getFitness(Solution solution, Long costOfPattern,
			Long costOfPrinting) {
		ArrayList<Integer> count = getCount(solution);
		Long prints = count
				.parallelStream()
				.mapToLong(i -> i.longValue()).sum();

		return prints * costOfPrinting + count.size() * costOfPattern;
	}
}
