package main.bg.softuni.repository;

import main.bg.softuni.exceptions.InvalidComparisonQueryException;
import main.bg.softuni.repository.interfaces.DataSorter;
import main.bg.softuni.io.OutputWriter;

import java.util.*;
import java.util.stream.Collectors;

public class RepositorySorter implements DataSorter {

    @Override
    public void printSortedStudents (
            Map<String, Double> studentsWithMarks,
            String comparisonType,
            int numberOfStudents) {
        comparisonType = comparisonType.toLowerCase();

        if (!comparisonType.equals("ascending") && !comparisonType.equals("descending")) {
            throw new InvalidComparisonQueryException();
        }

        Comparator<Map.Entry<String, Double>> comparator = (x, y) -> {
            double value1 = x.getValue();
            double value2 = y.getValue();
            return Double.compare(value1, value2);
        };

        List<String> sortedStudents = studentsWithMarks.entrySet()
                .stream()
                .sorted(comparator)
                .limit(numberOfStudents)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (comparisonType.equals("descending")) {
            Collections.reverse(sortedStudents);
        }

        printStudents(studentsWithMarks, sortedStudents);
    }

    private void printStudents(Map<String, Double> courseData, List<String> sortedStudents) {
        for (String student : sortedStudents) {
            OutputWriter.printStudent(student, courseData.get(student));
        }
    }
}

