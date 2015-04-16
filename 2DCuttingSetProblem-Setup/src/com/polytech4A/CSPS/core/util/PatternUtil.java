package com.polytech4A.CSPS.core.util;

import com.polytech4A.CSPS.core.method.verification.IVerificationMethod;
import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;

/**
 * 
 * @author Laura Manipulatin des patterns pour les modifier
 */
public class PatternUtil {

	/**
	 * Rajoute une image au pattern si possible. La modification sera faite sur
	 * le pattern passé en paramètre par référence.
	 * 
	 * @param pattern
	 *            auquel on veut ajouter une image
	 * @param image
	 *            que l'on veut ajouter
	 * @param verif
	 *            methode de vérification souhaitée
	 * @return true si l'image peut etre insérée, false sinon
	 */
	public static boolean addImage(Pattern pattern, Long imageId, IVerificationMethod verif) {
		Pattern patternTemp;
		try {
			patternTemp = (Pattern) pattern.clone();
			for (Image image : patternTemp.getListImg()) {
				if (image.getId().equals(imageId)) {
					image.setAmount(image.getAmount() + 1);
					if ((patternTemp = verif.getPlacedPattern(patternTemp)) != null) {
						pattern.setPattern(patternTemp);

						return true;
					}
					break;
				}
			}

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean supressImage(Pattern pattern, Long imageId, IVerificationMethod verif) {
		Long amount;
		Pattern patternTemp;
		try {
			patternTemp = (Pattern) pattern.clone();

			for (int i = 0; i < patternTemp.getListImg().size(); i++) {
				if (patternTemp.getListImg().get(i).getId().equals(imageId) && (amount = patternTemp.getListImg().get(i).getAmount()) > 0) {
					patternTemp.getListImg().get(i).setAmount(amount - 1);
					if ((patternTemp = verif.getPlacedPattern(patternTemp)) != null) {
						pattern.setPattern(patternTemp);

						return true;
					}
					break;
				}
			}

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean exchangeImage(Pattern originPattern, Pattern destinationPattern, Long imageId, IVerificationMethod verif) {

		Pattern originTemp;
		Pattern destinaionTemp;
		Long amount;

		try {
			originTemp = (Pattern) originPattern.clone();
			destinaionTemp = (Pattern) destinationPattern.clone();
			// suppress from origin pattern
			for (int i = 0; i < originTemp.getListImg().size(); i++) {
				if (originTemp.getListImg().get(i).getId().equals(imageId) && (amount = originTemp.getListImg().get(i).getAmount()) > 0) {
					originTemp.getListImg().get(i).setAmount(amount - 1);
					if ((originTemp = verif.getPlacedPattern(originTemp)) != null) {
						// add to destination pattern
						for (Image image : destinaionTemp.getListImg()) {
							if (image.getId().equals(imageId))
								image.setAmount(image.getAmount() + 1);
						}
						if ((destinaionTemp = verif.getPlacedPattern(destinaionTemp)) != null) {
							// modify real patterns
							destinationPattern.setPattern(destinaionTemp);
							originPattern.setPattern(originTemp);
							return true;
						}
					}
				}
			}

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return false;
	}

}