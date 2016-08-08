package main.bg.softuni;

import main.bg.softuni.io.CommandInterpreter;
import main.bg.softuni.io.IOManager;
import main.bg.softuni.io.InputReader;
import main.bg.softuni.io.OutputWriter;
import main.bg.softuni.io.interfaces.DirectoryManager;
import main.bg.softuni.io.interfaces.Interpreter;
import main.bg.softuni.io.interfaces.Reader;
import main.bg.softuni.judge.ContentComparer;
import main.bg.softuni.judge.Tester;
import main.bg.softuni.network.ASynchDownloader;
import main.bg.softuni.network.DownloadManager;
import main.bg.softuni.repository.RepositoryFilter;
import main.bg.softuni.repository.RepositorySorter;
import main.bg.softuni.repository.StudentsRepository;
import main.bg.softuni.repository.interfaces.DataFilter;
import main.bg.softuni.repository.interfaces.DataSorter;
import main.bg.softuni.repository.interfaces.Database;

public class Main {

    public static void main(String[] args) {
        ContentComparer tester = new Tester();
        ASynchDownloader downloadManager = new DownloadManager();
        DirectoryManager ioManager = new IOManager();
        DataSorter sorter = new RepositorySorter();
        DataFilter filter = new RepositoryFilter();
        Database repository = new StudentsRepository(sorter, filter);
        Interpreter commandInterpreter = new CommandInterpreter(tester, repository, downloadManager, ioManager);
        Reader inputReader = new InputReader(commandInterpreter);

        try{
            inputReader.readCommands();
        }catch (Exception ioe){
            OutputWriter.displayException(ioe.getMessage());
        }

    }
}
