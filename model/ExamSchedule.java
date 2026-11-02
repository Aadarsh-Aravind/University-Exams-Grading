package model;

import java.time.LocalDate;

public class ExamSchedule {
    private Assessment assessment;
    private LocalDate date;
    private String venue;

    public ExamSchedule(Assessment assessment, LocalDate date, String venue) {
        this.assessment = assessment;
        this.date = date;
        this.venue = venue;
    }

    public Assessment getAssessment() { return assessment; }
    public LocalDate getDate() { return date; }
    public String getVenue() { return venue; }
}
