package main.bg.softuni.exceptions;

public class NullOrEmptyStringException extends IllegalArgumentException {

    private static final String NULL_OR_EMPTY_VALUE = "The value of the variable CANNOT be null or empty!";

    public NullOrEmptyStringException() {
        super(NULL_OR_EMPTY_VALUE);
    }

    public NullOrEmptyStringException(String message) {
        super(message);
    }
}
