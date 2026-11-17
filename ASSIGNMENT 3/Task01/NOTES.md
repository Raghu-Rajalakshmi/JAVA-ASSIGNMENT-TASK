# Q1 – ATM exception handling

The program demonstrates unchecked validation/funds exceptions, a checked `IOException`, multi-catch, catch ordering, and `finally`. `finally` runs when control leaves the try/catch normally or exceptionally (except abrupt JVM termination or similar process failure). The narrower `InvalidAmountException` and `InsufficientFundsException` are caught before any broad `Exception` catch.

Relevant hierarchy:
```text
Throwable
├── Error
└── Exception
    ├── RuntimeException (unchecked)
    │   ├── IllegalArgumentException
    │   │   └── InvalidAmountException
    │   └── InsufficientFundsException
    └── IOException (checked)
```

Try the program with `java -cp Task01 ATM 12345 100`, `... 12345 -5`, and `... 12345 5000`. To demonstrate missing card file, temporarily rename `card-reader.txt` before running. `Scanner.nextInt()` with text input follows the input-mismatch path. The sample main recreates the card file at startup, so for the missing-file test call `withdraw` from a small test harness or temporarily comment out the file-creation lines.

Error/code classification and catch location:
| Failure | Type | Checked? | Handling |
|---|---|---:|---|
| Text where number expected | InputMismatchException | No | Input catch |
| Negative/zero amount or invalid account | InvalidAmountException | No | Multi-catch |
| Amount above balance | InsufficientFundsException | No | Multi-catch |
| Missing/unreadable card file | IOException | Yes | IOException catch |
| JVM memory/VM failure | Error | No | Not caught |

Avoid catching every failure as `Exception` unless at a top-level process boundary for logging/cleanup; doing so loses precise recovery and can hide programming defects. Without exceptions, validate tokens with `Scanner.hasNextInt/hasNextDouble`, check business rules, and return status codes. Validation is generally linear in number of inputs; exception construction/throwing adds stack-trace capture overhead and allocation, while ordinary valid-path checks are cheaper.
