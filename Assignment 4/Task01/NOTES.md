# Q1 – Diagnose and repair fee-payment event handling

Deficiencies in the supplied design:
1. One listener contains UI, validation, JDBC and payment logic; it is hard to test and maintain.
2. `==` compares String references, not command contents; use `equals` or separate listeners.
3. `Double.parseDouble` can throw for blank/non-numeric text and no positive/finite amount check exists.
4. JDBC runs on the event-dispatch thread; the window freezes during slow I/O.
5. `Thread.sleep(3000)` blocks the event-dispatch thread and does not simulate a safe bank confirmation.
6. SQL is assembled by concatenation, allowing SQL injection and quoting errors.
7. Connection/statement/result resources are not closed.
8. Checked `SQLException` and `InterruptedException` are not handled.
9. Receipt button has no receipt-specific behavior; the `else` branch clears fields.
10. No useful validation/error feedback or close confirmation is provided.

`FeeWindow` collects input and displays status; `FeeService` validates and performs parameterized persistence. `SwingWorker` performs database work off the event-dispatch thread and updates Swing components in `done()`. The database URL/user/password are configurable using `DB_URL`, `DB_USER`, `DB_PASSWORD`. The SQL script creates the required table. Add the MySQL Connector/J driver to the runtime classpath to connect to MySQL.

Alternative: separate listener objects or `Action` objects avoid a k-comparison if/else chain and can be O(1) dispatch by direct registration; k listener objects use O(k) additional space. Actual dispatch also includes event queue and listener invocation overhead.
