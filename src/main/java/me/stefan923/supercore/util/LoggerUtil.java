package me.stefan923.supercore.util;

import me.stefan923.supercore.SuperCore;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggerUtil {

    private static final Logger logger = SuperCore.getInstance().getLogger();

    private LoggerUtil() { }

    public static void sendError(String message) {
        logger.log(Level.SEVERE, message);
    }

    public static void sendInfo(String message) {
        logger.log(Level.INFO, message);
    }

}
