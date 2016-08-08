package main.bg.softuni.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.repository.interfaces.Database;

@Alias("show")
public class ShowCourseCommand extends Command {

    @Inject
    private Database database;

    public ShowCourseCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 2 && this.getData().length != 3) {
            throw new InvalidInputException(this.getInput());
        }

        String courseName = this.getData()[1];

        if (this.getData().length == 2) {
            this.database.getStudentsByCourse(courseName);
        } else {
            String student = this.getData()[2];
            this.database.getStudentMarkInCourse(courseName, student);
        }
    }
}
