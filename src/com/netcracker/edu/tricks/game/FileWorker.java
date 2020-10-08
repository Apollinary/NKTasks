package com.netcracker.edu.tricks.game;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class FileWorker {
    private static final String WORD_BANK_FILE_NAME = "WordsBank.txt";
    private final static String RESULTS_STORAGE_FILE_NAME = "ResultsStorage.txt";

    public static String getRandomWordFromBank() {
        int numberOfLine = new Random().nextInt(getLinesCount(WORD_BANK_FILE_NAME));

        String result = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(WORD_BANK_FILE_NAME)));
            for (int i = 0; i < numberOfLine; i++) {
                reader.readLine();
            }
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getLinesCount(String filename) {
        File file = new File(filename);
        int result = 0;
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                result++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert scanner != null;
            scanner.close();
        }
        return result;
    }

    public static void writeResultToStorage(String userName, String word, boolean isGuessed, int numberOfTrials) {
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        String lineToWrite = userName + ";" + word + ";" + isGuessed + ";" + numberOfTrials + ";" + formatForDateNow.format(new Date()) + ";" + "\n";
        try {
            Files.write(Paths.get(RESULTS_STORAGE_FILE_NAME).getFileName(), lineToWrite.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
