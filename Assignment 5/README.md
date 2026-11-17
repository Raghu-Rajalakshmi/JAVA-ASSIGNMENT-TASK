# CSA09-YourRegisterNumber-Assignment5

**Course:** Programming in Java (CSA09)  
**Assignment:** 5 – CO5, Data Persistence Engineering  
**Student name:** YOUR NAME  
**Register number:** YOUR REGISTER NUMBER  
**Java:** JDK 17 or later

## Repository structure

- `Task01-File-Format-Design`: delimited/binary record layouts, operations, recovery, complexity.
- `Task02-Buffered-IO-Design`: byte-at-a-time cost, buffered redesign, read-all comparison.
- `Task03-DAO-Pattern`: generic DAO pattern and object/table mapping.
- `Task04-Serialization-Design`: transient fields, object graph and versioning.
- `Task05-Transactions-and-Injection`: transfer transaction and SQL injection analysis.
- `Task06-Instrumented-File-Store`: `PassStore`, completed TODO, line counter and find benchmark.
- `Task07-Adversarial-Inputs`: unsafe/safe login comparison and SQL setup.
- `Task08-JDBC-Batch-Engineering`: completed `PassLoader`, schema and JDBC benchmark.
- `Task09-Migration-and-Decision`: migration tool, metadata inspection and production decision.

## Compile and run file tasks

Run from the repository root:
```bash
javac Task06-Instrumented-File-Store/PassStore.java
java -cp Task06-Instrumented-File-Store PassStore

javac Task06-Instrumented-File-Store/LineCounter.java
java -cp Task06-Instrumented-File-Store LineCounter path/to/log.txt

javac -cp Task06-Instrumented-File-Store Task06-Instrumented-File-Store/BenchmarkPassFind.java
java -cp Task06-Instrumented-File-Store BenchmarkPassFind
```

## MySQL tasks (7–9)

1. Install MySQL and download MySQL Connector/J.
2. Run the relevant `schema.sql` file in MySQL.
3. Set `DB_URL`, `DB_USER`, `DB_PASSWORD`.
4. Compile/run with Connector/J in classpath. Example (Linux/macOS):
```bash
javac -cp /path/mysql-connector-j.jar:Task08-JDBC-Batch-Engineering \
  Task08-JDBC-Batch-Engineering/PassLoader.java \
  Task08-JDBC-Batch-Engineering/BusPassJdbcBenchmark.java
java -cp /path/mysql-connector-j.jar:Task08-JDBC-Batch-Engineering BusPassJdbcBenchmark
```
Use `;` rather than `:` on Windows. `LoginDemo` is in Task07 and `MigrationTool` is in Task09; include `Task08-JDBC-Batch-Engineering` for `PassLoader` when compiling/running MigrationTool.

## Evidence and submission checklist

- Execute every task yourself; add screenshots of terminal output and database results.
- For B1, measure 1 MB, 10 MB, 50 MB line counts and 1k/10k/100k find times.
- For B3, record timings for 1k/10k/100k rows and test failure rollback.
- Add your machine, OS, JDK, MySQL and Connector/J versions to `measurements.csv`.
- Do not submit fabricated measurements or screenshots. The repository includes test harnesses; actual measurements must be generated on your system.
- Add the GitHub repository link to your report and share it with faculty.

## Suggested progressive commits

```bash
git init
git add Task01-File-Format-Design Task02-Buffered-IO-Design && git commit -m "Complete A1 and A2 persistence design"
git add Task03-DAO-Pattern Task04-Serialization-Design && git commit -m "Complete A3 and A4 design"
git add Task05-Transactions-and-Injection && git commit -m "Complete A5 transaction and injection design"
git add Task06-Instrumented-File-Store && git commit -m "Implement B1 file store and measurements"
git add Task07-Adversarial-Inputs && git commit -m "Implement B2 adversarial login tests"
git add Task08-JDBC-Batch-Engineering && git commit -m "Implement B3 JDBC batch loader"
git add Task09-Migration-and-Decision README.md && git commit -m "Complete B4 migration and engineering decision"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/CSA09-YOURREGISTERNUMBER-Assignment5.git
git push -u origin main
```

Replace placeholders with your details. Add one commit per task as required.
