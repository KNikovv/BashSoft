package main.bg.softuni.exceptions;

public class InvalidComparisonQueryException extends IllegalArgumentException {

    private static final String INVALID_COMPARISON_QUERY =
            "The comparison query you want, does not exist in the context of the current program!";

    public InvalidComparisonQueryException() {
        super(INVALID_COMPARISON_QUERY);
    }

    private InvalidComparisonQueryException(String message) {
        super(message);
    }
}
