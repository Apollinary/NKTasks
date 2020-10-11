package com.netcracker.edu.uvarov.urldownloader;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class ResourceDownloader {

    /**
     * Downloads a page from the URL to a file, located on the specified path.
     * @param strURL URL string.
     * @param absolutePathToDirectory A path to which the file is saved.
     */
    public static void downloadFile(String strURL, String absolutePathToDirectory) {
        try (InputStream in = new URL(strURL).openStream()) {
            File existingFile = Paths.get(absolutePathToDirectory).toFile();

            if (existingFile.exists()) {
                int userAnswer = new JOptionPane().showConfirmDialog(null, "File already exists:\n" + existingFile.getAbsolutePath() + "\nRewrite existing file?");
                if (userAnswer == 0) {
                    Files.delete(Paths.get(absolutePathToDirectory));
                    Files.copy(in, Paths.get(absolutePathToDirectory));
                }
            } else {
                Files.copy(in, Paths.get(absolutePathToDirectory));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Download each element of given set of URLs to a file, located on the specified path.
     * @param referencesSet A set of URLs.
     * @param absolutePathToDirectory A path to which the files are saved.
     */
    public static void downloadFilesFromSet(Set<String> referencesSet, String absolutePathToDirectory) {
        File folder = Paths.get(absolutePathToDirectory).toFile();
        if (!folder.exists()) {
            folder.mkdir();
        }
        for (String link : referencesSet) {
            downloadFile(link, absolutePathToDirectory + "/" + StringParser.generateFileName(link));
        }
    }

}
