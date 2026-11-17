# Q6 – Campus Event Registration

## Requirements and testable rules
- Student can register with student ID, name and event.
- Empty fields are rejected with a visible message.
- The same student cannot register twice for the same event; database UNIQUE(student_id,event_name) is the final concurrency-safe guard.
- Committee can filter by event, view a page of results, and export the visible page to CSV.
- Database calls run in `SwingWorker.doInBackground`; Swing component updates happen in `done()` on the EDT.
- PreparedStatements are used for all user-controlled SQL values.

## Screen and event flow
`Registration JFrame -> form -> Register -> validate -> SwingWorker -> Service -> DAO -> MySQL -> status + refresh table`. Filter loads one page. File > Refresh reloads; Export CSV opens JFileChooser; Exit closes.

## Layers
`RegistrationApp (Swing UI) -> RegistrationService (validation/business rules) -> RegistrationDao (JDBC) -> MySQL`. The table is populated from a bounded page using `LIMIT` and `OFFSET`.

## Run
1. Execute `schema.sql` in MySQL.
2. Set environment variables `DB_URL`, `DB_USER`, `DB_PASSWORD`.
3. Add MySQL Connector/J jar to the classpath.
4. Compile/run:
   `javac Task06/RegistrationApp.java`
   `java -cp "Task06:PATH_TO_MYSQL_CONNECTOR_JAR" RegistrationApp` (Windows uses `;` in classpath).

## Test cases
- Normal: valid student ID/name and event -> successful registration appears in table.
- Invalid: empty ID or name -> UI rejects before JDBC.
- Duplicate: repeat same student ID and event -> UNIQUE constraint rejects second insert and UI displays error.
- Responsiveness: use a slow DB/network and verify window can still repaint/respond while worker runs.

## Alternative and complexity
Loading all N=100,000 rows transfers and stores O(N) rows in the client table model and has O(N) initial time; memory is O(N × average row size), potentially hundreds of MB with Swing object overhead. Paging with page size P loads O(P) rows per request and O(P) client table memory; total time to inspect all data across pages is still O(N), but initial display and EDT model update are bounded by P. `SwingWorker` prevents the EDT from blocking on network/database waits. OFFSET can become slower at deep pages; keyset pagination using `WHERE id < ? ORDER BY id DESC LIMIT ?` is a scalable alternative.
