# Q2 – User-defined exceptions and propagation

Expected output:
```text
Saved answer01.pdf
Error: Could not store answer02.pdf
Cause: Disk quota exceeded
```

Exception hierarchy: `Exception -> ExamException -> SubmissionStorageException`. The storage layer translates the implementation-specific `IOException` into a domain-specific exception and keeps the original cause. If the cause is omitted, `getCause()` returns `null`, so the root cause message and original stack trace are lost.

Propagation: storage throws `SubmissionStorageException`; service/controller methods can declare `throws ExamException`; the controller/UI catches the domain exception and displays a safe message. Checked exceptions force callers to handle or declare them. If changed to `RuntimeException`, `throws` declarations are optional and callers are not compiler-forced to handle them.

Exception creation captures a stack trace and allocates an object; this has a cost proportional to stack depth. Chaining retains a reference to the cause and its trace, increasing retained memory by the reachable exception chain.
