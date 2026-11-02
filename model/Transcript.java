package model;

import java.util.List;

public class Transcript {
    private Student student;
    private List<Grade> grades;

    public Transcript(Student student, List<Grade> grades) {
        this.student = student;
        this.grades = grades;
    }

    // GPA for one term = sum(points x credits) / sum(credits)
    private double gpa(int term) {
        double weighted = 0;
        int credits = 0;
        for (Grade g : grades) {
            if (term == 0 || g.getCourse().getTerm() == term) {
                weighted += g.getPoints() * g.getCourse().getCredits();
                credits += g.getCourse().getCredits();
            }
        }
        return credits == 0 ? 0 : weighted / credits;
    }

    public void print() {
        System.out.println("\n========== TRANSCRIPT ==========");
        System.out.println("Student : " + student.getName() + " (" + student.getId() + ")");
        for (int term = 1; term <= 8; term++) {
            boolean found = false;
            for (Grade g : grades) {
                if (g.getCourse().getTerm() == term) {
                    if (!found) {
                        System.out.println("\n--- Term " + term + " ---");
                        System.out.printf("%-8s %-22s %-7s %-6s %-6s%n",
                                "Code", "Course", "Credits", "Grade", "Points");
                        found = true;
                    }
                    System.out.printf("%-8s %-22s %-7d %-6s %-6d%n",
                            g.getCourse().getCode(), g.getCourse().getName(),
                            g.getCourse().getCredits(), g.getLetter(), g.getPoints());
                }
            }
            if (found) {
                System.out.printf("Term %d GPA : %.2f%n", term, gpa(term));
            }
        }
        System.out.printf("%nCGPA : %.2f%n", gpa(0));
        System.out.println("================================");
    }
}
