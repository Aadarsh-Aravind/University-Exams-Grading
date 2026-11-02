package model;

public class FinalExam extends Assessment {
    public FinalExam(String name, Course course, double weightage, double maxMarks) {
        super(name, course, weightage, maxMarks);
    }

    @Override
    public String getType() {
        return "Final Exam";
    }
}
