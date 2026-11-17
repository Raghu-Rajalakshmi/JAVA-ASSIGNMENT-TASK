# B1 – Implementation and experiment plan

`PassStore` implements add/find/list, validates exactly seven fields and skips damaged lines. The included sample deliberately inserts a malformed P1002 row; searching P1003 continues past it and succeeds. Searching P1002 returns null. The parser supports escaped pipe/backslash characters.

`LineCounter` reports elapsed time for unbuffered and 64 KiB buffered counting. Create test files of 1 MiB, 10 MiB and 50 MiB with `yes 'sample line' | head -c SIZE > sample.txt` (or generate with Java/Python); run `java LineCounter sample.txt`. `BenchmarkPassFind` measures last-record lookup at 1k, 10k and 100k records. Record actual results and machine/JDK details in `measurements.csv`; do not invent timings.

Expected growth: file scan find is O(n), while streaming line count is O(N bytes). Buffering changes read-call count from roughly N to ceil(N/B), where B is buffer size; both remain O(N) time. A HashMap loaded once costs O(n) startup and O(n) memory, then expected O(1) per query; q searches cost expected O(n+q), versus O(qn) repeated scans.
