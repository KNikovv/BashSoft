package main.bg.softuni.exceptions;

public class DuplicateEntryInStructureException extends IllegalArgumentException {

    private static final String DUPLICATE_ENTRY = "The %s already exist in %s";

    public DuplicateEntryInStructureException(String entryName, String courseName) {
        super(String.format(DUPLICATE_ENTRY, entryName, courseName));
    }
    public DuplicateEntryInStructureException(String message) {
        super(message);
    }

}
