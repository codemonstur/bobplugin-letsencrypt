package letsencrypt.util;

import org.shredzone.acme4j.Certificate;
import org.shredzone.acme4j.util.KeyPairUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;

import static org.shredzone.acme4j.util.KeyPairUtils.readKeyPair;
import static org.shredzone.acme4j.util.KeyPairUtils.writeKeyPair;

/**
 * The {@code Crypto} enum.
 */
public enum Crypto {;

    /**
     * Returns the result of {@code newKeypair}.
     *
     * @return the result
     */
    public static KeyPair newKeypair() {
        return KeyPairUtils.createKeyPair(2048);
    }

    /**
     * Returns the result of {@code loadKeyPair}.
     *
     * @return the result
     * @throws IOException if the operation fails
     */
    public static KeyPair loadKeyPair() throws IOException {
        return loadKeyPair("keypair.pem");
    }
    /**
     * Returns the result of {@code loadKeyPair}.
     *
     * @param filename the filename
     * @return the result
     * @throws IOException if the operation fails
     */
    public static KeyPair loadKeyPair(final String filename) throws IOException {
        try (final var reader = new FileReader(filename)) {
            return readKeyPair(reader);
        }
    }

    /**
     * Performs the {@code saveKeyPair} operation.
     *
     * @param keyPair the key pair
     * @throws IOException if the operation fails
     */
    public static void saveKeyPair(final KeyPair keyPair) throws IOException {
        saveKeyPair(keyPair, "keypair.pem");
    }
    /**
     * Performs the {@code saveKeyPair} operation.
     *
     * @param keyPair the key pair
     * @param filename the filename
     * @throws IOException if the operation fails
     */
    public static void saveKeyPair(final KeyPair keyPair, final String filename) throws IOException {
        try (final var writer = new FileWriter(filename)) {
            writeKeyPair(keyPair, writer);
        }
    }

    /**
     * Performs the {@code writeCertificate} operation.
     *
     * @param cert the cert
     * @throws IOException if the operation fails
     */
    public static void writeCertificate(final Certificate cert) throws IOException {
        writeCertificate(cert, "cert-chain.crt");
    }
    /**
     * Performs the {@code writeCertificate} operation.
     *
     * @param cert the cert
     * @param filename the filename
     * @throws IOException if the operation fails
     */
    public static void writeCertificate(final Certificate cert, final String filename) throws IOException {
        try (final var writer = new FileWriter(filename)) {
            cert.writeCertificate(writer);
        }
    }

}
