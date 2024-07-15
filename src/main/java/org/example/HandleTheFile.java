package org.example;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HandleTheFile {

    public static boolean createFile(ArrayList<Elements> reviews, String topic) throws IOException {

        String baseUrl = "https://www.themoviedb.org/";
        String filePath = "movie_reviews.txt";
        int reviewsCount = 0;

        SimpleDateFormat originalFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat targetFormate = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Elements reviewElements : reviews) {
                for (Element review : reviewElements) {

                    String movieUrl = baseUrl + review.select("a[href]").attr("href");

                    String title  = review.getElementsByClass("result-title").text();

                    String actors = review.getElementsByClass("search-result-actors").text();

                    String date = review.getElementsByClass("search-result-date").text();

                    try {
                        Date parsedDate = originalFormat.parse(date);
                        date = targetFormate.format(parsedDate);
                    } catch (ParseException e) {
                        System.err.println("error in fetching date" + e.getMessage());
                        date = " Unknown";
                    }
                    String line = String.format("%s|%s|%s|%s%n", movieUrl, title, actors, date);
                    writer.write(line);
                    writer.newLine();
                    reviewsCount += 1;

                }

            }
            writer.newLine();

            writer.write("total count: " + reviewsCount);

        }catch (IOException e){
            System.out.println("failed to write to the file: "+e.getMessage());
            return false;
        }

        return true;
    }
}
