package com.netcracker.edu.uvarov.urldownloader;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class URLDownloader {
    private final String RESOURCE_PATTERN_STRING = "<link.*?href=[\"'](http.+?)[\"'].*?>|<img.*?src=[\"'](.+?)[\"'].*?>";
    public static final String DEFAULT_FILE_NAME = "index";

    private final String workingDirectory;
    private String fileName;
    private final String urlString;

    public URLDownloader(String urlString, String filePath) {
        this.urlString = urlString;
        this.workingDirectory = defineWorkingDirectory(filePath);
        this.fileName = getUserFileName(filePath);
    }

    /**
     * Return absolute path to folder in which the main file is located.
     *
     * @return absolute path to folder.
     */
    public String getWorkingDirectory() {
        return workingDirectory;
    }

    /**
     * Checks whether the user specified the name of the file to save the content.
     *
     * @param filePath Absolute path to file or directory.
     * @return name of file or null.
     */
    private String getUserFileName(String filePath) {
        Path regularFilePath = new File(filePath).toPath();
        return Files.isDirectory(regularFilePath) ? null : regularFilePath.getFileName().toString();
    }

    /**
     * Sets a URL connection and download it's content.
     *
     * @throws IOException
     */
    public void downloadToFile() throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        fileName = fileName == null ? StringParser.generateFileName(connection.getURL().toString() + ".html") : fileName;
        fileName = checkFileName(workingDirectory, fileName);
        writeContentToFile(connection, workingDirectory + '/' + fileName);
    }

    /**
     * Reads a content from URL connection and replaces the links to page resources and writes the content to a file.
     *
     * @param connection       URL connection object.
     * @param absoluteFileName absolute path to file.
     * @throws IOException
     */
    private void writeContentToFile(HttpURLConnection connection, String absoluteFileName) throws IOException {
        String encoding = getSiteEncoding(connection) != null ? getSiteEncoding(connection) : StandardCharsets.UTF_8.toString();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(absoluteFileName)), encoding));

        String s;
        StringBuilder fullContentString = new StringBuilder();
        while ((s = reader.readLine()) != null) {
            fullContentString.append(s);
        }
        Set<String> resourcesSet = getResourceReferencesSet(fullContentString.toString());
        ResourceDownloader.downloadFilesFromSet(resourcesSet, workingDirectory + "\\" + fileName.replace(".html", "_files\\"));

        writer.write(replaceResourcesLinks(fullContentString.toString(), resourcesSet));
        writer.close();
        reader.close();
    }

    /**
     * Defines the encoding of the HTML page.
     *
     * @param connection URL connection object.
     * @return Encoding of HTML page.
     */
    private String getSiteEncoding(URLConnection connection) {
        String encoding = connection.getHeaderField("Content-Type");
        try {
            encoding = encoding.split("charset=")[1];
        } catch (IndexOutOfBoundsException ex) {
            encoding = null;
        }
        return encoding;
    }

    /**
     * Searches substrings in tags <img> and <link> and adds them to a set.
     *
     * @param fullContentString A string containing the code from URL.
     * @return A set of links.
     */
    private Set<String> getResourceReferencesSet(String fullContentString) {
        Set<String> resourcesReferencesSet = new HashSet<>();
        Pattern pattern = Pattern.compile(RESOURCE_PATTERN_STRING);
        Matcher matcher = pattern.matcher(fullContentString);

        while (matcher.find()) {
            if (matcher.group(1) != null && (!resourcesReferencesSet.contains(matcher.group(1)))) {
                resourcesReferencesSet.add(matcher.group(1));
            } else if (matcher.group(2) != null) {
                resourcesReferencesSet.add(matcher.group(2));
            }
        }

        return resourcesReferencesSet;
    }

    /**
     * Checks whether the parameter is a directory or a file.
     * Returns the path to the folder where the file is located if the parameter is a regular file.
     *
     * @param pathToFile Path to directory or file.
     * @return A path to directory.
     * @throws IllegalArgumentException If parameter is not a directory or a file.
     */
    private String defineWorkingDirectory(String pathToFile) {
        Path file = new File(pathToFile).toPath();

        if (Files.isDirectory(file)) {
            return pathToFile;
        } else if (Files.isRegularFile(file) || !Files.exists(file)) {
            return file.getParent().toString();
        } else {
            throw new IllegalArgumentException("String must point to file or directory.");
        }
    }

    /**
     * Checks whether a file exists in the specified directory.
     * If file exists, asks the user to rename file or rewrite existing file.
     *
     * @param directoryString Path to directory.
     * @param fileName        name of file to check.
     * @return A string wit filename.
     */
    private String checkFileName(String directoryString, String fileName) {
        File existingFile = new File(directoryString + "\\" + fileName);
        int userAnswer = 0;
        if (existingFile.exists()) {
            userAnswer = new JOptionPane().showConfirmDialog(null, "File already exists:\n" + existingFile.getAbsolutePath() + "\nRewrite existing file?");
        }
        if (userAnswer != 0) {
            fileName = JOptionPane.showInputDialog("Enter new file name:") + ".html";
        }
        return fileName;
    }


    /**
     * Replaces for links in tags <link> and <img> inside the given URL string.
     * Searches the content string for strings that are in the set and replaces them with the path to local files.
     *
     * @param fullContentString A string containing the code from URL.
     * @param resourcesLinks    A set of strings, which should be replaced.
     * @return A string containing the code from URL with replaced links to resources.
     */
    public String replaceResourcesLinks(String fullContentString, Set<String> resourcesLinks) {
        for (String resourcesLink : resourcesLinks) {
            fullContentString = fullContentString.replace(resourcesLink,
                    getWorkingDirectory() + '\\' + StringParser.generateFileName(resourcesLink));
        }
        return fullContentString;
    }

}