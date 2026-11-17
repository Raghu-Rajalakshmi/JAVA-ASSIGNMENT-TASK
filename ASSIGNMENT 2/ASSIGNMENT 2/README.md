# CSA09-YourRegisterNumber-Assignment2

Student name: Raghu Rajalakshmi  
Register number: 192524350 
Java version: Java 17 

## Task folders
Task01 Strings benchmark; Task02 List/Deque benchmark; Task03 hashCode/equals; Task04 Map benchmark; Task05 generic leaderboard; Task06 PriorityQueue triage; Task07 redesign benchmark.

## Compile and run (from repository root)
```bash
javac Task01/StringBuildingBenchmark.java && java -cp Task01 StringBuildingBenchmark
javac Task02/ListStructureBenchmark.java && java -cp Task02 ListStructureBenchmark
javac Task03/StudentHashSetDemo.java && java -cp Task03 StudentHashSetDemo
javac Task04/PharmacyMapBenchmark.java && java -cp Task04 PharmacyMapBenchmark
javac Task05/LeaderboardDemo.java && java -cp Task05 LeaderboardDemo
javac Task06/Triage.java && java -cp Task06 Triage
javac Task07/RedesignStringBuilder.java && java -cp Task07 RedesignStringBuilder
```
Expected fixed outputs:
- Q3 corrected set: `Unique students: 2`
- Q5: `[Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)]`
- Q6: `Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) John(S3) Asha(S5)`

Benchmark timings depend on hardware/JVM. Run them yourself, save raw results in `results/`, and do not report fabricated numbers. Add genuine output screenshots to each Task folder. These are teaching microbenchmarks, not JMH-grade measurements.

## GitHub workflow
```bash
git init
git add README.md Task01 && git commit -m "Complete Task 1"
git add Task02 && git commit -m "Complete Task 2"
git add Task03 && git commit -m "Complete Task 3"
git add Task04 && git commit -m "Complete Task 4"
git add Task05 && git commit -m "Complete Task 5"
git add Task06 && git commit -m "Complete Task 6"
git add Task07 results && git commit -m "Complete Task 7 redesign"
git branch -M main
git remote add origin https://github.com/Raghu-Rajalakshmi/CSA09-YOURREGISTERNUMBER-Assignment2.git
git push -u origin main
```
