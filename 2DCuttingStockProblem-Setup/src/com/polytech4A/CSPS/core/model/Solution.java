package com.polytech4A.CSPS.core.model;

import java.util.ArrayList;

/**
 * Une solution au Cutting Problem
 *
 * @author Laura
 */
public class Solution {

	private Long fitness = -1L;

	public synchronized Long getFitness() {
		return fitness;
	}

	public synchronized void setFitness(Long fitness) {
		this.fitness = fitness;
	}

	/**
	 * liste des pattern qui forment la solution
	 */
	private ArrayList<Pattern> patterns = new ArrayList<Pattern>();

	public Solution(Solution solution) {
		this.patterns = (ArrayList<Pattern>) solution.patterns.clone();
	}

	public Solution(ArrayList<Pattern> patterns) {
		super();
		this.patterns = patterns;
	}

	public Solution() {
		super();
		this.patterns = new ArrayList<Pattern>();
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
		final StringBuffer sb = new StringBuffer("Solution{");
		if (fitness != -1L)
			sb.append("fitness=").append(fitness);
		sb.append(", patterns=").append(patterns);
		sb.append("}\n");
		return sb.toString();
	}

	public void setSolution(Solution s) {
		patterns = new ArrayList<Pattern>();
		try {
			for (Pattern pattern : s.getPatterns()) {
				patterns.add((Pattern) pattern.clone());
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		fitness = s.getFitness();
	}

	/**
	 * Creates and returns a copy of this object.  The precise meaning
	 * of "copy" may depend on the class of the object. The general
	 * intent is that, for any object {@code x}, the expression:
	 * <blockquote>
	 * <pre>
	 * x.clone() != x</pre></blockquote>
	 * will be true, and that the expression:
	 * <blockquote>
	 * <pre>
	 * x.clone().getClass() == x.getClass()</pre></blockquote>
	 * will be {@code true}, but these are not absolute requirements.
	 * While it is typically the case that:
	 * <blockquote>
	 * <pre>
	 * x.clone().equals(x)</pre></blockquote>
	 * will be {@code true}, this is not an absolute requirement.
	 *
	 * By convention, the returned object should be obtained by calling
	 * {@code super.clone}.  If a class and all of its superclasses (except
	 * {@code Object}) obey this convention, it will be the case that
	 * {@code x.clone().getClass() == x.getClass()}.
	 *
	 * By convention, the object returned by this method should be independent
	 * of this object (which is being cloned).  To achieve this independence,
	 * it may be necessary to modify one or more fields of the object returned
	 * by {@code super.clone} before returning it.  Typically, this means
	 * copying any mutable objects that comprise the internal "deep structure"
	 * of the object being cloned and replacing the references to these
	 * objects with references to the copies.  If a class contains only
	 * primitive fields or references to immutable objects, then it is usually
	 * the case that no fields in the object returned by {@code super.clone}
	 * need to be modified.
	 *
	 * The method {@code clone} for class {@code Object} performs a
	 * specific cloning operation. First, if the class of this object does
	 * not implement the interface {@code Cloneable}, then a
	 * {@code CloneNotSupportedException} is thrown. Note that all arrays
	 * are considered to implement the interface {@code Cloneable} and that
	 * the return type of the {@code clone} method of an array type {@code T[]}
	 * is {@code T[]} where T is any reference or primitive type.
	 * Otherwise, this method creates a new instance of the class of this
	 * object and initializes all its fields with exactly the contents of
	 * the corresponding fields of this object, as if by assignment; the
	 * contents of the fields are not themselves cloned. Thus, this method
	 * performs a "shallow copy" of this object, not a "deep copy" operation.
	 *
	 * The class {@code Object} does not itself implement the interface
	 * {@code Cloneable}, so calling the {@code clone} method on an object
	 * whose class is {@code Object} will result in throwing an
	 * exception at run time.
	 *
	 * @return a clone of this instance.
	 * @throws CloneNotSupportedException if the object's class does not
	 *                                    support the {@code Cloneable} interface. Subclasses
	 *                                    that override the {@code clone} method can also
	 *                                    throw this exception to indicate that an instance cannot
	 *                                    be cloned.
	 * @see Cloneable
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
