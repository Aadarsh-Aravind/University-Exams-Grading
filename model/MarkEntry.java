package model;

public class MarkEntry {
    private Student student;
    private Assessment assessment;
    private double marks;
    private int resitCount;

    public MarkEntry(Student student, Assessment assessment, double marks) {
        this.student = student;
        this.assessment = assessment;
        this.marks = marks;
    }

    public void update(double newMarks) {
        this.marks = newMarks;
    }

    public void addResit() {
        resitCount++;
    }

    public Student getStudent() { return student; }
    public Assessment getAssessment() { return assessment; }
    public double getMarks() { return marks; }
    public int getResitCount() { return resitCount; }
}
