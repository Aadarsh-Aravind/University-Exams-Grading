package model;

public class Course {
    private String code;
    private String name;
    private int credits;
    private int term;

    public Course(String code, String name, int credits, int term) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.term = term;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public int getTerm() { return term; }
}
