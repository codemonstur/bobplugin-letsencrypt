package letsencrypt.http;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * The {@code RequestExecutor} interface.
 */
public interface RequestExecutor {
    /**
     * Returns the result of {@code fetchInto}.
     *
     * @param <T> the type parameter
     * @param type the type
     * @return the result
     * @throws IOException if the operation fails
     */
    <T> T fetchInto(Type type) throws IOException;
    /**
     * Returns the result of {@code fetchInto}.
     *
     * @param <T> the type parameter
     * @param clazz the clazz
     * @return the result
     * @throws IOException if the operation fails
     */
    <T> T fetchInto(Class<T> clazz) throws IOException;
    /**
     * Performs the {@code execute} operation.
     *
     * @throws IOException if the operation fails
     */
    void execute() throws IOException;
}
