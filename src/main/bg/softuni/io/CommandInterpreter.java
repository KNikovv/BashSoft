package main.bg.softuni.io;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.commands.Executable;
import main.bg.softuni.io.interfaces.DirectoryManager;
import main.bg.softuni.io.interfaces.Interpreter;
import main.bg.softuni.judge.ContentComparer;
import main.bg.softuni.network.ASynchDownloader;
import main.bg.softuni.repository.interfaces.Database;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CommandInterpreter implements Interpreter {

    private static final String COMMANDS_LOCATION = "src/main/bg/softuni/commands";
    private static final String COMMANDS_PACKAGE = "main.bg.softuni.commands.";

    private ContentComparer tester;
    private Database repository;
    private ASynchDownloader downloadManager;
    private DirectoryManager inputOutputManager;

    public CommandInterpreter(ContentComparer tester,
                              Database repository,
                              ASynchDownloader downloadManager,
                              DirectoryManager inputOutputManager) {
        this.tester = tester;
        this.repository = repository;
        this.downloadManager = downloadManager;
        this.inputOutputManager = inputOutputManager;
    }

    @Override
    public void interpretCommand(String input) throws IOException {
        String[] data = input.split("\\s+");
        String commandName = data[0].toLowerCase();
        try {
            Executable command = parseCommand(input, data, commandName);
            command.execute();
        } catch (Throwable t) {
            OutputWriter.displayException(t.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private Executable parseCommand(String input, String[] data, String command) throws IOException {
        Executable executable = null;
        File commandsFolder = new File(COMMANDS_LOCATION);
        for (File file : commandsFolder.listFiles()) {
            if (!file.isFile() || !file.getName().endsWith(".java")) {
                continue;
            }

            try {
                String className = file.getName().substring(0, file.getName().lastIndexOf('.'));
                Class<Executable> exeClass = (Class<Executable>) Class.forName(COMMANDS_PACKAGE + className);

                if (!exeClass.isAnnotationPresent(Alias.class)) continue;

                if (checkIsCommandPresent(command, exeClass)) continue;

                executable = instantiateExecutable(input, data, exeClass);

                break; // no need for unnecessary loop
            } catch (ReflectiveOperationException roe) {
                roe.printStackTrace();
            }
        }
        return executable;
    }

    private Executable instantiateExecutable(String input, String[] data, Class<Executable>
            exeClass) throws ReflectiveOperationException {
        Constructor<Executable> exeCtor = exeClass.getDeclaredConstructor(String.class, String[].class);
        Executable executable = exeCtor.newInstance(input, data);
        this.injectDependencies(executable, exeClass);
        return executable;
    }

    private boolean checkIsCommandPresent(String command, Class<Executable> exeClass) {
        Alias alias = exeClass.getDeclaredAnnotation(Alias.class);
        String value = alias.value();
        if (!value.equalsIgnoreCase(command)) {
            return true;
        }
        return false;
    }

    private void injectDependencies(Executable executable,
                                    Class<Executable> exeClass) throws ReflectiveOperationException {

        Field[] exeFields = exeClass.getDeclaredFields();
        for (Field fieldToSet : exeFields) {
            if (!fieldToSet.isAnnotationPresent(Inject.class)) {
                continue;
            }
            fieldToSet.setAccessible(true);
            Field[] theseFields = CommandInterpreter.class.getDeclaredFields();

            for (Field thisField : theseFields) {
                if (!thisField.getType().equals(fieldToSet.getType())) {
                    continue;
                }

                thisField.setAccessible(true);
                fieldToSet.set(executable, thisField.get(this));
            }
        }

    }
}

