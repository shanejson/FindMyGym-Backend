package com.findmygym.findMyGymbackend.parser;

import com.findmygym.findMyGymbackend.model.GymDetails;
import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class goodLifeFitnessParser {
    public void parseGoodLifeFitness() throws IOException {
        JsonArray jsonArray = new JsonArray();
        File outputFile = new File("gymData.json");
        if (outputFile.exists()) {
            try (FileReader fileReader = new FileReader("gymData.json")) {
                jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String folderPath = "HTMLFilesGoodLifeFitness";
        File HTMLFilesFolder = new File(folderPath);
        File[] HTMLfileList = HTMLFilesFolder.listFiles();
        if (HTMLfileList != null) {
            for(File i : HTMLfileList){
                if(i.isFile() && i.getName().endsWith(".html")){
                    JsonObject jsonObject = new JsonObject();
                    System.out.println("-----------------------");
                    System.out.println("Visiting file: "+ i);
                    Document parsedDocument = Jsoup.parse(i, "UTF-8", "");

                    //Adding Unique ID
                    UUID uniqueID = UUID.randomUUID();
                    jsonObject.addProperty("ID", String.valueOf(uniqueID));

                    //Adding gym name static
                    jsonObject.addProperty("gym", "Good Life Fitness");

                    //Province
                    Elements divElement = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div");
                    String province = divElement.attr("data-club-province");
                    jsonObject.addProperty("province", province);

                    //City
                    String titleText = parsedDocument.title();
                    String GymCity = titleText.split("\\|")[1].trim();
                    String[] parts = GymCity.split("\\s+");
                    String city = parts[0];
                    //System.out.println("City: "+ city);
                    jsonObject.addProperty("city", city);

                    //Location Name
                    String GymLocationNameElement = parsedDocument.selectXpath("//*[@id=\"club-detail-page-35ffdaeaba\"]/div[2]/div[1]/div/div/div[2]/div/div/div/h1").text();
                    jsonObject.addProperty("GymLocationNameElement", GymLocationNameElement);
                    //System.out.println("GymLocationNameElement: "+ GymLocationNameElement);

                    //Location Address
                    String GymLocationAddressElement = parsedDocument.selectXpath("//*[@id=\"club-detail-page-35ffdaeaba\"]/div[2]/div[1]/div/div/div[2]/div/div/div/p[2]").text();
                    jsonObject.addProperty("GymLocationAddressElement", GymLocationAddressElement);
                    //System.out.println("GymLocationAddressElement "+GymLocationAddressElement);

                    //Phone Number
                    String GymLocationPhoneElement = parsedDocument.selectXpath("//*[@id=\"club-detail-page-35ffdaeaba\"]/div[2]/div[1]/div/div/div[2]/div/div/div/p[3]/span[1]").text();
                    jsonObject.addProperty("GymLocationPhoneElement", GymLocationPhoneElement);
                    //System.out.println("GymLocationPhoneElement "+GymLocationPhoneElement);

                    //Gym Pin Location
                    Elements GymPinLocation = parsedDocument.selectXpath("//*[@id=\"club-detail-page-35ffdaeaba\"]/div[2]/div[3]/div/div/div[2]/div[1]/a");
                    String GymPinLocationElement = GymPinLocation.attr("href");
                    jsonObject.addProperty("GymPinLocationElement", GymPinLocationElement);
                    //System.out.println("GymPinLocationElement "+GymPinLocationElement);

                    //Amenities
                    //ArrayList<String> GymAmenitiesElement = new ArrayList<>();
                    JsonArray GymAmenitiesElement = new JsonArray();
                    //Elements GymAmenities = parsedDocument.selectXpath("//*[@id=\"club-detail-page-35ffdaeaba\"]/div[2]/div[5]/div/div/div[3]");
                    //Elements GymAmenities = parsedDocument.select("c-additional-amenities");
                    Elements GymAmenities = parsedDocument.select("div.c-additional-amenities");

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
                        //System.out.println("Gym Amenities: " + element);
                    //}
                    //jsonObject.addProperty("GymAmenitiesArrayList", Arrays.toString(GymAmenitiesArrayList));
                    jsonObject.add("GymAmenitiesArrayList", GymAmenitiesElement);


                    //Pricing
                    String membershipName = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[3]/label").text();
                    //System.out.println("membershipName1: "+ membershipName1);
                    jsonObject.addProperty("membershipName", membershipName);
                    String membershipPrice1a = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[3]/div/div/span[2]").text();
                    String membershipPrice1b = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[3]/div/div/span[3]").text();
                    String membershipPrice = membershipPrice1a + membershipPrice1b;
                    //System.out.println("membershipPrice1: "+ membershipPrice1);
                    jsonObject.addProperty("membershipPrice", membershipPrice);

                    String membershipDuration = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[6]/span").text();
                    jsonObject.addProperty("membershipDuration", membershipDuration);



//                    String membershipName2 = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[7]/label").text();
//                    //System.out.println("membershipName2: "+ membershipName2);
//                    jsonObject.addProperty("membershipName2", membershipName2);
//                    String membershipPrice2a = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[7]/div/div/span[2]").text();
//                    String membershipPrice2b = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[7]/div/div/span[3]").text();
//                    String membershipPrice2 = membershipPrice2a + membershipPrice2b;
//                    //System.out.println("membershipPrice2: "+ membershipPrice2);
//                    jsonObject.addProperty("membershipPrice2", membershipPrice2);

//                    String membershipName3 = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[8]/label").text();
//                    //System.out.println("membershipName3: "+ membershipName3);
//                    jsonObject.addProperty("membershipName3", membershipName3);
//                    String membershipPrice3a = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[8]/div/div/span[2]").text();
//                    String membershipPrice3b = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[8]/div/div/span[3]").text();
//                    String membershipPrice3 = membershipPrice3a + membershipPrice3b;
//                    //System.out.println("membershipPrice3: "+ membershipPrice3);
//                    jsonObject.addProperty("membershipPrice3", membershipPrice3);


                    jsonArray.add(jsonObject);



                }
            }
            System.out.print("1: "+jsonArray);



            System.out.println("Json Array: " + jsonArray);

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

            System.out.println("*****Final: "+ jsonArray);

            // Write JSON to file
            try (FileWriter fileWriter = new FileWriter("gymData.json")) {
                Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
                gson.toJson(jsonArray, fileWriter);
                System.out.println("Data has been written to gymData.json");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            System.out.println("There are no crawled HTML files.");
        }
    }
}
