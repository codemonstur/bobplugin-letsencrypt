package letsencrypt.http;

/**
 * The {@code MethodUriBuilder} interface.
 */
public interface MethodUriBuilder {
    /**
     * Returns the result of {@code head}.
     *
     * @param uri the uri
     * @return the result
     */
    HeaderBodyBuilder head(String uri);
    /**
     * Returns the result of {@code head}.
     *
     * @param uriFormat the uri format
     * @param arguments the arguments
     * @return the result
     */
    HeaderBodyBuilder head(String uriFormat, Object... arguments);
    /**
     * Returns the result of {@code get}.
     *
     * @param uri the uri
     * @return the result
     */
    HeaderBodyBuilder get(String uri);
    /**
     * Returns the result of {@code get}.
     *
     * @param uriFormat the uri format
     * @param arguments the arguments
     * @return the result
     */
    HeaderBodyBuilder get(String uriFormat, Object... arguments);
    /**
     * Returns the result of {@code post}.
     *
     * @param uri the uri
     * @return the result
     */
    HeaderBodyBuilder post(String uri);
    /**
     * Returns the result of {@code post}.
     *
     * @param uriFormat the uri format
     * @param arguments the arguments
     * @return the result
     */
    HeaderBodyBuilder post(String uriFormat, Object... arguments);
    /**
     * Returns the result of {@code put}.
     *
     * @param uri the uri
     * @return the result
     */
    HeaderBodyBuilder put(String uri);
    /**
     * Returns the result of {@code put}.
     *
     * @param uriFormat the uri format
     * @param arguments the arguments
     * @return the result
     */
    HeaderBodyBuilder put(String uriFormat, Object... arguments);
    /**
     * Returns the result of {@code delete}.
     *
     * @param uri the uri
     * @return the result
     */
    HeaderBodyBuilder delete(String uri);
    /**
     * Returns the result of {@code delete}.
     *
     * @param uriFormat the uri format
     * @param arguments the arguments
     * @return the result
     */
    HeaderBodyBuilder delete(String uriFormat, Object... arguments);
    /**
     * Returns the result of {@code method}.
     *
     * @param method the method
     * @param uri the uri
     * @return the result
     */
    HeaderBodyBuilder method(String method, String uri);
    /**
     * Returns the result of {@code method}.
     *
     * @param method the method
     * @param uriFormat the uri format
     * @param arguments the arguments
     * @return the result
     */
    HeaderBodyBuilder method(String method, String uriFormat, Object... arguments);
}
