package com.polytech4A.CSPS.core.tests;

import com.polytech4A.CSPS.core.method.LinearResolutionMethod;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

import java.util.ArrayList;

/**
 * @author Alexandre
 *         09/04/2015
 */
public class TestsAlexandre {

    public static void main(String[] args) {
        Tests tests = new Tests();
        Context context = tests.getContext(0);
        LinearResolutionMethod linearResolutionMethod = new LinearResolutionMethod(context);
        ArrayList<Long> count = linearResolutionMethod.getCount(tests.getSolution(0));
        count.stream().forEach(c -> System.out.println(c));
    }
}
