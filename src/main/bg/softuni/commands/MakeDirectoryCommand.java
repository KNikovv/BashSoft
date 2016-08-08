package main.bg.softuni.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.io.interfaces.DirectoryManager;

@Alias("mkdir")
public class MakeDirectoryCommand extends Command {

    @Inject
    private DirectoryManager directoryManager;

    public MakeDirectoryCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 2) {
            throw new InvalidInputException(this.getInput());
        }

        String folderName = this.getData()[1];
        this.directoryManager.createDirectoryInCurrentFolder(folderName);
    }
}
