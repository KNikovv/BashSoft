package main.bg.softuni.io.interfaces;

import java.io.IOException;

public interface Interpreter {

    void interpretCommand(String input) throws IOException;
}
