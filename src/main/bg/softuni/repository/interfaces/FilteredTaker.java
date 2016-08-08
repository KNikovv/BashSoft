package main.bg.softuni.repository.interfaces;

public interface FilteredTaker {

    void filterAndTake(String courseName, String filterType);

    void filterAndTake(String courseName, String filter, int studentsToTake);
}
