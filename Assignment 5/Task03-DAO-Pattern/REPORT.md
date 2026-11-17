# A3 – Discover the Data-Access Design Pattern

The repeated sequence is: obtain connection, prepare SQL, bind values, execute, map ResultSet rows to objects, close ResultSet/statement/connection, and translate SQL errors. Table/column names, SQL predicates, parameter mapping and object construction differ.

```java
public interface Dao<T, K> {
    K insert(T value) throws SQLException;
    Optional<T> findById(K id) throws SQLException;
    List<T> findAll() throws SQLException;
    boolean update(T value) throws SQLException;
    boolean delete(K id) throws SQLException;
}
```

`StudentDao implements Dao<Student, Long>`; it owns student-specific SQL and maps rows. Example model: `Student(id: BIGINT PRIMARY KEY, registerNo: VARCHAR(20) UNIQUE, name: VARCHAR(100), email: VARCHAR(120))`. Mapping: `id -> id` (primary key), `registerNo -> register_no`, `name -> name`, `email -> email`.

**Alternative: SQL in services/GUI.** A renamed table column may require edits in every caller; with DAO, normally one DAO mapping/query changes. If SQL is duplicated in k callers, change effort is O(k); DAO centralizes it to O(1) code locations. Runtime time and per-operation memory are effectively the same because both execute equivalent SQL and map equivalent rows; DAO adds a small abstraction/dispatch overhead, normally negligible. DAO improves maintainability/testability, not database query complexity.
