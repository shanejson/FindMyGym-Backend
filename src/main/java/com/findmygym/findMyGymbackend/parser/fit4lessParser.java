package com.findmygym.findMyGymbackend.parser;

import com.findmygym.findMyGymbackend.model.Gym;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;

import java.util.ArrayList;

public class fit4lessParser {
    public List<Gym> parsefit4Less(){
        List<Gym> gymList = new ArrayList<>();

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
                        System.out.println("Parsed Document: " + parsedDocument);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
        return gymList;
    }

//    public Gym extractDate(Document file){
//        //Gym Name
//        Element GymLocationNameElement = parsedDocument.select("h1.gym__details--h1 span").first();
//        String GymLocationName = GymLocationNameElement != null ? GymLocationNameElement.toString() : null;
//
//        //Gym Address
//        Element GymLocationAddressElement = parsedDocument.select("gym-details-info__address").first();
//        String GymLocationAddressName = GymLocationAddressElement != null ? GymLocationAddressElement.toString() : null;
//
//        //Gym Phone
//        Element GymLocatioPhoneElement = parsedDocument.select("gym-details-info__phone a").first();
//        String GymLocationPhoneName = GymLocatioPhoneElement != null ? GymLocatioPhoneElement.toString() : null;
//
//        return new Gym(GymLocationName, GymLocationAddressName, GymLocationPhoneName);
//    }


}
