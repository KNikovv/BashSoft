package main.bg.softuni.repository;

import main.bg.softuni.dataStructures.SimpleOrderedBag;
import main.bg.softuni.dataStructures.SimpleSortedList;
import main.bg.softuni.models.interfaces.Course;
import main.bg.softuni.models.interfaces.Student;
import main.bg.softuni.repository.interfaces.DataFilter;
import main.bg.softuni.repository.interfaces.DataSorter;
import main.bg.softuni.repository.interfaces.Database;
import main.bg.softuni.io.OutputWriter;
import main.bg.softuni.models.SoftUniCourse;
import main.bg.softuni.models.SoftUniStudent;
import main.bg.softuni.staticData.ExceptionMessages;
import main.bg.softuni.staticData.SessionData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentsRepository implements Database {

    private boolean isDataInitialized = false;
    private Map<String, Course> courses;
    private Map<String, Student> students;
    private DataFilter filter;
    private DataSorter sorter;

    public StudentsRepository(DataSorter sorter, DataFilter filter) {
        this.sorter = sorter;
        this.filter = filter;
    }

    public void loadData(String fileName) throws IOException {
        if (this.isDataInitialized) {
            throw new RuntimeException(ExceptionMessages.DATA_ALREADY_INITIALIZED);
        }

        this.students = new LinkedHashMap<>();
        this.courses = new LinkedHashMap<>();
        this.readData(fileName);
    }

    public void unloadData() throws IOException {
        if (!this.isDataInitialized) {
            throw new RuntimeException(ExceptionMessages.DATA_NOT_INITIALIZED);
        }

        this.students = null;
        this.courses = null;
        this.isDataInitialized = false;
    }


    public void getStudentMarkInCourse(String courseName, String studentName) {
        if (!isQueryForStudentPossible(courseName, studentName)) {
            return;
        }

        double mark = this.courses.get(courseName).getStudentsByName().get(studentName).getMarksByCourseName().get
                (courseName);
        OutputWriter.printStudent(studentName, mark);
    }

    public void getStudentsByCourse(String courseName) {
        if (!isQueryForCoursePossible(courseName)) {
            return;
        }

        OutputWriter.writeMessageOnNewLine(courseName + ":");
        for (Map.Entry<String, Student> student : this.courses.get(courseName).getStudentsByName().entrySet()) {
            this.getStudentMarkInCourse(courseName, student.getKey());
        }
    }

    @Override
    public SimpleOrderedBag<Course> getCoursesSorted(String sortType) {
        SimpleOrderedBag<Course> courseSortedList = new SimpleSortedList<>(Course.class, sortType);

        courseSortedList.addAll(this.courses.values());
        return courseSortedList;
    }

    @Override
    public SimpleOrderedBag<Student> getStudentsSorted(String sortType) {
        SimpleOrderedBag<Student> studentsSortedList = new SimpleSortedList<>(Student.class, sortType);

        studentsSortedList.addAll(this.students.values());
        return studentsSortedList;
    }

    public void filterAndTake(String courseName, String filter) {
        int studentsToTake = this.courses.get(courseName).getStudentsByName().size();
        filterAndTake(courseName, filter, studentsToTake);
    }

    public void filterAndTake(
            String courseName, String filter, int studentsToTake) {
        if (!isQueryForCoursePossible(courseName)) {
            return;
        }

        Map<String, Double> marks = new LinkedHashMap<>();
        for (Map.Entry<String, Student> entry : this.courses.get(courseName).getStudentsByName().entrySet()) {
            marks.put(entry.getKey(), entry.getValue().getMarksByCourseName().get(courseName));
        }
        this.filter.printFilteredStudents(marks, filter, studentsToTake);
    }

    public void orderAndTake(
            String courseName, String orderType, int studentsToTake) {
        if (!isQueryForCoursePossible(courseName)) {
            return;
        }
        Map<String, Double> marks = new LinkedHashMap<>();
        for (Map.Entry<String, Student> entry : this.courses.get(courseName).getStudentsByName().entrySet()) {
            marks.put(entry.getKey(), entry.getValue().getMarksByCourseName().get(courseName));
        }
        this.sorter.printSortedStudents(marks, orderType, studentsToTake);
    }

    public void orderAndTake(String courseName, String orderType) {
        int studentsToTake = this.courses.get(courseName).getStudentsByName().size();
        orderAndTake(courseName, orderType, studentsToTake);
    }

    private void readData(String fileName) throws IOException {
        String regex = "([A-Z][a-zA-Z#\\+]*_[A-Z][a-z]{2}_\\d{4})\\s+([A-Za-z]+\\d{2}_\\d{2,4})\\s([\\s0-9]+)";
        Pattern pattern = Pattern.compile(regex);

        String path = SessionData.currentPath + "\\" + fileName;
        List<String> lines = Files.readAllLines(Paths.get(path));

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (line.isEmpty() || !matcher.find()) {
                continue;
            }

            String courseName = matcher.group(1);
            String studentName = matcher.group(2);
            String scoresStr = matcher.group(3);
            try {
                int[] scores = Arrays.stream(scoresStr.split("\\s+")).mapToInt(Integer::parseInt).toArray();
                if (Arrays.stream(scores).anyMatch(score -> score > 100 || score < 0)) {
                    OutputWriter.displayException(ExceptionMessages.INVALID_SCORE);
                    continue;
                }
                if (scores.length > SoftUniCourse.NUMBER_OF_TASKS_ON_EXAM) {
                    OutputWriter.displayException(ExceptionMessages.INVALID_NUMBER_OF_SCORES);
                    continue;
                }
                if (!this.students.containsKey(studentName)) {
                    this.students.put(studentName, new SoftUniStudent(studentName));
                }
                if (!this.courses.containsKey(courseName)) {
                    this.courses.put(courseName, new SoftUniCourse(courseName));
                }

                Course course = this.courses.get(courseName);
                Student student = this.students.get(studentName);
                try {
                    student.enrollInCourse(course);
                    student.setMarkOnCourse(course.getName(), scores);
                    course.enrollStudent(student);
                } catch (IllegalArgumentException e) {
                    OutputWriter.displayException(e.getMessage());
                }
            } catch (NumberFormatException e) {
                OutputWriter.displayException(e.getMessage());
            }
        }
        this.isDataInitialized = true;
        OutputWriter.writeMessageOnNewLine("Data read.");
    }

    private boolean isQueryForStudentPossible(String courseName, String studentName) {
        if (!isQueryForCoursePossible(courseName)) {
            return false;
        }

        if (!this.courses.get(courseName).getStudentsByName().containsKey(studentName)) {
            OutputWriter.displayException(ExceptionMessages.NON_EXISTING_STUDENT);
            return false;
        }

        return true;
    }

    private boolean isQueryForCoursePossible(String courseName) {
        if (!isDataInitialized) {
            OutputWriter.displayException(ExceptionMessages.DATA_NOT_INITIALIZED);
            return false;
        }

        if (!this.courses.containsKey(courseName)) {
            OutputWriter.displayException(ExceptionMessages.NON_EXISTING_COURSE);
            return false;
        }

        return true;
    }

}
