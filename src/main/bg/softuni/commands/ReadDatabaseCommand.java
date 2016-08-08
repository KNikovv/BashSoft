package main.bg.softuni.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.repository.interfaces.Database;

@Alias("readdb")
public class ReadDatabaseCommand extends Command {

    @Inject
    private Database database;

    public ReadDatabaseCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 2){
            throw new InvalidInputException(this.getInput());
        }

        String fileName = this.getData()[1];
        this.database.loadData(fileName);
    }
}
