# bobplugin-letsencrypt

A [bob](https://github.com/codemonstur/bobthebuildtool) plugin that creates
[Let's Encrypt](https://letsencrypt.org) accounts and certificates using
**GoDaddy DNS** for domain validation (DNS-01 challenge).

Because it uses DNS-01, it can issue **wildcard certificates**.

## Commands

| Command | Purpose |
| --- | --- |
| `le-account` | Create an ACME account (keypair + email address). |
| `le-certificate` | Run the full DNS-01 flow and obtain a certificate. |
| `create-keypair` | Generate a new RSA/EC keypair on disk. |
| `list-godaddy-records` | List DNS records in the GoDaddy zone (debugging aid). |

## Certificate options

```
bob le-certificate --staging --domain example.com --hostname '*.example.com' ...
```

| Option | Description |
| --- | --- |
| `--staging` | Use the Let's Encrypt staging environment (no rate limits, untrusted certs). |
| `--organization` | Organization name for the CSR. |
| `-d`, `--domain` | Domain being validated (the zone). |
| `-h`, `--hostname` | Certificate name/SAN, e.g. `*.example.com`. |
| `-k`, `--domain-keypair-file` | Keypair file used to sign the CSR. |
| `-a`, `--account-keypair-file` | ACME account keypair file. |
| `-o`, `--output-file` | Where to write the issued certificate. |
| `--api-key` | GoDaddy API key in `KEY:SECRET` form. |
| `-q`, `--quiet` | Suppress progress output. |

## How it works

1. Load (or create) the ACME account keypair via [acme4j](https://github.com/shred/acme4j).
2. Place an order for the requested names.
3. For each DNS-01 challenge, publish the `_acme-challenge` TXT record through the
   **GoDaddy DNS REST API**.
4. Verify propagation with `dnsjava` (NS/TXT lookups with polling and timeout).
5. Finalize the order with a CSR and download the certificate.

## Notes

- The plugin swaps the thread context classloader while talking to acme4j,
  because acme4j discovers providers through `ServiceLoader`.
- DNS propagation is polled, so there can be a short wait after writing the TXT
  record.

## Building

```sh
make build
```

## License

MIT
