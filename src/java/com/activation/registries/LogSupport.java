/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.PrintStream
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.util.logging.Level
 *  java.util.logging.Logger
 */
package com.sun.activation.registries;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogSupport {
    private static boolean debug = false;
    private static final Level level = Level.FINE;
    private static Logger logger;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        try {
            debug = Boolean.getBoolean((String)"javax.activation.debug");
        }
        catch (Throwable throwable) {}
        logger = Logger.getLogger((String)"javax.activation");
    }

    private LogSupport() {
    }

    public static boolean isLoggable() {
        return debug || logger.isLoggable(level);
    }

    public static void log(String string2) {
        if (debug) {
            System.out.println(string2);
        }
        logger.log(level, string2);
    }

    public static void log(String string2, Throwable throwable) {
        if (debug) {
            System.out.println(String.valueOf((Object)string2) + "; Exception: " + (Object)((Object)throwable));
        }
        logger.log(level, string2, throwable);
    }
}

