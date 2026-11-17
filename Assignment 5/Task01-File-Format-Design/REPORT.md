# A1 – Transport Office Bus-Pass Records

## A1.1 Record layouts

**Delimited text:** `passId|registerNo|name|routeNo|boardingStop|validUntil|amountPaid` followed by LF. UTF-8 encoding. Escape backslash as `\\` and delimiter as `\|` in every string field; parse with an escape-aware parser, not `String.split("|")`. Numeric fields are decimal text; date is ISO-8601 (`YYYY-MM-DD`). Example:
`P1001|192311001|Arun Kumar|12|Central\\|East|2027-05-31|4500.00`

**Binary DataOutputStream:** write fields in this order: `writeUTF(passId)`, `writeUTF(registerNo)`, `writeUTF(name)`, `writeInt(routeNo)`, `writeUTF(boardingStop)`, `writeUTF(validUntil)`, `writeDouble(amountPaid)`. `writeUTF` stores a length prefix, so strings are not fixed-width; the record is variable-length. A name containing a delimiter needs no escaping in this binary layout.

## A1.2 SIMATS-style pseudocode

```text
ALGORITHM AddPass(pass)
BEGIN
    VALIDATE required fields, routeNo > 0, amountPaid >= 0 and ISO date
    ENCODE string fields using backslash escaping
    OPEN BufferedWriter(FileWriter(file, UTF_8, APPEND))
    WRITE encoded fields joined by '|', then NEWLINE
    CLOSE writer using try-with-resources
END

ALGORITHM FindPass(passId)
BEGIN
    IF file does not exist THEN RETURN NOT_FOUND
    OPEN BufferedReader(FileReader(file, UTF_8))
    WHILE line := READ_LINE() is not EOF
        fields := ESCAPE_AWARE_PARSE(line)
        IF field count is not 7 OR numeric/date validation fails THEN
            LOG damaged line number; CONTINUE
        END IF
        IF fields[0] equals passId THEN CLOSE; RETURN fields
    END WHILE
    CLOSE reader; RETURN NOT_FOUND
END

ALGORITHM ListRoute(routeNo)
BEGIN
    result := EMPTY_LIST
    IF file does not exist THEN RETURN result
    OPEN BufferedReader(FileReader(file, UTF_8))
    FOR EACH line
        fields := ESCAPE_AWARE_PARSE(line)
        IF valid record AND parsed route equals routeNo THEN APPEND fields TO result
        ELSE IF invalid THEN LOG damaged line; CONTINUE
    END FOR
    CLOSE reader; RETURN result
END
```

`BufferedReader`/`BufferedWriter` reduce small read/write calls and provide line-oriented text processing. `FileReader`/`FileWriter` are character streams; specify UTF-8 explicitly in production. `FileInputStream`/`FileOutputStream` are byte streams suited to binary records or explicit byte encoding.

## A1.3 Damaged data and recovery

Validate field count, escaping, numeric ranges and date format per line; log line number and reason, skip only the damaged record, and continue. Missing file: treat as an empty store for first-time creation, but distinguish this from permission/I/O errors. For crash safety, write a complete new file to a sibling temporary file, flush/close (and force to disk where required), then atomically rename it over the original; retain a backup or journal. A partially appended final line is detected by missing fields/newline/checksum and quarantined. For concurrent writers, use a file lock; a database is preferable when several counters write concurrently.

## Alternative: fixed-length RandomAccessFile

A fixed-width record allows direct seek to record index `i` at `i × recordSize`, O(1) once the index is known. Searching by pass ID still requires O(n) scan unless a separate ID-to-offset index is maintained (then expected O(1) hash lookup plus O(1) seek). Fixed-size strings waste padding: if a name uses 80 bytes but averages 22, about 58 bytes per record are unused, in addition to other fields' padding. Text is human-readable and variable-sized but ID search is O(n).
