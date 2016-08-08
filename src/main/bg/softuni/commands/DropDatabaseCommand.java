package main.bg.softuni.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.io.OutputWriter;
import main.bg.softuni.repository.interfaces.Database;

@Alias("dropdb")
public class DropDatabaseCommand extends Command {

    @Inject
    private Database database;
    public DropDatabaseCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {

        if (this.getData().length != 1){
            throw new InvalidInputException(this.getInput());
        }

        this.database.unloadData();
        OutputWriter.writeMessageOnNewLine("Database dropped!");

    }
}
