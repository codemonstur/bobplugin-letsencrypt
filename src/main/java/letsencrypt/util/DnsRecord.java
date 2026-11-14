package letsencrypt.util;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * The {@code DnsRecord} class.
 */
public final class DnsRecord {

    /**
     * The {@code TYPE_LIST_RECORDS} constant.
     */
    public static final Type TYPE_LIST_RECORDS = new TypeToken<List<DnsRecord>>(){}.getType();

    /**
     * The {@code data} field.
     */
    public final String data;
    /**
     * The {@code name} field.
     */
    public final String name;
    /**
     * The {@code port} field.
     */
    public final Integer port;
    /**
     * The {@code priority} field.
     */
    public final Integer priority;
    /**
     * The {@code protocol} field.
     */
    public final String protocol;
    /**
     * The {@code service} field.
     */
    public final String service;
    // I would love for this value to be 0, but GoDaddy won't go lower than 600
    // If you try anyway the API will return a 422
    /**
     * The {@code ttl} field.
     */
    public final int ttl = 600;
    /**
     * The {@code type} field.
     */
    public final String type;
    /**
     * The {@code weight} field.
     */
    public final Integer weight;

    /**
     * Creates a new instance.
     *
     * @param type the type
     * @param name the name
     * @param data the data
     */
    public DnsRecord(final String type, final String name, final String data) {
        this.data = data;
        this.name = name;
        this.protocol = null;
        this.service = null;
        this.type = type;
        this.port = null;
        this.priority = null;
        this.weight = null;
    }

}
