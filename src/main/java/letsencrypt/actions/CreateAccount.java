package letsencrypt.actions;

import bobthebuildtool.pojos.buildfile.Project;
import jcli.annotations.CliCommand;
import jcli.annotations.CliOption;
import jcli.errors.InvalidCommandLine;
import org.shredzone.acme4j.Session;
import org.shredzone.acme4j.exception.AcmeException;

import java.io.IOException;
import java.util.Map;

import static jcli.CliParserBuilder.newCliParser;
import static letsencrypt.core.LetsEncrypt.newLetsEncryptAccount;
import static letsencrypt.util.Crypto.loadKeyPair;
import static letsencrypt.util.Functions.logInfo;

/**
 * The {@code CreateAccount} enum.
 */
public enum CreateAccount {;

    /**
     * The {@code CliArguments} class.
     */
    @CliCommand(name = "le-account", description = "create a cryptographic keypair")
    public static final class CliArguments {
        /**
         * The {@code staging} field.
         */
        @CliOption(longName = "staging", description = "Flag to use the staging server URI instead of the production URI")
        public boolean staging;

        /**
         * The {@code keyPairFile} field.
         */
        @CliOption(name = 'k', longName = "keypair", isMandatory = true, description = "Path to a PEM file to use for the new account")
        public String keyPairFile;
        /**
         * The {@code emailAddress} field.
         */
        @CliOption(name = 'e', longName = "email", isMandatory = true, description = "The email address to connect the account to")
        public String emailAddress;

        /**
         * The {@code quiet} field.
         */
        @CliOption(name = 'q', longName = "quiet", description = "Print no output")
        public boolean quiet;

        /**
         * The {@code help} field.
         */
        @CliOption(name = 'h', longName = "help", isHelp = true)
        public boolean help;

        /**
         * Creates a new instance.
         */
        public CliArguments() {
        }
    }

    /**
     * Returns the result of {@code createAccount}.
     *
     * @param project the project
     * @param env the env
     * @param args the command-line arguments
     * @return the result
     * @throws AcmeException if the operation fails
     * @throws IOException if the operation fails
     * @throws InvalidCommandLine if the operation fails
     */
    public static int createAccount(final Project project, final Map<String, String> env, final String[] args)
            throws AcmeException, IOException, InvalidCommandLine {
        final CliArguments arguments = newCliParser(CliArguments::new).parse(args);

        final var logEnabled = !arguments.quiet;

        logInfo(logEnabled, "Loading account keypair at " + arguments.keyPairFile);
        final var accountKeyPair = loadKeyPair(arguments.keyPairFile);

        final var serverUri = arguments.staging ? "acme://letsencrypt.org/staging" : "acme://letsencrypt.org";
        logInfo(logEnabled, "Connecting to " + serverUri);
        final var session = new Session(serverUri);

        logInfo(logEnabled, "Creating account for " + arguments.emailAddress);
        newLetsEncryptAccount(session, accountKeyPair, arguments.emailAddress);
        return 0;
    }

}
