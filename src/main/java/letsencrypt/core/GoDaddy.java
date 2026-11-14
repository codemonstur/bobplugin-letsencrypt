package letsencrypt.core;

import letsencrypt.util.DnsRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static bobthebuildtool.services.Functions.isNullOrEmpty;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static letsencrypt.http.HttpCallBuilder.newHttpsCall;
import static letsencrypt.util.DnsRecord.TYPE_LIST_RECORDS;
import static letsencrypt.util.Functions.isInThePast;
import static org.xbill.DNS.Type.NS;
import static org.xbill.DNS.Type.TXT;

/**
 * The {@code GoDaddy} enum.
 */
public enum GoDaddy {;

    /**
     * Returns the result of {@code getRecords}.
     *
     * @param apiKey the api key
     * @param domain the domain
     * @return the result
     * @throws IOException if the operation fails
     */
    public static List<DnsRecord> getRecords(final String apiKey, final String domain) throws IOException {
        return newHttpsCall("api.godaddy.com")
            .get("/v1/domains/%s/records", domain)
            .header("Authorization", "sso-key " + apiKey)
            .expectSuccess()
            .fetchInto(TYPE_LIST_RECORDS);
    }

    /**
     * Performs the {@code setRecord} operation.
     *
     * @param apiKey the new value
     * @param domain the new value
     * @param type the new value
     * @param name the new value
     * @param value the new value
     * @throws IOException if the operation fails
     */
    public static void setRecord(final String apiKey, final String domain, final String type, final String name, final String value) throws IOException {
        setRecord(apiKey, domain, type, name, singletonList(new DnsRecord(type, name, value)));
    }

    /**
     * Performs the {@code setRecord} operation.
     *
     * @param apiKey the new value
     * @param domain the new value
     * @param type the new value
     * @param name the new value
     * @param records the new value
     * @throws IOException if the operation fails
     */
    public static void setRecord(final String apiKey, final String domain, final String type, final String name, final List<DnsRecord> records) throws IOException {
        newHttpsCall("api.godaddy.com")
            .put("/v1/domains/%s/records/%s/%s", domain, type, name)
            .header("Authorization", "sso-key " + apiKey)
            .header("Content-Type", "application/json")
            .jsonBody(records)
            .expectSuccess()
            .execute();
    }

    /**
     * Performs the {@code deleteRecord} operation.
     *
     * @param apiKey the api key
     * @param domain the domain
     * @param type the type
     * @param name the name
     * @throws IOException if the operation fails
     */
    public static void deleteRecord(final String apiKey, final String domain, final String type, final String name) throws IOException {
        newHttpsCall("api.godaddy.com")
            .put("/v1/domains/%s/records/%s/%s", domain, type, name)
            .header("Authorization", "sso-key " + apiKey)
            .header("Content-Type", "application/json")
            .jsonBody(List.of(new DnsRecord(type, name, "")))
            .expectSuccess()
            .execute();
    }

    // Tried really hard to get this to work. DNS has too many caching tricks and just gets in the way.
    // If you try to query like this you will always and forever get the wrong value. Even after the
    // whole world already gets the right value. It will not pass until the 10-minute timeout is over.
    //
    // So we are better off just waiting 10 minutes.
    /**
     * Performs the {@code waitForGoDaddyUpdate} operation.
     *
     * @param domain the domain
     * @param name the name
     * @param expected the expected
     * @param maxWait the max wait
     * @throws TextParseException if the operation fails
     * @throws UnknownHostException if the operation fails
     * @throws InterruptedException if the operation fails
     * @throws TimeoutException if the operation fails
     */
    public static void waitForGoDaddyUpdate(final String domain, final String name, final String expected, final long maxWait)
            throws TextParseException, UnknownHostException, InterruptedException, TimeoutException {
        final var hostname = name + "." + domain;
        final var nameservers = nameservers(domain);
        final long stopTime = currentTimeMillis() + maxWait;

        while (true) {
            if (isInThePast(stopTime)) throw new TimeoutException("GoDaddy did not make the correct value available in time");
            if (allMatch(listTxtRecords(nameservers, hostname), expected)) break;
            //noinspection BusyWait
            Thread.sleep(5_000);
        }
    }

    /**
     * Returns the result of {@code nameservers}.
     *
     * @param domain the domain
     * @return the result
     * @throws TextParseException if the operation fails
     */
    public static List<String> nameservers(final String domain) throws TextParseException {
        final var lookup = new Lookup(domain, NS).run();
        if (lookup == null) return Collections.emptyList();
        return stream(lookup).map(Record::getAdditionalName).map(Name::toString).collect(toList());
    }

    /**
     * Returns the result of {@code listTxtRecords}.
     *
     * @param nameservers the nameservers
     * @param hostname the hostname
     * @return the result
     * @throws TextParseException if the operation fails
     * @throws UnknownHostException if the operation fails
     */
    public static List<String> listTxtRecords(final List<String> nameservers, final String hostname)
            throws TextParseException, UnknownHostException {
        final List<String> records = new ArrayList<>();

        // GoDaddy has two nameservers, and they sometimes disagree with each other. So we need to find
        // both and query both.
        for (final var nameserver : nameservers) {
            final var lookup = new Lookup(hostname, TXT);
            lookup.setResolver(new SimpleResolver(nameserver));
            final var dnsTypeLookup = lookup.run();
            if (dnsTypeLookup == null) continue;
            // We add all records there might be, but there really should be only one here
            for (final var record : dnsTypeLookup) {
                records.addAll(((TXTRecord) record).getStrings());
            }
        }

        return records;
    }

    private static boolean allMatch(final List<String> list, final String expected) {
        if (isNullOrEmpty(list)) return false;
        for (final var value : list) if (!expected.equals(value)) return false;

        return true;
    }

}
