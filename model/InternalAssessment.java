package model;

public class InternalAssessment extends Assessment {
    public InternalAssessment(String name, Course course, double weightage, double maxMarks) {
        super(name, course, weightage, maxMarks);
    }

    @Override
    public String getType() {
        return "Internal";
    }
}
