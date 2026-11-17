# CSA09-YourRegisterNumber-Assignment1

**Student name:** YOUR NAME  
**Register number:** YOUR REGISTER NUMBER  
**Java version:** Java 17 or later

## Project structure
- `Task01`–`Task12`: task evidence and task-specific programs
- `src/parking/model`: Vehicle hierarchy and ParkingTicket
- `src/parking/service`: facility, pass and pricing abstractions
- `src/parking/app`: runnable demonstration
- `Task03/ParkingFacilityComplete.java`: completed supplied allocation/release program
- `Task08/FeeDemo.java`: completed supplied polymorphism program

## Compile and run (integrated system)
From the repository root:

```bash
javac -d out src/parking/model/*.java src/parking/service/*.java src/parking/app/*.java
java -cp out parking.app.ParkingApp
```

## Run the supplied Task 3 program
```bash
javac Task03/ParkingFacilityComplete.java
java -cp Task03 ParkingFacilityComplete
```

Expected output:
```text
[0, 0]
[0, 1]
[1, 0]
null
true false false
[0, 1]
```

## Run the supplied Task 8 program
```bash
javac Task08/FeeDemo.java
java -cp Task08 FeeDemo
```

Expected output:
```text
TwoWheeler TN01A1 -> Rs 30.0
Car TN02B2 -> Rs 90.0
ElectricCar TN03C3 -> Rs 210.0
true
```

## Notes
- The floor layout is a jagged array, so every floor may have a different number of slots.
- Active tickets are indexed by registration number in a `HashMap`.
- `Vehicle` is abstract; vehicle types override their hourly/fee behaviour.
- `Pass` and `PricingRule` are interfaces, allowing new pass and pricing policies.
- The delivery-van example interprets the premium window as billed hours beginning from the entry hour. A production system should calculate exact overlap minutes with 09:00–17:00.
- Update the student details and add your own execution screenshots before submission.
- Commit each task progressively; do not make one final bulk commit only.
