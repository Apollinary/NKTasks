package com.netcracker.edu.uvarov.urldownloader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String urlString = args[0];
        String pathToFile = args.length > 1 ? args[1] : System.getProperty("user.dir");
        new URLDownloader(urlString, pathToFile).downloadToFile();
    }

}
