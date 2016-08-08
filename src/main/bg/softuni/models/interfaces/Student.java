package main.bg.softuni.models.interfaces;

import java.util.Map;

public interface Student extends Comparable<Student> {

    String getUserName();

    void enrollInCourse(Course course);

    Map<String, Double> getMarksByCourseName();

    void setMarkOnCourse(String courseName, int... scores);
}
