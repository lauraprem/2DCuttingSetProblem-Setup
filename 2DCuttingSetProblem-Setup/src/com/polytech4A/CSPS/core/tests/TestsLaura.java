package com.polytech4A.CSPS.core.tests;

import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.method.verification.VerificationMethodImpl;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.Resolution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;
import com.polytech4A.CSPS.core.resolution.util.file.ToPNG;
import com.polytech4A.CSPS.core.util.SolutionUtil;

/**
 * @author Alexandre
 *         09/04/2015
 */
public class TestsLaura {
	public static void main(String[] args) {
		// Image image1 = new Image(1L, new Vector(12L, 12L), 352L);
		// image1.setAmount(1L);
		// Image image2 = new Image(2L, new Vector(2L, 2L), 100L);
		// image2.setAmount(2L);
		// Image image3 = new Image(3L, new Vector(1L, 1L), 25L);
		// image3.setAmount(0L);
		// ArrayList<Image> imageList1 = new ArrayList<Image>();
		// imageList1.add(image1);
		// imageList1.add(image2);
		// imageList1.add(image3);
		// Pattern pattern1 = new Pattern(new Vector(30L, 30L), imageList1);
		//
		// VerificationMethodImpl verif = new VerificationMethodImpl();
		// // System.out.println(PatternUtil.addImage(pattern, 2L, verif));
		// // System.out.println(PatternUtil.supressImage(pattern, 2L, verif));
		//
		// Image image4 = new Image(1L, new Vector(12L, 12L), 352L);
		// image4.setAmount(2L);
		// Image image5 = new Image(2L, new Vector(2L, 2L), 100L);
		// image5.setAmount(0L);
		// Image image6 = new Image(3L, new Vector(1L, 1L), 25L);
		// image6.setAmount(1L);
		// ArrayList<Image> imageList2 = new ArrayList<Image>();
		// imageList2.add(image4);
		// imageList2.add(image5);
		// imageList2.add(image6);
		// Pattern pattern2 = new Pattern(new Vector(30L, 30L), imageList2);
		// ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		// patterns.add(pattern1);
		// patterns.add(pattern2);
		// Solution solution = new Solution(patterns);
		// System.out.println(solution);
		// System.out.println("---------------------------------------------------------");
		// System.out.println(SolutionUtil.getViableAddNeighbor(solution,
		// verif));

		Tests tests = new Tests();
		Context context = tests.getContext(0);
		IVerificationMethod verif = new VerificationMethodImpl();

//		Solution s = SolutionUtil.getRandomViableSolution2(context, verif, 2, 2);
//		System.out.println(SolutionUtil.isSolvable(context, s));
		// System.out.println(s);
		Resolution resolution = new Resolution(context);
//		resolution.setSolution(s);
		new ToPNG().save("genetic", resolution);

	}
}