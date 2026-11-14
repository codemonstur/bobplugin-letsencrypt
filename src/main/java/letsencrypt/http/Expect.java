package letsencrypt.http;

import java.io.IOException;
import java.net.http.HttpResponse;

/**
 * The {@code Expect} interface.
 *
 * @param <T> the type parameter
 */
public interface Expect<T> {
    /**
     * Performs the {@code verify} operation.
     *
     * @param response the response
     * @throws IOException if the operation fails
     */
    void verify(HttpResponse<T> response) throws IOException;
}