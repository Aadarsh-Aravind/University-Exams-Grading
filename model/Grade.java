package model;

public class Grade {
    private Student student;
    private Course course;
    private double totalMarks;
    private String letter;
    private int points;

    public Grade(Student student, Course course, double totalMarks) {
        this.student = student;
        this.course = course;
        this.totalMarks = totalMarks;
        applyPolicy();
    }

    // Grading policy: total marks -> letter grade and grade points
    private void applyPolicy() {
        if (totalMarks >= 90) { letter = "O";  points = 10; }
        else if (totalMarks >= 80) { letter = "A+"; points = 9; }
        else if (totalMarks >= 70) { letter = "A";  points = 8; }
        else if (totalMarks >= 60) { letter = "B";  points = 7; }
        else if (totalMarks >= 50) { letter = "C";  points = 6; }
        else { letter = "F"; points = 0; }
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public double getTotalMarks() { return totalMarks; }
    public String getLetter() { return letter; }
    public int getPoints() { return points; }
}
