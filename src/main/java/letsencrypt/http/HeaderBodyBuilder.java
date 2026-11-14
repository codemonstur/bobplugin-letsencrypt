package letsencrypt.http;

import java.net.http.HttpRequest.BodyPublisher;

/**
 * The {@code HeaderBodyBuilder} interface.
 */
public interface HeaderBodyBuilder extends ExpectBuilder {
    /**
     * Returns the result of {@code header}.
     *
     * @param name the name
     * @param value the value
     * @return the result
     */
    HeaderBodyBuilder header(String name, String value);
    /**
     * Returns the result of {@code body}.
     *
     * @param body the body
     * @return the result
     */
    ExpectBuilder body(String body);
    /**
     * Returns the result of {@code body}.
     *
     * @param body the body
     * @return the result
     */
    ExpectBuilder body(BodyPublisher body);
    /**
     * Returns the result of {@code jsonBody}.
     *
     * @param body the body
     * @return the result
     */
    ExpectBuilder jsonBody(Object body);
}
