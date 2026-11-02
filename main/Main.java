package main;

import exception.ExamException;
import service.ExamService;
import util.InputHelper;

public class Main {
    public static void main(String[] args) {
        ExamService service = new ExamService();
        int choice = 0;

        while (choice != 8) {
            System.out.println("\n===== University Exams & Grading =====");
            System.out.println("1. Add Course");
            System.out.println("2. Add Student");
            System.out.println("3. Create Assessment");
            System.out.println("4. Schedule Exam");
            System.out.println("5. Record Marks");
            System.out.println("6. Publish Grades");
            System.out.println("7. Generate Transcript");
            System.out.println("8. Exit");
            choice = InputHelper.readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1:
                        service.addCourse(
                                InputHelper.readText("Course code: "),
                                InputHelper.readText("Course name: "),
                                InputHelper.readInt("Credits (1-6): "),
                                InputHelper.readInt("Term (1-8): "));
                        break;
                    case 2:
                        service.addStudent(
                                InputHelper.readText("Student ID: "),
                                InputHelper.readText("Student name: "),
                                InputHelper.readText("Course codes to register (comma separated): "));
                        break;
                    case 3:
                        service.createAssessment(
                                InputHelper.readText("Course code: "),
                                InputHelper.readText("Assessment name: "),
                                InputHelper.readInt("Type (1-Internal, 2-Final Exam): "),
                                InputHelper.readDouble("Weightage (%): "),
                                InputHelper.readDouble("Maximum marks: "));
                        break;
                    case 4:
                        service.scheduleExam(
                                InputHelper.readText("Course code: "),
                                InputHelper.readText("Assessment name: "),
                                InputHelper.readText("Date (yyyy-MM-dd): "),
                                InputHelper.readText("Venue: "));
                        break;
                    case 5:
                        service.recordMarks(
                                InputHelper.readText("Student ID: "),
                                InputHelper.readText("Course code: "),
                                InputHelper.readText("Assessment name: "),
                                InputHelper.readDouble("Marks: "));
                        break;
                    case 6:
                        service.publishGrades(InputHelper.readText("Course code: "));
                        break;
                    case 7:
                        service.generateTranscript(InputHelper.readText("Student ID: "));
                        break;
                    case 8:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Enter 1-8.");
                }
            } catch (ExamException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
