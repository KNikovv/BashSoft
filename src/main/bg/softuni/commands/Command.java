package main.bg.softuni.commands;

import main.bg.softuni.exceptions.InvalidInputException;

public abstract class Command implements Executable {

    private String input;
    private String[] data;

    protected Command(String input, String[] data) {
        this.setInput(input);
        this.setData(data);
    }

    public String getInput() {
        return input;
    }

    public String[] getData() {
        return data;
    }

    private void setInput(String input) {
        if (input == null || input.equals("")) {
            throw new InvalidInputException(input);
        }
        this.input = input;
    }

    private void setData(String[] data) {
        if (data == null || data.length < 1) {
            throw new InvalidInputException(this.getInput());
        }
        this.data = data;
    }

    @Override
    public abstract void execute() throws Exception;
}
