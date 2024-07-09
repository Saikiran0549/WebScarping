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

        String baseUrl = "https://www.cochranelibrary.com/";
        String filePath = "cochrane_reviews.txt";
        int reviewsCount = 0;

        SimpleDateFormat originalFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat targetFormate = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Elements reviewElements : reviews) {
                for (Element review : reviewElements) {

                    String topicUrl = baseUrl + review.select("a[href]").attr("href");

                    String title = review.getElementsByClass("result-title").text();

                    String authors = review.getElementsByClass("search-result-authors").text();

                    String date = review.getElementsByClass("search-result-date").text();

                    try {
                        Date parsedDate = originalFormat.parse(date);
                        date = targetFormate.format(parsedDate);
                    } catch (ParseException e) {
                        System.err.println("error in fetching date" + e.getMessage());
                        date = " Unknown";
                    }
                    String line = String.format("%s|%s|%s|%s|%s%n", topicUrl, topic, title, authors, date);
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
