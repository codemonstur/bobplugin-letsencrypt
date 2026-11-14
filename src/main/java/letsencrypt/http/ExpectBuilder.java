package letsencrypt.http;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * The {@code ExpectBuilder} interface.
 */
public interface ExpectBuilder extends RequestExecutor {
    /**
     * Returns the result of {@code expectStatusCode}.
     *
     * @param code the code
     * @return the result
     */
    ExpectBuilder expectStatusCode(int code);
    /**
     * Returns the result of {@code expectSuccess}.
     *
     * @return the result
     */
    ExpectBuilder expectSuccess();
    /**
     * Returns the result of {@code expectSuccess}.
     *
     * @param supplier the supplier
     * @return the result
     */
    ExpectBuilder expectSuccess(Supplier<? extends IOException> supplier);
    /**
     * Returns the result of {@code expect}.
     *
     * @param expect the expect
     * @return the result
     */
    ExpectBuilder expect(Expect<String> expect);
}
