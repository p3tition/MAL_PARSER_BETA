package com.example.malparser;

public class ConsoleProgressBar {
    public static void printProgressBar(double percentage) {
        StringBuilder progressBar = new StringBuilder("[");
        int length = 70; // Length of the progress bar

        int filledLength = (int) (length * percentage / 100);
        int remainingLength = length - filledLength;

        // Unicode block characters for the filled and remaining parts
        char filledBlock = '\u2588'; // Full block character
        char remainingBlock = '\u2591'; // Light shade character

        for (int i = 0; i < filledLength; i++) {
            progressBar.append(filledBlock);
        }

        for (int i = 0; i < remainingLength; i++) {
            progressBar.append(remainingBlock);
        }

        progressBar.append(String.format("] %.2f%%", percentage));
        System.out.print("\r" + progressBar.toString());
    }
}
