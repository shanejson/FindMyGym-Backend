package com.findmygym.findMyGymbackend.parser;
import com.findmygym.findMyGymbackend.model.GymDetails;
import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import java.io.File;
import java.util.List;

import java.util.ArrayList;
import java.util.UUID;

public class fit4lessParser {
    public List<GymDetails> parsefit4Less() throws IOException {

        JsonArray jsonArray = new JsonArray();
        File outputFile = new File("gymData.json");
        if (outputFile.exists()) {
            try (FileReader fileReader = new FileReader("gymData.json")) {
                jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JsonObject jsonObject1 = new JsonObject();

        String url = "https://www.fit4less.ca/membership";
        Document membershipPage = Jsoup.connect(url).get();

        //Fit4Less Membership One Details
        String membershipName1 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[1]/div[1]/div[1]/div/span[1]").text();
        //jsonObject1.addProperty("membershipName1", membershipName1);


        String membershipPrice1 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[1]/div[1]/div[1]/div/span[2]/span[2]").text();
        //jsonObject1.addProperty("membershipPrice1", membershipPrice1);


        String membershipDuration1 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[1]/div[1]/div[1]/div/span[3]").text();
        //jsonObject1.addProperty("membershipDuration1", membershipDuration1);


        //Fit4Less Membership Two Details
        String membershipName2 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/span[1]").text();
        jsonObject1.addProperty("membershipName", membershipName2);

        String membershipPrice2 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/span[2]/span[2]").text();
        jsonObject1.addProperty("membershipPrice", membershipPrice2);


        String membershipDuration2 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/span[3]").text();
        jsonObject1.addProperty("membershipDuration", membershipDuration2);

        System.out.println("Object1: "+ jsonObject1);
        List<GymDetails> GymDetails = new ArrayList<>();

        String folderPath = "HTMLFilesfit4Less";
        File HTMLFilesFolder = new File(folderPath);
        File[] HTMLfileList = HTMLFilesFolder.listFiles();

        if (HTMLfileList != null) {
            for(File i : HTMLfileList){
                if(i.isFile() && i.getName().endsWith(".html")){
                    try {
                        JsonObject jsonObject = new JsonObject(); // Create a new JsonObject instance for each HTML file
                        System.out.println("-----------------------------------------------------");
                        System.out.println("Visiting file: "+ i);
                        Document parsedDocument = Jsoup.parse(i, "UTF-8", "");

                        for (String key : jsonObject1.keySet()) {
                            JsonElement value = jsonObject1.get(key);
                            System.out.println("------>>>>"+ key + ": "+ value);
                            jsonObject.add(key, value);
                        }
                        //Adding Unique ID
                        UUID uniqueID = UUID.randomUUID();
                        jsonObject.addProperty("ID", String.valueOf(uniqueID));

                        //Adding gym name static
                        jsonObject.addProperty("gym", "Fit4Less");

                        //Province City
                        Elements GymLocationDetails = parsedDocument.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[1]/a[3]");
                        String GymLocationDetailsX = GymLocationDetails.attr("href");
                        String[] parts = GymLocationDetailsX.split("/");
                        String province = parts[3];
                        province = capitalizeFirstLetter(province);
                        jsonObject.addProperty("province", province);
                        String city = parts[4];
                        city = capitalizeFirstLetter(city);
                        jsonObject.addProperty("city", city);
                        //System.out.println("City: "+ city);
                        //System.out.println("Province: "+ province);

                        //Location Name
                        String GymLocationNameElement = parsedDocument.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[3]/div[1]/h1/span").text();
                        jsonObject.addProperty("GymLocationNameElement", GymLocationNameElement);
                        //System.out.println("GymLocationNameElement: "+ GymLocationNameElement);

                        //Location Address
                        String GymLocationAddressElement = parsedDocument.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[5]/div[2]/div[1]/div/div[1]").text();
                        jsonObject.addProperty("GymLocationAddressElement", GymLocationAddressElement);
                        System.out.println("GymLocationAddressElement "+GymLocationAddressElement);

                        //Phone Number
                        String GymLocationPhoneElement = parsedDocument.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[5]/div[2]/div[1]/div/div[2]/a").text();
                        jsonObject.addProperty("GymLocationPhoneElement", GymLocationPhoneElement);
                        //System.out.println("GymLocationPhoneElement "+GymLocationPhoneElement);

                        //Gym Pin Location
                        Elements GymPinLocation = parsedDocument.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[5]/div[2]/div[1]/div/div[3]/a");
                        String GymPinLocationElement = GymPinLocation.attr("href");
                        jsonObject.addProperty("GymPinLocationElement", GymPinLocationElement);
                        //System.out.println("GymPinLocationElement "+GymPinLocationElement);

                        //Amenities
                        //ArrayList<String> GymAmenitiesElement = new ArrayList<>();
                        JsonArray GymAmenitiesElement = new JsonArray();
                        Elements GymAmenities = parsedDocument.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[5]/div[2]/div[3]/div[2]/div");
                        Element ListofAmenities = GymAmenities.select("ul").first();
                        if(ListofAmenities != null){
                            Elements SingleAmenities = ListofAmenities.select("li");
                            for (Element j : SingleAmenities) {
                                System.out.println("singleAmenities: " + j.text());
                                GymAmenitiesElement.add(j.text());
                            }
                        }
                        //String[] GymAmenitiesArrayList = GymAmenitiesElement.toArray(new String[0]);
                        //for (String element : GymAmenitiesArrayList) {
                         //   System.out.println("Gym Amenities: " + element);
                        //}
                        //System.out.println("GymAmenitiesArrayList: "+GymAmenitiesArrayList);
                        //jsonObject.addProperty("GymAmenitiesArrayList", Arrays.toString(GymAmenitiesArrayList));
                        jsonObject.add("GymAmenitiesArrayList", GymAmenitiesElement);
                        System.out.println(jsonObject);
                        //Creating the JSON Array
                        jsonArray.add(jsonObject);



                        //System.out.println(jsonObject);
                        //System.out.println(" ");
                        System.out.println(jsonArray);

                        // Create a set to store unique values of the key "GymLocationNameElement"
                        Set<String> uniqueGymLocations = new HashSet<>();
                        // Iterate over the jsonArray to remove duplicates
                        Iterator<JsonElement> iterator = jsonArray.iterator();
                        while (iterator.hasNext()) {
                            JsonObject obj = (JsonObject) iterator.next();
                            String gymLocationName = obj.get("GymLocationNameElement").getAsString();
                            // If the gymLocationName is already in the set, remove this object from the array
                            if (uniqueGymLocations.contains(gymLocationName)) {
                                iterator.remove();
                            } else {
                                // Otherwise, add the gymLocationName to the set
                                uniqueGymLocations.add(gymLocationName);
                            }
                        }

                        // Write JSON to file
                        try (FileWriter fileWriter = new FileWriter("gymData.json")) {
                            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
                            gson.toJson(jsonArray, fileWriter);
                            System.out.println("Data has been written to gymData.json");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }else{
            System.out.println("HTMLFilesfit4less folder does not exist.");
        }
        return GymDetails;
    }

    private static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }



}
