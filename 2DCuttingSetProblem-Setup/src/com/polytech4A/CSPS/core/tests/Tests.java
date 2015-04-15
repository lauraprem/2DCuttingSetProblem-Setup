package com.polytech4A.CSPS.core.tests;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.model.Vector;
import com.polytech4A.CSPS.core.resolution.util.context.Context;

import java.util.ArrayList;

/**
 * @author Alexandre
 *         10/04/2015
 */
public class Tests {
    public ArrayList<Context> contexts = new ArrayList<>();
    public ArrayList<Solution> solution = new ArrayList<>();
    private ArrayList<Pattern> patterns = new ArrayList<>();

    public int size() { return contexts.size(); }
    public Context getContext(int i) { return contexts.get(i); }
    public Solution getSolution(int i) { return solution.get(i); }

    public Tests() {
        contexts.add(context0());

        solution.add(solution0());
    }

    private Solution solution0() {
        ArrayList<Image> images;

        images = new ArrayList<>(images0());
        images.get(3).setAmount(1L);
        images.get(5).setAmount(1L);
        images.get(7).setAmount(1L);
        images.get(11).setAmount(1L);
        images.get(14).setAmount(1L);
        images.get(17).setAmount(1L);
        images.get(18).setAmount(1L);
        images.get(19).setAmount(1L);
        patterns.add(new Pattern(context0().getPatternSize(), images));

        images = new ArrayList<>(images0());
        images.get(0).setAmount(1L);
        images.get(1).setAmount(1L);
        images.get(8).setAmount(1L);
        images.get(12).setAmount(1L);
        images.get(15).setAmount(1L);
        patterns.add(new Pattern(context0().getPatternSize(), images));

        images = new ArrayList<>(images0());
        images.get(10).setAmount(5L);
        images.get(11).setAmount(1L);
        images.get(13).setAmount(2L);
        images.get(14).setAmount(1L);
        images.get(17).setAmount(1L);
        images.get(18).setAmount(1L);
        patterns.add(new Pattern(context0().getPatternSize(), images));

        images = new ArrayList<>(images0());
        images.get(2).setAmount(1L);
        images.get(4).setAmount(1L);
        images.get(6).setAmount(1L);
        images.get(9).setAmount(1L);
        images.get(13).setAmount(1L);
        images.get(16).setAmount(2L);
        patterns.add(new Pattern(context0().getPatternSize(), images));

        return new Solution(1000, patterns);
    }


    private Context context0() {
        return new Context("test", 2, 400, images0(), new Vector(140000L, 70000L));
    }

    private ArrayList<Image> images0() {
        ArrayList<Image> images = new ArrayList<>();
        // 0
        images.add(new Image(0L, new Vector(93300L, 37200L), 179L));
        images.add(new Image(1L, new Vector(89300L, 30700L), 192L));
        images.add(new Image(2L, new Vector(72700L, 33300L), 121L));
        images.add(new Image(3L, new Vector(57100L, 40800L), 130L));
        images.add(new Image(4L, new Vector(84600L, 26300L), 117L));
        // 5
        images.add(new Image(5L, new Vector(73100L, 26900L), 172L));
        images.add(new Image(6L, new Vector(51100L, 34000L), 128L));
        images.add(new Image(7L, new Vector(70800L, 19900L), 157L));
        images.add(new Image(8L, new Vector(36400L, 36600L), 141L));
        images.add(new Image(9L, new Vector(31500L, 30100L), 128L));
        // 10
        images.add(new Image(10L, new Vector(47200L, 19800L), 161L));
        images.add(new Image(11L, new Vector(23400L, 39900L), 191L));
        images.add(new Image(12L, new Vector(19700L, 44300L), 180L));
        images.add(new Image(13L, new Vector(74200L, 10200L), 187L));
        images.add(new Image(14L, new Vector(46300L, 14400L), 186L));
        // 15
        images.add(new Image(15L, new Vector(45300L, 12700L), 153L));
        images.add(new Image(16L, new Vector(34100L, 15300L), 191L));
        images.add(new Image(17L, new Vector(14100L, 32400L), 200L));
        images.add(new Image(18L, new Vector(39600L, 7800L), 105L));
        images.add(new Image(19L, new Vector(16100L, 18200L), 134L));

        return images;
    }
}
