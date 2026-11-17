# A5 – Design for Correctness and Safety

## A5.1 Transfer transaction pseudocode

```text
ALGORITHM Transfer(hostelId, canteenId, amount)
BEGIN
    IF amount <= 0 THEN RETURN INVALID_AMOUNT
    OPEN JDBC CONNECTION
    SET autoCommit FALSE
    TRY
        UPDATE wallet SET balance = balance - amount
          WHERE wallet_id = hostelId AND balance >= amount
        IF affected rows != 1 THEN RAISE INSUFFICIENT_FUNDS
        UPDATE wallet SET balance = balance + amount WHERE wallet_id = canteenId
        IF affected rows != 1 THEN RAISE UNKNOWN_DESTINATION
        COMMIT
    CATCH ANY ERROR
        ROLLBACK
        REPORT failure
    FINALLY
        RESTORE autoCommit and CLOSE connection
END
```

Use row-count checks and database constraints; transaction isolation/locking protects concurrent transfers. Two auto-commit updates can leave the debit committed without the credit if failure occurs between them, destroying money. A transaction adds begin/commit/rollback and locking/logging overhead, but preserves atomicity. Both approaches perform two updates (O(1) statements); transaction correctness is the decisive difference.

## A5.2 SQL injection

Unsafe query: `SELECT * FROM users WHERE id='` + id + `' AND pwd='` + pwd + `'`. An input such as username `admin' -- ` can comment out the password predicate (exact comment syntax depends on database); this can authenticate without a valid password. The safe design is a parameterized query:

```java
String sql = "SELECT 1 FROM users WHERE id = ? AND pwd_hash = ?";
try (PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setString(1, id);
    ps.setString(2, passwordHash);
    try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
}
```

In real systems use a salted password-hashing algorithm (Argon2id/bcrypt/scrypt/PBKDF2), not plaintext or a fast unsalted hash. PreparedStatement separates SQL structure from values; input is data even if it contains quotes or SQL syntax.

Escaping quotes or regex validation is weaker because SQL dialects, encodings, edge cases and future query changes can bypass hand-written filters. Regex adds O(m) validation for input length m per request, but does not provide a reliable SQL/code boundary. PreparedStatement also binds O(m) data but delegates correct value handling to the driver/database.
