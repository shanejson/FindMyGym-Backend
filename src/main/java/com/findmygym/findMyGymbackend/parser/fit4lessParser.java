package com.findmygym.findMyGymbackend.parser;

import com.findmygym.findMyGymbackend.model.GymDetails;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.List;

import java.util.ArrayList;

public class fit4lessParser {
    public List<GymDetails> parsefit4Less() throws IOException {

        String url = "https://www.fit4less.ca/membership";

        Document membershipPage = Jsoup.connect(url).get();

        //Fit4Less Membership One Details
        String membershipName1 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[1]/div[1]/div[1]/div/span[1]").text();
        //System.out.println(membershipName1);
        String membershipPrice1 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[1]/div[1]/div[1]/div/span[2]/span[2]").text();
        //System.out.println(membershipPrice1);
        String membershipDuration1 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[1]/div[1]/div[1]/div/span[3]").text();
        //System.out.println(membershipDuration1);

        //Fit4Less Membership Two Details
        String membershipName2 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/span[1]").text();
        System.out.println(membershipName2);

        String membershipPrice2 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/span[2]/span[2]").text();
        System.out.println(membershipPrice2);

        String membershipDuration2 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/span[3]").text();
        System.out.println(membershipDuration2);

        //ArrayList<String> membershipAmenities1 = new ArrayList<>();
        //Elements membershipAmenitiesDiv1 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[1]/div[1]/div[2]");
        //Element membershipAmenitiesList1 = membershipAmenitiesDiv1.select("ul").first();
        //System.out.println("membershipAmenitiesList1--->"+ membershipAmenitiesList1);

        List<GymDetails> GymDetails = new ArrayList<>();

        String folderPath = "HTMLFilesfit4Less";
        File HTMLFilesFolder = new File(folderPath);
        File[] HTMLfileList = HTMLFilesFolder.listFiles();

        //Add if else statement
        if (HTMLfileList != null) {
            for(File i : HTMLfileList){
                if(i.isFile() && i.getName().endsWith(".html")){
                    try {
                        //Parsing HTML file
                        Document parsedDocument = Jsoup.parse(i, "UTF-8", "");

                        String GymLocationNameElement = parsedDocument.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[3]/div[1]/h1/span").text();
                        //System.out.println(GymLocationNameElement);
                        String GymLocationAddressElement = parsedDocument.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[5]/div[2]/div[1]/div/div[1]").text();
                        //System.out.println(GymLocationAddressElement);
                        String GymLocationPhoneElement = parsedDocument.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[5]/div[2]/div[1]/div/div[2]/a").text();
                        //System.out.println(GymLocationPhoneElement);
                        Elements GymPinLocation = parsedDocument.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[5]/div[2]/div[1]/div/div[3]/a");
                        String GymPinLocationElement = GymPinLocation.attr("href");
                        //System.out.println(GymPinLocationElement);
                        ArrayList<String> GymAmenitiesElement = new ArrayList<>();
                        Elements GymAmenities = parsedDocument.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[5]/div[2]/div[3]/div[2]/div");
                        Element ListofAmenities = GymAmenities.select("ul").first();
                        if(ListofAmenities != null){
                            Elements SingleAmenities = ListofAmenities.select("li");
                            for (Element j : SingleAmenities) {
                                GymAmenitiesElement.add(j.text());
                            }
                        }
                        String[] GymAmenitiesArrayList = GymAmenitiesElement.toArray(new String[0]);
                        for (String element : GymAmenitiesArrayList) {
                            //System.out.println(element);
                        }
                        //need to save as an Object inside one JSON file.
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
        return GymDetails;
    }



}
