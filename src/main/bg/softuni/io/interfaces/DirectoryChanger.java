package main.bg.softuni.io.interfaces;

import java.io.IOException;

public interface DirectoryChanger {

    void changeCurrentDirRelativePath(String relativePath) throws IOException;

    void changeCurrentDirAbsolutePath(String absolutePath) throws IOException;
}
