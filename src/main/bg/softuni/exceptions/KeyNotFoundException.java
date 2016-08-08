package main.bg.softuni.exceptions;

public class KeyNotFoundException extends IllegalArgumentException {

    public static final String KEY_NOT_FOUND = "SoftUniStudent must be enrolled in a course before you set his mark.";

    public KeyNotFoundException() {
        super(KEY_NOT_FOUND);
    }

    public KeyNotFoundException(String message) {
        super(message);
    }
}
