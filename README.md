University Exams & Grading System (Java OOP Mini-Project)

Course: 23CSR306 Java Programming
Assignment: II, Specification 32

Student: Aadarsh Aravind A T

Reg. No.: 717825F201

Class: II / IT / III / ___ (2026–2027)

GitHub: paste your repository link here

Problem Statement

A Java console application for a University Exams & Grading system that manages courses, students, assessments, exam schedules, marks, grades and transcripts using OOP principles.

Menu Options

Add Course / Add Student / Create Assessment / Schedule Exam / Record Marks / Publish Grades / Generate Transcript / Exit

Rules

- A student can be evaluated only for a registered course.
- Total marks are calculated from assessment weightages, which must add up to 100.
- Grades are derived from total marks using the grading policy below.
- The transcript shows grades with term GPA and CGPA.
- A resit is allowed only for grade C or F, once per assessment, and updates the record.

Grading Policy

- 90 and above: Grade O (10 points)
- 80 to 89: Grade A+ (9 points)
- 70 to 79: Grade A (8 points)
- 60 to 69: Grade B (7 points)
- 50 to 59: Grade C (6 points)
- Below 50: Grade F (0 points)

OOP Concepts Used

- Encapsulation: private fields with getters.
- Inheritance: InternalAssessment and FinalExam extend Assessment.
- Polymorphism: overridden getType() method.
- Aggregation: a Student has Courses; a MarkEntry has a Student and an Assessment.

Project Structure

- model: Course, Student, Assessment, InternalAssessment, FinalExam, ExamSchedule, MarkEntry, Grade, Transcript
- service: ExamService
- util: InputHelper
- exception: ExamException, NotRegisteredException
- main: Main

How to Compile and Run

1. Open Eclipse (JDK 8 or above) and create a new Java project.
2. Copy the folders model, service, util, exception and main into the project's src folder.
3. Run main.Main.
