package com.polytech4A.CSPS.core.model.couple;

import com.polytech4A.CSPS.core.model.Solution;

/**
 * @author Alexandre
 *         29/04/2015
 */
public class Couple {
    private Solution s1;
    private Solution s2;

    public Couple(Solution s1, Solution s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public Solution getS1() {
        return s1;
    }

    public void setS1(Solution s1) {
        this.s1 = s1;
    }

    public Solution getS2() {
        return s2;
    }

    public void setS2(Solution s2) {
        this.s2 = s2;
    }
}
