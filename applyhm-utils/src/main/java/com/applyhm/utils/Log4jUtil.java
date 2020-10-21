package com.applyhm.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log4jUtil {

    static final String BR = System.getProperty("line.separator");
    private static final Logger logger = LogManager.getLogger(Log4jUtil.class.getName());

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void db(String message) {
        logger.info(message);
    }

    public static void exception(Exception ex) {
        String message = getErrorStackTrace(ex);
        error(message);
    }

    public static String getErrorStackTrace(Exception ex) {
        String error = ex.toString() + BR;
        StackTraceElement[] st = ex.getStackTrace();
        for (StackTraceElement s : st) {
            error += "\t " + s + BR;
        }
        return error;
    }
}
