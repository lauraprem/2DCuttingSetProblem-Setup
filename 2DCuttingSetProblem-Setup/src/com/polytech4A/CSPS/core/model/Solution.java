package com.polytech4A.CSPS.core.model;

import java.util.ArrayList;

import static com.polytech4A.CSPS.core.util.Util.escToString;

/**
 * Une solution au Cutting Problem
 *
 * @author Laura
 */
public class Solution {

    /**
     * paramètre de conversion des float en long
     */
    private int scale;

    /**
     * liste des pattern qui forment la solution
     */
    private ArrayList<Pattern> patterns;

    public Solution(int scale, ArrayList<Pattern> patterns) {
        super();
        this.scale = scale;
        this.patterns = patterns;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public ArrayList<Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(ArrayList<Pattern> patterns) {
        this.patterns = patterns;
    }

    @Override
    public String toString() {
        return "SOLUTION\n"
                + "Paramètre de conversion des float en long : " + scale + "\n"
                + "Patterns qui forment la solution : " + escToString(patterns) + "\n";
    }
}
