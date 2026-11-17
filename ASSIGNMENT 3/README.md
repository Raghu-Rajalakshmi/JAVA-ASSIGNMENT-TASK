# CSA09-YourRegisterNumber-Assignment3

**Student name:** Raghu Rajalakshmi
**Register number:** 192524350 
**Partner name/register number:** no partner
**Java version:** Java 17 

This repository contains the Java source for all seven questions in Programming in Java – Assignment 3 (CO3).

## Contents
- `Task01`: ATM exception handling, checked/unchecked exceptions and finally
- `Task02`: user-defined exceptions, propagation and exception chaining
- `Task03`: manual resource closing versus try-with-resources
- `Task04`: Thread subclass and Runnable scanner stations
- `Task05`: cinema seat-booking race condition and synchronized solution
- `Task06`: bounded producer-consumer counter and deadlock-safe wallet transfer
- `Task07`: ExecutorService/Callable/Future result processor

## Compile and run
Run commands from the repository root:
```bash
javac Task01/ATM.java && java -cp Task01 ATM 12345 100
javac Task02/StorageDemo.java && java -cp Task02 StorageDemo
javac Task03/SalesReport.java && java -cp Task03 SalesReport
javac Task04/ScannerStations.java && java -cp Task04 ScannerStations
javac Task05/SeatBooking.java && java -cp Task05 SeatBooking
javac Task06/TokenCounter.java && java -cp Task06 TokenCounter
javac Task07/ConcurrentResultProcessor.java && java -cp Task07 ConcurrentResultProcessor
```

## Expected key results
- Q2 prints `Saved answer01.pdf`, then a storage error with cause `Disk quota exceeded`.
- Q3 prints `Reports equal: true`.
- Q5 prints `Seats sold: 10`.
- Q6 prints `Sum of tokens collected: 210 (expected 210)`.
- Q7 reports eight valid scores and one corrupt input for each pool size. Timing varies by machine.

## Evidence to add before submission
1. Execute every program on your own computer and capture terminal screenshots.
2. Add test tables, stack traces, debugger observations, and raw timing data required by the assignment.
3. For Q4, run at least ten times and record observed completion orders and priority/daemon experiments.
4. For Q5, record ten-run results for both unsafe and synchronized variants. The included synchronized version is the final safe implementation.
5. For Q7, add the missing-file case using an expected-input manifest if you need to count files that do not exist.

## Progressive GitHub commits
```bash
git init
git add README.md Task01 && git commit -m "Complete Q1 ATM exception handling"
git add Task02 && git commit -m "Complete Q2 exception propagation"
git add Task03 && git commit -m "Complete Q3 resource safety"
git add Task04 && git commit -m "Complete Q4 thread lifecycle"
git add Task05 && git commit -m "Complete Q5 synchronized seat booking"
git add Task06 && git commit -m "Complete Q6 deadlock and producer consumer"
git add Task07 && git commit -m "Complete Q7 concurrent result processor"
git branch -M main
git remote add origin https://github.com/Raghu-Rajalakshmi/JAVA-ASSIGNMENT-TASK.git
git push -u origin main
```

