package main.bg.softuni.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.network.ASynchDownloader;

@Alias("downloadasynch")
public class DownloadAsynchCommand extends Command {

    @Inject
    private ASynchDownloader downloader;

    public DownloadAsynchCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 2) {
            throw new InvalidInputException(this.getInput());
        }
        String fileUrl = this.getData()[1];
        this.downloader.downloadOnNewThread(fileUrl);

    }
}
