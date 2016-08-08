package main.bg.softuni.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.judge.ContentComparer;

@Alias("cmp")
public class CompareFilesCommand extends Command {

    @Inject
    private ContentComparer tester;

    public CompareFilesCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 3){
            throw new InvalidInputException(this.getInput());
        }

        String firstPath = this.getData()[1];
        String secondPath = this.getData()[2];
        this.tester.compareContent(firstPath, secondPath);
    }
}
