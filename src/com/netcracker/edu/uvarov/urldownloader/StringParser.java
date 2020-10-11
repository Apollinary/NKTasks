package com.netcracker.edu.uvarov.urldownloader;

public class StringParser {

    /**
     * Extract a part of URL string between the last '\' symbol and the first '?' symbol.
     * @param connectionString URL string.
     * @return a string, containing file name.
     */
    public static String generateFileName(String connectionString) {
        String fileName = connectionString.split("\\?")[0];

        if ((fileName.indexOf("://") + 2) != fileName.lastIndexOf('/')) {
            if (fileName.charAt(fileName.length() - 1) == '/') {
                fileName = fileName.substring(0, fileName.length() - 1);
            }
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        } else {
            fileName = URLDownloader.DEFAULT_FILE_NAME;
        }
        return fileName.contains(".") ? fileName : fileName + ".html";
    }

}
