package main.bg.softuni.repository.interfaces;

import main.bg.softuni.dataStructures.SimpleOrderedBag;
import main.bg.softuni.models.interfaces.Course;
import main.bg.softuni.models.interfaces.Student;

public interface Requester {

    void getStudentMarkInCourse(String courseName, String studentName);

    void getStudentsByCourse(String courseName);

    SimpleOrderedBag<Course> getCoursesSorted(String sortType);

    SimpleOrderedBag<Student> getStudentsSorted(String sortType);
}
