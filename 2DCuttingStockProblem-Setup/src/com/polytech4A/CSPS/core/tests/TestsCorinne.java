package com.polytech4A.CSPS.core.tests;

import java.util.ArrayList;

import com.polytech4A.CSPS.core.method.LinearResolutionMethod;
import com.polytech4A.CSPS.core.method.verification.VerificationMethodGuillotineSimple;
import com.polytech4A.CSPS.core.method.verification.VerificationMethodImpl;
import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.Resolution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;
import com.polytech4A.CSPS.core.util.SolutionUtil;



/**
 * @author Alexandre 09/04/2015
 */
public class TestsCorinne {
	public static void main(String[] args) {
		VerificationMethodImpl v = new VerificationMethodImpl();
		
		Tests tests = new Tests();
        Context context = tests.getContext(0);
        Pattern pattern = tests.getSolution(0).getPatterns().get(0);
        Image img = pattern.getListImg().get(0);
        Solution solution = tests.getSolution(0);
        img.toString();
        LinearResolutionMethod linearResolutionMethod = new LinearResolutionMethod(context);
        ArrayList<Long> count = linearResolutionMethod.getCount(tests.getSolution(0));
//        count.stream().forEach(c -> System.out.println(c));
        LinearResolutionMethod.check(count, context, solution);
        Resolution resolution = new Resolution(context);
        
        SolutionUtil s = new SolutionUtil();
        System.out.println("Solution : "+s.makeSolvable(context, solution,v));
//        long time = System.nanoTime()/1000;
//        Solution s = v.getPlaced(tests.getSolution(0));
//        long time2 = System.nanoTime()/1000;
//        System.out.println("Temps : "+(time2-time)+" micro secondes");
        
//        resolution.setSolution(s);
//        System.out.println("Final : "+s);
//        new ToPNG().save("test", resolution);
	}
}
