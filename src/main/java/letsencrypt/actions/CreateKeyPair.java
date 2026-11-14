package letsencrypt.actions;

import bobthebuildtool.pojos.buildfile.Project;
import jcli.annotations.CliCommand;
import jcli.annotations.CliOption;
import jcli.errors.InvalidCommandLine;

import java.io.IOException;
import java.util.Map;

import static jcli.CliParserBuilder.newCliParser;
import static letsencrypt.util.Crypto.newKeypair;
import static letsencrypt.util.Crypto.saveKeyPair;

/**
 * The {@code CreateKeyPair} enum.
 */
public enum CreateKeyPair {;

    /**
     * The {@code CliArguments} class.
     */
    @CliCommand(name = "create-keypair", description = "create a cryptographic keypair")
    public static final class CliArguments {
        /**
         * The {@code outputFile} field.
         */
        @CliOption(name = 'o', longName = "output-file", isMandatory = true, description = "The path to write the keypair to in PEM format")
        public String outputFile;
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
     * Returns the result of {@code createKeyPair}.
     *
     * @param project the project
     * @param env the env
     * @param args the command-line arguments
     * @return the result
     * @throws IOException if the operation fails
     * @throws InvalidCommandLine if the operation fails
     */
    public static int createKeyPair(final Project project, final Map<String, String> env, final String[] args)
            throws IOException, InvalidCommandLine {
        final CliArguments arguments = newCliParser(CliArguments::new).parse(args);

        saveKeyPair(newKeypair(), arguments.outputFile);
        return 0;
    }

}