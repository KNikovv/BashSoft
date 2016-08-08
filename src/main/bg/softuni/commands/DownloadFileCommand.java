package main.bg.softuni.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.network.Downloader;

@Alias("download")
public class DownloadFileCommand extends Command {

    @Inject
    private Downloader downloader;

    public DownloadFileCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 2){
            throw new InvalidInputException(this.getInput());
        }
        String fileUrl = this.getData()[1];
        this.downloader.download(fileUrl);
    }
}
