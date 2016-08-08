package main.bg.softuni.models;

import main.bg.softuni.exceptions.DuplicateEntryInStructureException;
import main.bg.softuni.exceptions.NullOrEmptyStringException;
import main.bg.softuni.models.interfaces.Course;
import main.bg.softuni.models.interfaces.Student;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class SoftUniCourse implements Course {

    public static final int NUMBER_OF_TASKS_ON_EXAM = 5;
    public static final int MAX_SCORE_ON_EXAM_TASK = 100;

    private String name;
    private LinkedHashMap<String, Student> studentsByName;

    public SoftUniCourse(String name) {
        this.setUserName(name);
        this.studentsByName = new LinkedHashMap<>();
    }

    private void setUserName(String courseName) {
        if (courseName == null || courseName.trim().length() == 0) {
            throw new NullOrEmptyStringException();
        }
        this.name = courseName;
    }

    @Override
    public Map<String, Student> getStudentsByName() {
        return Collections.unmodifiableMap(this.studentsByName);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void enrollStudent(Student student) {
        if (this.studentsByName.containsKey(student.getUserName())) {
            throw new DuplicateEntryInStructureException(student.getUserName(), this.name);
        }

        this.studentsByName.put(student.getUserName(), student);
    }

    @Override
    public int compareTo(Course other) {
        return this.getName().compareTo(other.getName());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
