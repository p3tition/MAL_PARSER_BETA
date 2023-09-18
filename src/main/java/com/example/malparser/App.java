package com.example.malparser;

import static com.example.malparser.ScraperModul.scraper;

public class App {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        String dbUrl = "jdbc:mysql://localhost:3306/anination_main_01";
        String dbUser = "root";
        String dbPassword = "mysql";

        //NEED TO WRITE anime, manga or another for generate URL to API
        String type = "anime";

        int totalTitles = 56250;
        int completedTitles = 0;

        scraper(type, dbUrl, dbUser, dbPassword, totalTitles, completedTitles);
        /*  WARNING  */
        /*  CHANGE VALUES ON LINES: 31,77(START) AND 24(END) */

        long endTime = System.currentTimeMillis();
        long elapsedMilliseconds = endTime - startTime;
        long elapsedMinutes = elapsedMilliseconds / 60000;

        System.out.println("Elapsed time: " + elapsedMinutes + " minutes");
    }
}