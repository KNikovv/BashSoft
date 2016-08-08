package main.bg.softuni.models.interfaces;

import java.util.Map;

public interface Course extends Comparable<Course> {

    String getName();

    void enrollStudent(Student student);

    Map<String, Student> getStudentsByName();
}
