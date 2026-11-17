# B2 – Adversarial inputs

Run `schema.sql` in a disposable local assignment database only. Configure `DB_URL`, `DB_USER`, `DB_PASSWORD`, add MySQL Connector/J to classpath, then run `LoginDemo`. Input `admin' -- ` with a wrong password can make the concatenated query's password predicate a comment (MySQL accepts `-- ` followed by whitespace). The prepared query treats it as a literal user ID and rejects it. Never run the deliberately unsafe query against a real account database.

Also test `PassStore` using:
- `Pbad|too|few` (wrong field count)
- `Pbad|REG|Name|x|Stop|2027-01-01|12` (non-numeric route)
- an empty line
- a seven-field row with `not-a-number` amount.
All are rejected/skipped; later valid records remain searchable. These reveal that parsing must validate schema and data, not merely split strings.
