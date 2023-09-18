package com.example.malparser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

import static com.example.malparser.ConsoleProgressBar.printProgressBar;
import static com.example.malparser.JsonToTableDBSQL.json_to_table_db;

public class ScraperModul {
    public static void scraper(String type, String dbUrl, String dbUser, String dbPassword, int totalTitles, int completedTitles){
        try (Connection conndb = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            for(int i=1;i<=totalTitles;i++){
                try {
                    TimeUnit.SECONDS.sleep(1);
                    // URL of the API you want to request data from
                    String apiUrl = "https://api.jikan.moe/v4/"+ type +"/"+i;

                    // Create a URL object from the API URL
                    URL url = new URL(apiUrl);

                    // Create an HttpURLConnection to send the GET request
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    // Get the response code
                    int responseCode = conn.getResponseCode();

                    // Check if the request was successful (200)
                    if (responseCode == 200) {
                        // Read the response from the API
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        // Parse the JSON response using org.json
                        JSONObject jsonResponse = new JSONObject(response.toString());
                        int popularity = jsonResponse.getJSONObject("data").optInt("popularity");
                        if(popularity > 0){
                            System.out.println("PoP: " + popularity);
                            if(popularity <= 10000){
                                json_to_table_db(jsonResponse, conndb, i);
                            }else{
                                System.out.println("Popularity to low: " + popularity);
                            }
                        }else{
                            System.out.println("something wrong with popularity in title with ID: " + i);
                        }
                    } else {
                        System.out.println("GET request failed. Response code: " + responseCode + " ID: " + i);
                    }
                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // Increment completedTitles for each iteration
                completedTitles++;

                // Calculate the percentage progress
                double progressPercentage = (double) completedTitles / (totalTitles-1) * 100;

                // Print the progress bar
                printProgressBar(progressPercentage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
