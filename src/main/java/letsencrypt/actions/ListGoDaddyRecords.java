package letsencrypt.actions;

import bobthebuildtool.pojos.buildfile.Project;
import jcli.annotations.CliCommand;
import jcli.annotations.CliOption;
import jcli.errors.InvalidCommandLine;
import letsencrypt.core.GoDaddy;
import letsencrypt.util.DnsRecord;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static bobthebuildtool.services.Log.logInfo;
import static jcli.CliParserBuilder.newCliParser;

/**
 * The {@code ListGoDaddyRecords} enum.
 */
public enum ListGoDaddyRecords {;

    /**
     * The {@code CliArguments} class.
     */
    @CliCommand(name = "list-godaddy-records", description = "List DNS records attached to a given hostname in GoDaddy")
    public static final class CliArguments {
        /**
         * The {@code hostname} field.
         */
        @CliOption(name = 'h', longName = "hostname", isMandatory = true, description = "A hostname to get DNS records for")
        public String hostname;
        /**
         * The {@code godaddyApiKey} field.
         */
        @CliOption(longName = "api-key", defaultValue = "", isMandatory = true, description = "The GoDaddy API key in [API_KEY]:[API_SECRET] format")
        public String godaddyApiKey;

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
     * Returns the result of {@code listRecords}.
     *
     * @param project the project
     * @param env the env
     * @param args the command-line arguments
     * @return the result
     * @throws IOException if the operation fails
     * @throws InvalidCommandLine if the operation fails
     */
    public static int listRecords(final Project project, final Map<String, String> env, final String[] args)
            throws IOException, InvalidCommandLine {
        final CliArguments arguments = newCliParser(CliArguments::new).parse(args);

        final List<DnsRecord> records = GoDaddy.getRecords(arguments.godaddyApiKey, arguments.hostname);
        if (records.isEmpty()) return 0;

        logInfo("Records for " + arguments.hostname);
        for (final DnsRecord record : records) {
            logInfo(record.type + "\t- " + record.name + " - " + record.data);
        }

        return 0;
    }

}
