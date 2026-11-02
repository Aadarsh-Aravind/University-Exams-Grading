package model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String id;
    private String name;
    private List<Course> courses = new ArrayList<>(); // aggregation

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void register(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public boolean isRegistered(Course course) {
        return courses.contains(course);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Course> getCourses() { return courses; }
}
