package service;

import exception.ExamException;
import exception.NotRegisteredException;
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ExamService {
    private List<Course> courses = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private List<Assessment> assessments = new ArrayList<>();
    private List<ExamSchedule> schedules = new ArrayList<>();
    private List<MarkEntry> entries = new ArrayList<>();
    private List<Grade> grades = new ArrayList<>();

    // ---------- finders ----------
    private Course findCourse(String code) throws ExamException {
        for (Course c : courses) {
            if (c.getCode().equalsIgnoreCase(code)) return c;
        }
        throw new ExamException("Course not found: " + code);
    }

    private Student findStudent(String id) throws ExamException {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) return s;
        }
        throw new ExamException("Student not found: " + id);
    }

    private Assessment findAssessment(Course course, String name) throws ExamException {
        for (Assessment a : assessments) {
            if (a.getCourse() == course && a.getName().equalsIgnoreCase(name)) return a;
        }
        throw new ExamException("Assessment '" + name + "' not found in " + course.getCode());
    }

    private MarkEntry findEntry(Student s, Assessment a) {
        for (MarkEntry m : entries) {
            if (m.getStudent() == s && m.getAssessment() == a) return m;
        }
        return null;
    }

    private Grade findGrade(Student s, Course c) {
        for (Grade g : grades) {
            if (g.getStudent() == s && g.getCourse() == c) return g;
        }
        return null;
    }

    // ---------- menu operations ----------
    public void addCourse(String code, String name, int credits, int term) throws ExamException {
        for (Course c : courses) {
            if (c.getCode().equalsIgnoreCase(code)) throw new ExamException("Course already exists.");
        }
        if (credits < 1 || credits > 6) throw new ExamException("Credits must be between 1 and 6.");
        if (term < 1 || term > 8) throw new ExamException("Term must be between 1 and 8.");
        courses.add(new Course(code, name, credits, term));
        System.out.println("Course added: " + code);
    }

    public void addStudent(String id, String name, String courseCodes) throws ExamException {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) throw new ExamException("Student already exists.");
        }
        Student student = new Student(id, name);
        for (String code : courseCodes.split(",")) {
            student.register(findCourse(code.trim()));
        }
        students.add(student);
        System.out.println("Student added: " + id + " (registered for " + student.getCourses().size() + " course(s))");
    }

    public void createAssessment(String courseCode, String name, int type, double weightage, double maxMarks)
            throws ExamException {
        Course course = findCourse(courseCode);
        for (Assessment a : assessments) {
            if (a.getCourse() == course && a.getName().equalsIgnoreCase(name))
                throw new ExamException("Assessment already exists for this course.");
        }
        if (weightage <= 0 || maxMarks <= 0) throw new ExamException("Weightage and max marks must be positive.");
        if (totalWeightage(course) + weightage > 100)
            throw new ExamException("Total weightage for " + courseCode + " cannot exceed 100.");
        Assessment a = (type == 1)
                ? new InternalAssessment(name, course, weightage, maxMarks)
                : new FinalExam(name, course, weightage, maxMarks);
        assessments.add(a);
        System.out.println(a.getType() + " assessment created. Total weightage now: " + totalWeightage(course));
    }

    private double totalWeightage(Course course) {
        double total = 0;
        for (Assessment a : assessments) {
            if (a.getCourse() == course) total += a.getWeightage();
        }
        return total;
    }

    public void scheduleExam(String courseCode, String assessmentName, String date, String venue)
            throws ExamException {
        Assessment a = findAssessment(findCourse(courseCode), assessmentName);
        LocalDate d;
        try {
            d = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new ExamException("Invalid date. Use format yyyy-MM-dd.");
        }
        schedules.add(new ExamSchedule(a, d, venue));
        System.out.println("Exam scheduled: " + a.getName() + " on " + d + " at " + venue);
    }

    public void recordMarks(String studentId, String courseCode, String assessmentName, double marks)
            throws ExamException {
        Student student = findStudent(studentId);
        Course course = findCourse(courseCode);
        if (!student.isRegistered(course)) throw new NotRegisteredException(studentId, courseCode);
        Assessment a = findAssessment(course, assessmentName);
        if (marks < 0 || marks > a.getMaxMarks())
            throw new ExamException("Marks must be between 0 and " + a.getMaxMarks());

        MarkEntry entry = findEntry(student, a);
        Grade published = findGrade(student, course);

        if (entry == null) {
            entries.add(new MarkEntry(student, a, marks));
            System.out.println("Marks recorded.");
        } else if (published == null) {
            entry.update(marks);
            System.out.println("Marks updated.");
        } else {
            // Resit / improvement rule: allowed only for grade C or F, and only once per assessment
            if (published.getPoints() > 6)
                throw new ExamException("Not eligible for resit: grade " + published.getLetter() + " is above C.");
            if (entry.getResitCount() >= 1)
                throw new ExamException("Not eligible: resit already taken for this assessment.");
            entry.update(marks);
            entry.addResit();
            grades.remove(published);
            System.out.println("Resit marks recorded. Publish grades again to update the record.");
        }
    }

    public void publishGrades(String courseCode) throws ExamException {
        Course course = findCourse(courseCode);
        if (totalWeightage(course) != 100)
            throw new ExamException("Total weightage must be 100 before publishing (now "
                    + totalWeightage(course) + ").");

        System.out.println("\n===== GRADE SHEET : " + course.getCode() + " - " + course.getName() + " =====");
        System.out.printf("%-8s %-15s %-8s %-6s %-6s%n", "ID", "Name", "Total", "Grade", "Points");
        for (Student s : students) {
            if (!s.isRegistered(course)) continue;
            double total = 0;
            boolean complete = true;
            for (Assessment a : assessments) {
                if (a.getCourse() != course) continue;
                MarkEntry m = findEntry(s, a);
                if (m == null) { complete = false; break; }
                total += m.getMarks() / a.getMaxMarks() * a.getWeightage();
            }
            if (!complete) {
                System.out.printf("%-8s %-15s %s%n", s.getId(), s.getName(), "marks pending");
                continue;
            }
            Grade old = findGrade(s, course);
            if (old != null) grades.remove(old);
            Grade g = new Grade(s, course, total);
            grades.add(g);
            System.out.printf("%-8s %-15s %-8.2f %-6s %-6d%n",
                    s.getId(), s.getName(), g.getTotalMarks(), g.getLetter(), g.getPoints());
        }
        printAnalytics(course);
    }

    private void printAnalytics(Course course) {
        System.out.println("\n--- Assessment Analytics : " + course.getCode() + " ---");
        System.out.printf("%-14s %-11s %-7s %-8s%n", "Assessment", "Type", "Count", "Average");
        for (Assessment a : assessments) {
            if (a.getCourse() != course) continue;
            int count = 0;
            double sum = 0;
            for (MarkEntry m : entries) {
                if (m.getAssessment() == a) {
                    count++;
                    sum += m.getMarks();
                }
            }
            double avg = count == 0 ? 0 : sum / count;
            System.out.printf("%-14s %-11s %-7d %.2f / %.0f%n",
                    a.getName(), a.getType(), count, avg, a.getMaxMarks());
        }
    }

    public void generateTranscript(String studentId) throws ExamException {
        Student student = findStudent(studentId);
        List<Grade> own = new ArrayList<>();
        for (Grade g : grades) {
            if (g.getStudent() == student) own.add(g);
        }
        if (own.isEmpty()) throw new ExamException("No published grades for this student yet.");
        new Transcript(student, own).print();
    }
}
