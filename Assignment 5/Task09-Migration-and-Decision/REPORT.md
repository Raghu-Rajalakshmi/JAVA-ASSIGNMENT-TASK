# B4 – Final Engineering Decision

The migration utility streams the source text file, validates each record, accumulates accepted records for the assignment-scale example, reports invalid line numbers/content, and calls the transactional 1,000-row batch loader. For production-scale files, feed validated rows to a bounded batch iterator rather than retaining every accepted row in memory; this gives O(b) working memory.

`DatabaseMetaData` reports database product/version, JDBC driver name/version and available tables. This detects environment differences during deployment (database product, driver mismatch, missing schema/table) and supports portable setup diagnostics.

**Decision:** use a relational database as the system of record across 12 campuses. It supports concurrent counters, transactions, constraints, indexes and indexed pass-ID/route searches. Text files remain useful as import/export/audit interchange and backup artifacts; Java serialization is unsuitable as a shared multi-campus store because it is Java-specific and offers weak concurrent update/query support.

**Alternative: one serialized file per campus.** Each campus could write locally and periodically synchronize/merge files. A search across all campuses must inspect 12 files (O(N) total records), or build a merged index. Storage is O(N) across all files plus duplicated metadata. Concurrent writes within a campus need file locks or single-writer queues; conflict resolution and duplicate pass IDs across campuses require extra coordination. A shared database with unique constraints, transactions and indexes is safer and easier to operate. Measurements should be used to validate latency/throughput on the deployment hardware; no timings are claimed by this report.
