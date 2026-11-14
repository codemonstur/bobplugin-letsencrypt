package letsencrypt.util;

import bobthebuildtool.services.Log;

import java.net.URLEncoder;

import static java.lang.System.currentTimeMillis;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The {@code Functions} enum.
 */
public enum Functions {;

    /**
     * Performs the {@code logInfo} operation.
     *
     * @param enabled the enabled
     * @param message the message
     */
    public static void logInfo(final boolean enabled, final String message) {
        if (enabled) Log.logInfo(message);
    }

    /**
     * Returns the result of {@code urlEncode}.
     *
     * @param value the value
     * @return the result
     */
    public static String urlEncode(final String value) {
        return value != null ? URLEncoder.encode(value, UTF_8) : "";
    }

    /**
     * Returns the result of {@code isInThePast}.
     *
     * @param stopTime the stop time
     * @return whether the condition holds
     */
    public static boolean isInThePast(final long stopTime) {
        return stopTime < currentTimeMillis();
    }

}
