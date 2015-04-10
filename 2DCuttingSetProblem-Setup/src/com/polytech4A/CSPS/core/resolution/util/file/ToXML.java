package com.polytech4A.CSPS.core.resolution.util.file;

import com.polytech4A.CSPS.core.resolution.Resolution;

/**
 * @author Alexandre
 *         13/03/2015
 */
public class ToXML extends FileMethod {
	public ToXML() {
		super(".xml", "xml/");
	}

	@Override public void save(String filename, Resolution resolution) {
	}
}
