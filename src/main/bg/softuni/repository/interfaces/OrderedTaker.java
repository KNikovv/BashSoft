package main.bg.softuni.repository.interfaces;

public interface OrderedTaker {

    void orderAndTake(String courseName, String orderType);

    void orderAndTake(String courseName, String orderType, int studentsToTake);
}
