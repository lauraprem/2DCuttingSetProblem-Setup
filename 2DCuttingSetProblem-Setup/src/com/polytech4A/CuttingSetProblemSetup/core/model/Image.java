package com.polytech4A.CuttingSetProblemSetup.core.model;

import java.util.List;


/**
 * Image à positionner dans un pattern
 * @author Laura
 *
 */
public class Image {
	/**
	 * nombre de fois que l'image est présente dans un pattern
	 */
	private Long amout;
	
	/**
	 * Permet de savoir si l'image est tournée
	 */
	private boolean rotated;
	
	/**
	 * Liste des positions pour chaque fois que l'image est présente dans un pattern (amout)
	 */
	private List<Vector> positions;
	
	/**
	 * Taille de l'image
	 */
	private Vector size;
}
