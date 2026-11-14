package letsencrypt.util;

import org.shredzone.acme4j.Authorization;
import org.shredzone.acme4j.Order;
import org.shredzone.acme4j.exception.AcmeException;

/**
 * The {@code Exceptions} enum.
 */
public enum Exceptions {;

    /**
     * Returns the result of {@code challengeFailedBeforeTried}.
     *
     * @param auth the auth
     * @return the result
     */
    public static AcmeException challengeFailedBeforeTried(final Authorization auth) {
        return new AcmeException("Challenge " + auth.getIdentifier() + " was already failed: " + auth.getStatus());
    }
    /**
     * Returns the result of {@code challengeFailed}.
     *
     * @param auth the auth
     * @return the result
     */
    public static AcmeException challengeFailed(final Authorization auth) {
        return new AcmeException("We failed a challenge " + auth.getIdentifier());
    }
    /**
     * Returns the result of {@code signingRequestFailed}.
     *
     * @param order the order
     * @return the result
     */
    public static AcmeException signingRequestFailed(final Order order) {
        return new AcmeException("We failed a signing request: " + order.getError().map(Object::toString).orElse("unknown error"));
    }

}
