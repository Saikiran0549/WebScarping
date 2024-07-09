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


        //As per clients' request for confidentiality, I am using a sample open source website called : www.cochranelibrary.com
        System.out.println("please choose a number you want to browse");

        System.out.println("1.Allergy & intolerance  2.Blood disorders  3.Cancer  4.Child health 5.Complementary & alternative medicine 6.Consumer & communication strategies 7.Dentistry & oral health 8.Developmental, psychological and learning problems  9.Diagnosis. 10.Ear,  nose & throat. 11.Effective practice & health systems 12.Endocrine & metabolic 13.Eyes & vision 14.Gastroenterology & hepatology 15.Genetic disorders 16.Health & safety at work 17.Health professional education 18.Heart & circulation  19.infectious disease 20.insurance medicine 21.Kidney disease 22.Lungs & airways 23.Mental Health 24.Methodology 25.Neonatal care 26.Neurology 27.Orthopaedics & trauma 28.Pain & anaesthesia 29.Pregnancy & childbirth 30.Public health 31.Reproductive & sexual health 32.Rheumatology 33.Skin disorders 34.Tobacco, drugs & alcohol 35.Urology 36.Wounds");

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