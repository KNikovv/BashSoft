package main.bg.softuni.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.io.interfaces.DirectoryManager;

@Alias("cdabs")
public class ChangeAbsolutePathCommand extends Command {

    @Inject
    private DirectoryManager directoryManager;
    public ChangeAbsolutePathCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 2){
            throw new InvalidInputException(this.getInput());
        }

        String absolutePath = this.getData()[1];
        this.directoryManager.changeCurrentDirAbsolutePath(absolutePath);
    }
}
