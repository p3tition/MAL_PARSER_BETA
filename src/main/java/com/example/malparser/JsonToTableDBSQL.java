package com.example.malparser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.malparser.DownloadSaveImage.downloadAndSaveImage;

public class JsonToTableDBSQL {
    public static void json_to_table_db(JSONObject jsonData, Connection conn, int i) throws SQLException {
        // Database connection details
        String insertSql = "INSERT INTO anime_data (title_japanese, title_english, broadcast_string," +
                "broadcast_timezone, broadcast_time, broadcast_day, year_, episodes, rating, title_src, photo_path," +
                "type_, trailer_youtube, duration, score_mal, approved, season, airing, aired_string, aired_from," +
                " aired_to, studios_id, synopsis,title_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(insertSql);
        pstmt.setString(1, jsonData.getJSONObject("data").optString("title_japanese"));
        pstmt.setString(3, jsonData.getJSONObject("data").getJSONObject("broadcast").optString("string"));
        pstmt.setString(4, jsonData.getJSONObject("data").getJSONObject("broadcast").optString("timezone"));
        pstmt.setString(5, jsonData.getJSONObject("data").getJSONObject("broadcast").optString("time"));
        pstmt.setString(6, jsonData.getJSONObject("data").getJSONObject("broadcast").optString("day"));
        pstmt.setInt(7, jsonData.getJSONObject("data").optInt("year"));
        pstmt.setInt(8, jsonData.getJSONObject("data").optInt("episodes"));
        pstmt.setString(9, jsonData.getJSONObject("data").optString("rating"));
        pstmt.setString(10, jsonData.getJSONObject("data").optString("source"));

        String largeImageUrl = jsonData.getJSONObject("data").getJSONObject("images").getJSONObject("jpg").optString("large_image_url");
        String titleEnglish = jsonData.getJSONObject("data").optString("title_english");
        String titleb = jsonData.getJSONObject("data").optString("title");
        String titlec = jsonData.getJSONObject("data").optString("title_default");
        String uniqueId =  String.valueOf(i); // Replace this with a unique identifier like the anime's ID or a timestamp

        String imageName;
        String imagePath;
        String photopath;

        String sanitizedTitleEnglish = titleEnglish != null ? titleEnglish.replace("/", "_") : null;
        String sanitizedTitleb = titleb != null ? titleb.replace("/", "_") : null;
        String sanitizedTitlec = titlec != null ? titlec.replace("/", "_") : null;

        if (sanitizedTitleEnglish != null) {
            imageName = sanitizedTitleEnglish + "_" + uniqueId + ".jpg";
            imagePath = "D:/AAA_projects/anination07/src/main/upload/static/images/anime_pic/" + imageName;
            photopath = "/upload/static/images/anime_pic/" + imageName;
            pstmt.setString(2, jsonData.getJSONObject("data").optString("title_english"));
        } else if (sanitizedTitleb != null) {
            imageName = sanitizedTitleb + "_" + uniqueId + ".jpg";
            imagePath = "D:/AAA_projects/anination07/src/main/upload/static/images/anime_pic/" + imageName;
            photopath = "/upload/static/images/anime_pic/" + imageName;
            pstmt.setString(2, jsonData.getJSONObject("data").optString("title_english"));
        } else if (sanitizedTitlec != null) {
            imageName = sanitizedTitlec + "_" + uniqueId + ".jpg";
            imagePath = "D:/AAA_projects/anination07/src/main/upload/static/images/anime_pic/" + imageName;
            photopath = "/upload/static/images/anime_pic/" + imageName;
            pstmt.setString(2, jsonData.getJSONObject("data").optString("title_default"));
        } else {
            System.out.print("CAN'T FIND TITLE FOR SAVE IMAGE");
            imageName = "ERROR" + "_" + uniqueId + ".jpg";
            imagePath = "D:/AAA_projects/anination07/src/main/upload/static/images/anime_pic/" + imageName;
            photopath = "/upload/static/images/anime_pic/" + imageName;
            pstmt.setString(2, jsonData.getJSONObject("data").optString("title_japanese"));
        }
        downloadAndSaveImage(largeImageUrl, imagePath);

        pstmt.setString(11, photopath);
        pstmt.setString(12, jsonData.getJSONObject("data").optString("type"));
        pstmt.setString(13, jsonData.getJSONObject("data").getJSONObject("trailer").optString("url"));
        pstmt.setString(14, jsonData.getJSONObject("data").optString("duration"));
        //pstmt.setDouble(15, jsonData.getJSONObject("data").optDouble("score"));
        double score = jsonData.getJSONObject("data").optDouble("score", 0.0); // Set a default value of 0.0 if score is not a number
        if (Double.isNaN(score)) {
            pstmt.setNull(15, java.sql.Types.DOUBLE); // If score is NaN or not a number, set to NULL in the database
        } else {
            pstmt.setDouble(15, score);
        }
        pstmt.setInt(16, jsonData.getJSONObject("data").optBoolean("approved") ? 1 : 0);
        pstmt.setString(17, jsonData.getJSONObject("data").optString("season"));
        pstmt.setInt(18, jsonData.getJSONObject("data").optBoolean("airing") ? 1 : 0);
        pstmt.setString(19, jsonData.getJSONObject("data").getJSONObject("aired").optString("string"));
        pstmt.setString(20, jsonData.getJSONObject("data").getJSONObject("aired").optString("from"));
        pstmt.setString(21, jsonData.getJSONObject("data").getJSONObject("aired").optString("to"));
        JSONArray studiosArray = jsonData.getJSONObject("data").getJSONArray("studios");
        if (studiosArray.length() > 0) {
            JSONObject studioObject = studiosArray.getJSONObject(0);
            pstmt.setInt(22, studioObject.getInt("mal_id"));
        } else {
            pstmt.setNull(22, java.sql.Types.INTEGER); // If no studio found, set to NULL in the database
        }
        pstmt.setString(23, jsonData.getJSONObject("data").optString("synopsis"));
        pstmt.setString(24, jsonData.getJSONObject("data").optString("status"));

        pstmt.executeUpdate();
    }
}
