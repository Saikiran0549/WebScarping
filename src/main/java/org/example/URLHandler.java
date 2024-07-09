package org.example;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class URLHandler {


    static int topicIndex=0;

    static Connection.Response initialSessionResponse;
    //C.R class represents http response received from the initial connection to the website. It inlcudes headers, cookies and body.


    public static Document initiateSession() throws IOException {

        //As per clients' request for confidentiality, I am using a sample open source website called : www.cochranelibrary.com
        String mainPageUrl = "https://cochranelibrary.com";

        initialSessionResponse = Jsoup.connect(mainPageUrl)
                .method(Connection.Method.GET)
                .execute();

        Map<String, String> cookies = initialSessionResponse.cookies();

        Document authenticatedPage = Jsoup.connect(mainPageUrl)
                .cookies(cookies)
                .execute().parse();

        return authenticatedPage;

    }

    public static Element getTopic(Document homePage){
        Elements topics = homePage.getElementsByClass("browse-by-list-item");
        Element topic = topics.get(topicIndex);
        return topic;
    }

    public static Document getReviewPage(String topicUrl) throws IOException {

        Document reviewPage = Jsoup.connect(topicUrl)
                .cookies(initialSessionResponse.cookies())
                .execute().parse();

        return reviewPage;
    }

    public static ArrayList<String> getNextPagesUrl(Document reviewPage){

        ArrayList<String> allNextPages = new ArrayList<>();


        Elements nextPages = reviewPage.getElementsByClass("pagination-page-list-item");

        for(Element e : nextPages){

            allNextPages.add(e.select("a[href]").attr("href"));
        }

        return allNextPages;


    }

    public static String FetchHTMLfromPublicAPI () throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://developer.themoviedb.org/reference/intro/getting-started.");
        try(CloseableHttpResponse response = httpClient.execute(request)){
            return EntityUtils.toString(response.getEntity());
        }

    }




}
