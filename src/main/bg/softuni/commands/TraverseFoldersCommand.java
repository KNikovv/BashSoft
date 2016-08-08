package main.bg.softuni.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.io.interfaces.DirectoryManager;

@Alias("ls")
public class TraverseFoldersCommand extends Command {

    @Inject
    private DirectoryManager directoryManager;

    public TraverseFoldersCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 1 && this.getData().length != 2) {
            throw new InvalidInputException(this.getInput());
        }

        if (this.getData().length == 1) {
            this.directoryManager.traverseDirectory(0);
            return;
        }

        this.directoryManager.traverseDirectory(Integer.valueOf(this.getData()[1]));

    }
}
