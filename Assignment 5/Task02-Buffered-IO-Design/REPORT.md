# A2 – Don't Read What You Don't Need

A 50 MiB file contains 52,428,800 bytes, so a byte-at-a-time loop calls `FileInputStream.read()` about 52.4 million times (one call per byte, plus EOF). Each call incurs Java/native method and stream overhead; depending on buffering in the OS, it need not equal a physical disk operation, but the repeated call overhead is still costly.

```java
try (BufferedInputStream in = new BufferedInputStream(
        Files.newInputStream(path), 64 * 1024)) {
    int b; long lines = 0;
    while ((b = in.read()) != -1) if (b == '\n') lines++;
}
```

A more explicit byte-array loop reads up to 64 KiB per call and scans that array, reducing read calls from O(file size) to approximately `ceil(fileSize / bufferSize)`. For 50 MiB and 64 KiB this is about 800 buffer fills rather than 52 million one-byte reads. A buffer offers little benefit when data is already in memory, the source itself returns large blocks efficiently, or the bottleneck is network latency/processing rather than small reads.

**Alternative `Files.readAllLines`:** time O(N) and memory O(N + number of line objects), where N is file size; for a 50 MiB log, decoded strings and object overhead can consume substantially more than 50 MiB. Buffered streaming is O(N) time and O(B) extra memory for buffer size B; it is preferred for large logs and bounded-memory processing. Read-all is convenient for small files requiring random access to lines.
