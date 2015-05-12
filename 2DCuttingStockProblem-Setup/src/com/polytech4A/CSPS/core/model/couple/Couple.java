package com.polytech4A.CSPS.core.model.couple;

import com.polytech4A.CSPS.core.model.Solution;

/**
 * @author Alexandre
 *         29/04/2015
 */
public class Couple {
    private Integer s1;
    private Integer s2;

    public Couple(Integer s1, Integer s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public Integer getS1() {
        return s1;
    }

    public Integer getS2() {
        return s2;
    }
}
