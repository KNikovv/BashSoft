package main.bg.softuni.repository.interfaces;

import java.util.Map;

public interface DataFilter {

    void printFilteredStudents(
            Map<String, Double> studentsWithMarks,
            String filterType,
            Integer numberOfStudents);
}
