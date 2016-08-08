package main.bg.softuni.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.repository.interfaces.Database;
import main.bg.softuni.staticData.ExceptionMessages;

@Alias("order")
public class PrintOrderedStudentsCommand extends Command {

    @Inject
    private Database database;

    public PrintOrderedStudentsCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 5) {
            throw new InvalidInputException(this.getInput());
        }

        String courseName = this.getData()[1];
        String orderType = this.getData()[2].toLowerCase();
        String takeCommand = this.getData()[3].toLowerCase();
        String takeQuantity = this.getData()[4].toLowerCase();

        this.tryParseParametersForOrder(takeCommand, takeQuantity, courseName, orderType);
    }

    private void tryParseParametersForOrder(
            String takeCommand, String takeQuantity,
            String courseName, String orderType) {
        if (!takeCommand.equals("take")) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_TAKE_COMMAND);
        }

        if (takeQuantity.equals("all")) {
            this.database.orderAndTake(courseName, orderType);
            return;
        }

        try {
            int studentsToTake = Integer.parseInt(takeQuantity);
            this.database.orderAndTake(courseName, orderType, studentsToTake);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_TAKE_QUANTITY_PARAMETER);
        }
    }
}
