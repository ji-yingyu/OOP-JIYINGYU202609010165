# Object-Oriented Programming Fundamentals in Java

Assignment 1 - Individual Portfolio (20%)

| Student / course detail | Value |
| --- | --- |
| Student name | JIYINGYU |
| Student ID | 202609010165 |
| Course | Object Oriented Programming (Java) |
| Course code | BISE2093 |
| Class code | 202609F1376 |
| Institution | City University Malaysia, Cyberjaya Campus |
| Lecturer | Sir Nazmirul Izzad bin Nassir |
| Submission deadline | 27 November 2026 |

Repository: [OOP-JIYINGYU202609010165](https://github.com/ji-yingyu/OOP-JIYINGYU202609010165)

## Course description

This portfolio covers Java syntax, classes and objects, constructors, encapsulation,
inheritance, polymorphism, abstraction, collections, file handling and Swing event
handling. Each tutorial is an independent project that can be compiled with the JDK
without Maven, Gradle or external Java libraries.

The lecturer's compressed lab index groups **10 teaching weeks into 7 tutorials**.
This repository follows that index and uses `tutorial_1` to `tutorial_7` consistently.
Week 2 is included under Tutorial 1.

## Repository structure

```text
OOP-JIYINGYU202609010165/
|-- README.md
|-- myreport.pdf                 # Assignment 1 self-reflective report
|-- tutorial_1/                  # Weeks 1-2: HelloWorld, grades, Student objects
|   `-- week_2/                  # Student constructor and methods
|-- tutorial_2/                  # Weeks 3-4: Person, Student, Lecturer
|   `-- classroom_car/           # Extra Car example from classroom photographs
|-- tutorial_3/                  # Week 5: encapsulation and written answers
|-- tutorial_4/                  # Week 6: Employee/Lecturer inheritance
|-- tutorial_5/                  # Week 7: abstract Appliance and four subclasses
|-- tutorial_6/                  # Weeks 8-9: persistent to-do list
|-- tutorial_7/                  # Week 10: Programming Quiz Battle (Swing)
|-- docs/                        # Verification records and Swing quiz screenshots
|-- scripts/                     # Repeatable verification
|-- tests/                       # Behavioral and GUI workflow checks
|-- Employee.java                # Existing Assignment 2 payroll files retained at root
|-- FullTimeEmployee.java
|-- PartTimeEmployee.java
|-- PayrollSystem.java
`-- LICENSE
```

## Tutorial summary: Weeks 1-10

| Week(s) | Folder | Work completed | Main concepts |
| --- | --- | --- | --- |
| 1 | `tutorial_1` | HelloWorld, example StudentGrade program, corrected original starter | Main method, output, variables, selection, compilation |
| 2 | `tutorial_1/week_2` | Student constructor, displayInfo, study and takeExam | Objects, attributes, constructors and methods |
| 3-4 | `tutorial_2` | Person, Student and Lecturer introductions through Person references | Inheritance, private state, super, overriding and runtime polymorphism |
| 5 | `tutorial_3` | Student ID, name, CGPA, programme; answers in documentation.txt | Encapsulation, getters/setters and validation |
| 6 | `tutorial_4` | Employee and Lecturer with subject and added department | Inherited state, constructor chaining and code reuse |
| 7 | `tutorial_5` | WashingMachine, Refrigerator, AirConditioner and Microwave | Abstract class, common operations and subtype behavior |
| 8-9 | `tutorial_6` | Add three tasks, display, save to task.txt, automatically load earlier tasks | ArrayList, Scanner, UTF-8 file I/O and exceptions |
| 10 | `tutorial_7` | Five-question Swing quiz with feedback, score, progression and restart | JFrame, JButton, ActionListener and the event dispatch thread |

The classroom Car exercise is an additional reconstruction from the supplied
photographs. The lab's Week 3-4 Person exercise is also included. Sample people,
ages, grades and employee details inside demonstrations are test data.

## Technologies used

- Java SE / JDK 8 or later; compilation verified with `javac 1.8.0_101`.
- Java standard libraries: Swing/AWT, collections, I/O and NIO.
- Git and GitHub; the existing repository and MIT license are retained.
- Python 3 for optional verification.
- OpenAI Codex assisted with completing tutorial code, written answers, testing,
  documentation, and drafting the reflective report. The automated checks record
  the assistant's test runs; they are not an attestation by the student.

## How to run the projects

Install a JDK and ensure `java` and `javac` are on PATH, or set `JAVA_HOME`.
Run each project from its own source folder. Several independent tutorials use
the same class names; do not compile every Java file in the repository together.

```text
# Week 1
cd tutorial_1
javac HelloWorld.java StudentGrade.java tutorial_1.java
java HelloWorld
java StudentGrade
java StudentGrade 75

# Week 2 (start from the repository root)
cd tutorial_1/week_2
javac Main.java Student.java
java Main
```

For Tutorials 2-6, enter the relevant `tutorial_n` directory and run:

```text
javac *.java
java Main
```

Tutorial 6 asks for three non-empty tasks. It loads `task.txt` from the current
working directory before adding new tasks and saves the combined list. The first
run creates the file. Blank tasks are retried; incomplete input does not save a
partial update. Runtime task data are excluded from Git. An optional first command
line argument selects another task file: `java Main my_tasks.txt`.

For the photographed Car example:

```text
cd tutorial_2/classroom_car
javac Car.java Main.java
java Main
```

For the Week 10 GUI, use a local desktop with a display:

```text
cd tutorial_7
javac Questions.java QuizBattleGUI.java
java QuizBattleGUI
```

Choose an answer. An incorrect answer allows another attempt; a correct answer
adds one point and enables the next question. Use Finish after the last question,
or Restart to reset the quiz. A terminal-only Codespace cannot directly display
a Swing window; clone the repository to a desktop for this tutorial.

The existing Assignment 2 payroll project remains available from the repository root:

```text
javac Employee.java FullTimeEmployee.java PartTimeEmployee.java PayrollSystem.java
java PayrollSystem
```

### Verification

From the repository root, with Python 3 and a JDK available:

```text
python scripts/verify.py
python scripts/verify.py --java-home "path/to/your/jdk"
```

The verifier compiles independent projects into separate `build/` directories,
checks expected console output, CGPA and grade boundaries, persistence across
two runs, incomplete input, I/O errors, and Swing button workflows. It also checks
that the existing payroll program starts and exits. The GUI check needs a display.
The verification record and GUI images are in [docs/verification.txt](docs/verification.txt)
and [docs/evidence](docs/evidence). `build/` is generated and ignored.

## Reflection summary

The portfolio progresses from a working entry point to objects with state,
controlled access to data, reusable class hierarchies, persistent collections
and event-driven interaction. Important lessons include matching Java identifiers
exactly, distinguishing inheritance from overriding, validating state before
assignment, preserving saved data, and checking complete user workflows.

The [individual reflective report](myreport.pdf) discusses the work completed
across all ten teaching weeks, the design decisions, and future learning plans.
The signed AI declaration is submitted separately via Moodle.
