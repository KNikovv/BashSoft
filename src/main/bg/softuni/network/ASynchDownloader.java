package main.bg.softuni.network;

public interface ASynchDownloader extends Downloader {

    void downloadOnNewThread(String fileUrl);
}
