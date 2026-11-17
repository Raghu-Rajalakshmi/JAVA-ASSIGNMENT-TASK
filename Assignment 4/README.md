# CSA09-YourRegisterNumber-Assignment4

**Course:** Programming in Java (CSA09)  
**Student name:** YOUR NAME  
**Register number:** YOUR REGISTER NUMBER  
**Java version:** Java 17+  
**Assignment:** CO4 – Event-Driven GUI Design

## Task folders
- Task01: Fee-payment design diagnosis, FeeWindow and FeeService
- Task02: Hospital appointment design and working Swing prototype
- Task03: Library search screen and responsive nested layouts
- Task04: Completed DrawPad TODOs; click count and repaint persistence
- Task05: Four currency-converter event-handling styles
- Task06: Layered Swing/JDBC event registration application and MySQL schema

## Compile and run
Run commands from the repository root. Java source is in the default package:
```bash
javac Task01/FeeService.java Task01/FeeWindow.java
java -cp Task01 FeeWindow

javac Task02/AppointmentFrame.java
java -cp Task02 AppointmentFrame

javac Task03/LibrarySearchScreen.java
java -cp Task03 LibrarySearchScreen

javac Task04/DrawPad.java
java -cp Task04 DrawPad

javac Task05/ConverterFrameListener.java Task05/ConverterNamedInner.java Task05/ConverterAnonymous.java Task05/ConverterLambda.java
java -cp Task05 ConverterFrameListener
java -cp Task05 ConverterNamedInner
java -cp Task05 ConverterAnonymous
java -cp Task05 ConverterLambda

javac Task06/RegistrationApp.java
java -cp Task06:PATH_TO_MYSQL_CONNECTOR_JAR RegistrationApp
```
On Windows, use `;` instead of `:` in the Java classpath. Task01 and Task06 require MySQL Connector/J at runtime and a configured database. See each task's NOTES.md.

## Evidence checklist
- Task03: screenshots at three window sizes.
- Task04: screenshot before and after minimize/restore; compiler error from misspelled `mouseclicked` with `@Override`.
- Task05: valid and invalid conversion screenshots; compare changes across all four styles.
- Task06: normal, invalid and duplicate registration screenshots; include SQL execution and database setup.
- Add your own name, register number, report details, and actual screenshots before submission.

## Progressive GitHub commits
```bash
git init
git add README.md Task01 && git commit -m "Complete Q1 fee payment design"
git add Task02 && git commit -m "Complete Q2 appointment desk"
git add Task03 && git commit -m "Complete Q3 responsive library layout"
git add Task04 && git commit -m "Complete Q4 drawing pad event handling"
git add Task05 && git commit -m "Complete Q5 event handling styles"
git add Task06 && git commit -m "Complete Q6 event registration application"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/CSA09-YOURREGISTERNUMBER-Assignment4.git
git push -u origin main
```

Do not claim screenshots, MySQL tests or performance measurements that you have not personally run. Each student must execute the tasks and progressively commit their work.
