package com.polytech4A.CSPS.core.model.couple;

import com.polytech4A.CSPS.core.model.Solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Alexandre
 *         29/04/2015
 */
public class CoupleIterator implements Iterator<Couple> {
    private ArrayList<Solution> generation;
    private Couple current;

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
        if (generation.size() < 2) return false;
        if (current == null) current = new Couple(generation.get(0), generation.get(1));
        	return (generation.indexOf(current.getS1()) != generation.size() - 1) && (generation.indexOf(current.getS2()) != generation.size() - 1);
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
        if (current == null) current = new Couple(generation.get(0), generation.get(1));
        int indexS1 = generation.indexOf(current.getS1()),
                indexS2 = generation.indexOf(current.getS2());
        if (indexS1 == generation.size() - 2 && indexS2 == generation.size() - 1) throw new NoSuchElementException();
        if(indexS2 == generation.size() - 1) {
            indexS1++;
            indexS2 = indexS1++;
        } else indexS2++;
        current = new Couple(generation.get(indexS1), generation.get(indexS2));
        return current;
    }

    public void reset() {
        current = null;
    }
}
