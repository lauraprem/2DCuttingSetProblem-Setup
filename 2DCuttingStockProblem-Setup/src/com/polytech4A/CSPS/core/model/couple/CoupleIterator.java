package com.polytech4A.CSPS.core.model.couple;

import com.polytech4A.CSPS.core.model.Solution;

import java.util.*;

/**
 * @author Alexandre
 *         29/04/2015
 */
public class CoupleIterator implements Iterator<Couple> {
    private ArrayList<Solution> generation;
    private Random random = new Random();

    public CoupleIterator(List<Solution> generation) {
        this.generation = new ArrayList<>();
        for(Solution solution : generation) this.generation.add(solution);
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
        return generation.size() >= 2;
    }

    /**
@@ -60,4 +58,4 @@ public Couple next() {
    public void reset() {
        current = null;
    }
-}
+}
Please sign in to comment.

    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Couple next() {
        if (generation.size() < 2) throw new NoSuchElementException();
        int indexS1 = random.nextInt(generation.size()), indexS2 = random.nextInt(generation.size() - 1);
        if(indexS2 >= indexS1) indexS2++;
        return new Couple(generation.get(indexS1), generation.get(indexS2));
    }
}
