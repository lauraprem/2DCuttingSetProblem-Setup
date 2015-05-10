package com.polytech4A.CSPS.core.util;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.Resolution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Alexandre
 *         10/05/2015
 */
public class Report {
    public static void make(String filename, Resolution resolution) {
        Long time = System.currentTimeMillis() - Context.getMilliStart();
        Context context = resolution.getContext();
        Solution solution = resolution.getSolution();
        StringBuilder str = new StringBuilder();
        str.append("Fitness : ");
        str.append(solution.getFitness());
        str.append(", Found on ");
        str.append(time);
        str.append("ms (");
        str.append(time/1000);
        str.append("s)\n");
        for (Image image : context.getImages()) {
            str.append("Image ");
            str.append(image.getId());
            str.append(" : ");
            str.append(image.getSize().getWidth());
            str.append('x');
            str.append(image.getSize().getHeight());
            str.append(", Goal : ");
            str.append(image.getGoal());
            str.append('\n');
        }
        str.append('\n');
        str.append("     Image : ");
        for (Image image : context.getImages()) {
            Long id = image.getId();
            if (id < 10L) str.append(' ');
            str.append(image.getId());
            str.append(' ');
        }
        str.append('\n');
        for (int i = 0; i < solution.getPatterns().size(); i++) {
            Pattern pattern = solution.getPatterns().get(i);
            str.append("Pattern ");
            if (i < 10) str.append(' ');
            str.append(i);
            str.append(" : ");
            for (int j = 0; j < pattern.getListImg().size(); j++) {
                Image image = pattern.getListImg().get(j);
                Long amount = image.getAmount();
                if (amount < 10L) str.append(' ');
                str.append(amount);
                str.append(' ');
            }
            str.append('\n');
        }
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            out.write(str.toString().getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}