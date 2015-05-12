package com.polytech4A.CSPS.core.util;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.Resolution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Alexandre
 *         10/05/2015
 */
public class Report {
    public static void makeReport(String filename, Resolution resolution) {
        Long time = System.currentTimeMillis() - Context.getMilliStart();
        Context context = resolution.getContext();
        Solution solution = resolution.getSolution();
        StringBuilder str = new StringBuilder();
        str.append("Fitness : ");
        str.append(solution.getFitness());
        str.append(", Found on ");
        str.append(time);
        str.append("ms (");
        str.append(time / 1000);
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

    public static void makeStatisticReport(String filepath, List<Long> bestsFound, List<Long> bests, List<Long> averages, List<Long> worsts) {
        int maxGeneration = Math.max(bests.size(), Math.max(averages.size(), worsts.size()));
        StringBuilder str = new StringBuilder();
        str.append("Generation;");
        str.append("Best Found;");
        str.append("Best Fitness;");
        str.append("Average Fitness;");
        str.append("Worst Fitness;\n");
        int val;
        for (int i = 0; i < maxGeneration; i++) {
            Long bestFound = bestsFound.get(i), best = bests.get(i), average = averages.get(i), worst = worsts.get(i);
            str.append(i + 1);
            str.append(';');
            str.append(bestFound);
            str.append(';');
            str.append(best);
            str.append(';');
            str.append(average);
            str.append(';');
            str.append(worst);
            str.append('\n');
        }

        String filename = filepath + "/statistics.csv";
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
