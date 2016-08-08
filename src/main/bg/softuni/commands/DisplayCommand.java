package main.bg.softuni.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.dataStructures.SimpleOrderedBag;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.io.OutputWriter;
import main.bg.softuni.models.interfaces.Course;
import main.bg.softuni.models.interfaces.Student;
import main.bg.softuni.repository.interfaces.Database;

@Alias("display")
public class DisplayCommand extends Command {

    @Inject
    private Database database;

    public DisplayCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if (data.length != 3) {
            throw new InvalidInputException(this.getInput());
        }

        String entityToDisplay = data[1];
        String sortType = data[2];

        switch (entityToDisplay.toLowerCase()) {
            case "students":
                SimpleOrderedBag<Student> studentsSorted = this.database.getStudentsSorted(sortType);
                OutputWriter.writeMessageOnNewLine(studentsSorted.joinWith(System.lineSeparator()));
                break;
            case "courses":
                SimpleOrderedBag<Course> courseSorted = this.database.getCoursesSorted(sortType);
                OutputWriter.writeMessageOnNewLine(courseSorted.joinWith(System.lineSeparator()));
                break;
            default:
                throw new InvalidInputException(this.getInput());
        }
    }
}

