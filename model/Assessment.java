package model;

public class Assessment {
    private String name;
    private Course course;
    private double weightage;
    private double maxMarks;

    public Assessment(String name, Course course, double weightage, double maxMarks) {
        this.name = name;
        this.course = course;
        this.weightage = weightage;
        this.maxMarks = maxMarks;
    }

    // overridden by subclasses (polymorphism)
    public String getType() {
        return "General";
    }

    public String getName() { return name; }
    public Course getCourse() { return course; }
    public double getWeightage() { return weightage; }
    public double getMaxMarks() { return maxMarks; }
}
