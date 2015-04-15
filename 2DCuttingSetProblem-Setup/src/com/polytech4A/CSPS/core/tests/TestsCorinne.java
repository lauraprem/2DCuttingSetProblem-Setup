package com.polytech4A.CSPS.core.tests;

import java.util.ArrayList;
import java.util.Collections;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Vector;

/**
 * @author Alexandre 09/04/2015
 */
public class TestsCorinne {
	public static void main(String[] args) {

		// ArrayList<Image> listImage = new ArrayList<Image>();
		//
		// Image img1 = new Image(1L,new Vector(5L,40L),10L);
		// listImage.add(img1);
		// Image img2 = new Image(2L,new Vector(5L,10L),10L);
		// listImage.add(img2);
		// Image img3 = new Image(3L,new Vector(10L,5L),10L);
		// listImage.add(img3);
		// Image img4 = new Image(4L,new Vector(5L,30L),10L);
		// listImage.add(img4);
		// Image img5 = new Image(5L,new Vector(20L,5L),10L);
		// listImage.add(img5);
		//
		// Collections.sort(listImage,Image.ImageNameComparator);
		//
		// for(int i=0;i<listImage.size();i++){
		// System.out.println(listImage.get(i).toString());
		// }

		ArrayList<Pattern> listPattern = new ArrayList<Pattern>();

		Pattern p1 = new Pattern(new Vector(20L, 5L), 1L);
		listPattern.add(p1);
		Pattern p2 = new Pattern(new Vector(100L, 5L), 1L);
		listPattern.add(p2);
		Pattern p3 = new Pattern(new Vector(50L, 5L), 1L);
		listPattern.add(p3);
		Pattern p4 = new Pattern(new Vector(40L, 5L), 1L);
		listPattern.add(p4);
		Pattern p5 = new Pattern(new Vector(30L, 5L), 1L);
		listPattern.add(p5);

		Collections.sort(listPattern, Pattern.PatternNameComparator);

		for (int i = 0; i < listPattern.size(); i++) {
			System.out.println(listPattern.get(i).toString());
		}
	}
}
