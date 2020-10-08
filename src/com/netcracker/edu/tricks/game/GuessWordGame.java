package com.netcracker.edu.tricks.game;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GuessWordGame {

    private static final String HIDDEN_WORD = FileWorker.getRandomWordFromBank();
    private int trialCounter = 0;
    private String currentUserName;

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        Set<Character> userLettersSet = new HashSet<>();
        String userInput;
        System.out.println("The game is started!\nEnter your nickname:");
        currentUserName = scanner.nextLine();

        while (!checkVictoryCondition(userLettersSet)) {
            System.out.print("Key in one character or your guess word: ");
            trialCounter++;
            userInput = scanner.nextLine().toLowerCase();
            doTrial(userLettersSet, userInput);
        }

        System.out.println("Congratulation!\nYou got in " + trialCounter + " trials.");
        FileWorker.writeResultToStorage(currentUserName, HIDDEN_WORD, true, trialCounter);
    }

    private void doTrial(Set<Character> userLettersSet, String userInput) {
        if (userInput.length() == 1) {

            if (HIDDEN_WORD.contains(userInput)) {
                System.out.println("You guessed this letter : " + userInput);
                userLettersSet.add(userInput.charAt(0));
            } else {
                System.out.println("There is no such letter in the word.");
            }
            printUserWord(userLettersSet);

        } else if (userInput.length() > 1) {
            checkUserWord(userInput);
        }
    }

    private void checkUserWord(String userWord) {
        if (HIDDEN_WORD.equals(userWord)) {
            System.out.println("Congratulation!\nYou got in " + trialCounter + " trials.");
        } else {
            System.out.println("You missed. The word was: " + HIDDEN_WORD);
        }
        FileWorker.writeResultToStorage(currentUserName, HIDDEN_WORD, HIDDEN_WORD.equals(userWord), trialCounter);
        System.exit(0);
    }

    private boolean checkVictoryCondition(Set<Character> userLettersSet) {
        for (char element : GuessWordGame.HIDDEN_WORD.toCharArray()) {
            if (!userLettersSet.contains(element)) {
                return false;
            }
        }
        return true;
    }

    private void printUserWord(Set<Character> userLettersSet) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < GuessWordGame.HIDDEN_WORD.toCharArray().length; i++) {
            if (userLettersSet.contains(GuessWordGame.HIDDEN_WORD.charAt(i))) {
                result.append(GuessWordGame.HIDDEN_WORD.charAt(i));
            } else {
                result.append('_');
            }
        }
        System.out.println("Trial " + trialCounter + ": " + result);
    }

}
