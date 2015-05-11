package com.polytech4A.CSPS.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Alexandre
 *         09/04/2015
 */
public class Log {
    public final static Logger getLogger(Class clazz) {
        return LogManager.getLogger(clazz.getCanonicalName().replaceFirst("com.polytech4A.CSPS.core.", ""));
    }
}
