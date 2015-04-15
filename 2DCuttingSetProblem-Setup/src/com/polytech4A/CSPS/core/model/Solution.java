package com.polytech4A.CSPS.core.model;

import static com.polytech4A.CSPS.core.util.Util.escToString;

import java.util.ArrayList;

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
    
     public Solution(int scale) {
        super();
        this.scale = scale;
        this.patterns = new ArrayList<Pattern>();
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
    
    public void addPattern(Pattern p) {
        this.patterns.add(p);
    }

    @Override
    public String toString() {
        return "SOLUTION\n"
                + "scale : " + scale + "\n"
                + "Patterns : " + escToString(patterns) + "\n";
    }
}
