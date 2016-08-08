package main.bg.softuni.exceptions;

public class InvalidFilterException extends IllegalArgumentException {
    private static final String INVALID_FILTER = "Invalid filter.";

    public InvalidFilterException() {
        super(INVALID_FILTER);
    }

    private InvalidFilterException(String message) {
        super(message);
    }
}
