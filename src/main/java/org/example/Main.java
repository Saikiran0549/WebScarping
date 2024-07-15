package org.example;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        //As per clients' request for confidentiality, I am using a sample open source website called : www.themoviedb.org
        System.out.println("please choose a topic you want to browse");

        System.out.println(".........");
        URLHandler.topicIndex = Integer.parseInt(reader.readLine())-1;

        Document homePage = URLHandler.initiateSession();

        Element topic = URLHandler.getTopic(homePage);

        Document reviewPage = URLHandler.getReviewPage(topic.select("a[href]").attr("href"));

        ArrayList<String> allReviewPages = URLHandler.getNextPagesUrl(reviewPage);

        ArrayList<Elements> allReviews = new ArrayList<>();

        for(String pageUrl : allReviewPages){

            Document page = URLHandler.getReviewPage(pageUrl);
            allReviews.add(page.getElementsByClass("search-results-item-body"));
        }

        boolean success = HandleTheFile.createFile(allReviews,topic.text());

        if(success){
            System.out.println("successfully written to the file");
        }else{
            System.out.println("error");
        }

        String publicAPI = URLHandler.FetchHTMLfromPublicAPI();
        System.out.println("demo HTML fetched: "+publicAPI.substring(0,200));

    }
}
