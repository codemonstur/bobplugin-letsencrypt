package letsencrypt.http;

/**
 * The {@code ProtocolBuilder} interface.
 */
public interface ProtocolBuilder {
    /**
     * Returns the result of {@code scheme}.
     *
     * @param scheme the scheme
     * @return the result
     */
    ProtocolBuilder scheme(String scheme);
    /**
     * Returns the result of {@code port}.
     *
     * @param port the port
     * @return the result
     */
    ProtocolBuilder port(int port);
    /**
     * Returns the result of {@code hostname}.
     *
     * @param hostname the hostname
     * @return the result
     */
    MethodUriBuilder hostname(String hostname);
}
