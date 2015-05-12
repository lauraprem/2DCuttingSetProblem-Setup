package com.polytech4A.CSPS.core.model.couple;

import com.polytech4A.CSPS.core.model.Solution;

import java.util.*;

/**
 * @author Alexandre
 *         29/04/2015
 */
public class CoupleIterator implements Iterator<Couple> {
    private Integer indexMax;
    private List<Solution> generation;
    private Random random = new Random();

    public CoupleIterator(List<Solution> generation) {
        this.indexMax = generation.size();
        this.generation = generation;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return indexMax >= 2;
    }

    /**
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     * @@ -60,4 +58,4 @@ public Couple next() {
     * public void reset() {
     * current = null;
     * }
     * -}
     * +}
     * Please sign in to comment.
     * <p>
     * }
     * <p>
     * /**
     * Returns the next element in the iteration.
     */
    @Override
    public synchronized Couple next() {
        if (indexMax < 2) throw new NoSuchElementException();
        int indexS1 = random.nextInt(indexMax), indexS2 = random.nextInt(indexMax - 1);
        if (indexS2 >= indexS1) indexS2++;
        return new Couple(indexS1, indexS2);
    }
}
