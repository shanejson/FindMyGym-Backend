package com.findmygym.findMyGymbackend.parser;

import com.findmygym.findMyGymbackend.model.GymDetails;
import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.findmygym.findMyGymbackend.validators.Validator;

public class goodLifeFitnessParser {
    public void parseGoodLifeFitness() throws IOException {
        //Validator instance to access methods
        Validator Validator = new Validator();

        //JSON Array which will contain jsonObject and stored in "gymData.json"
        JsonArray jsonArray = new JsonArray();

        //File to be used & check if the file exists
        File outputFile = new File("gymData.json");
        if (outputFile.exists()) {
            try (FileReader fileReader = new FileReader("gymData.json")) {
                jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Parsing Process for the crawled Fit4Less HTML Pages stored in the folder
        String folderPath = "HTMLFilesGoodLifeFitness";
        File HTMLFilesFolder = new File(folderPath);
        File[] HTMLfileList = HTMLFilesFolder.listFiles();
        if (HTMLfileList != null) {
            for (File i : HTMLfileList) {
                if (i.isFile() && i.getName().endsWith(".html")) {
                    try{
                        //Creating a fresh jsonObject for each HTML file
                        JsonObject jsonObject = new JsonObject();
                        System.out.println("----------------------------------------------------");
                        System.out.println("Parsing file: " + i);
                        Document parsedDocument = Jsoup.parse(i, "UTF-8", "");

                        //Adding Unique ID
                        UUID uniqueID = UUID.randomUUID();
                        jsonObject.addProperty("ID", String.valueOf(uniqueID));

                        //Adding static gym name
                        jsonObject.addProperty("gym", "GoodLife Fitness");

                        //Adding Province and City name
                        Elements divElement = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div");
                        String province = divElement.attr("data-club-province");
                        jsonObject.addProperty("province", province);
                        //City
                        String titleText = parsedDocument.title();
                        String GymCity = titleText.split("\\|")[1].trim();
                        String[] parts = GymCity.split("\\s+");
                        String city = parts[0];
                        jsonObject.addProperty("city", city);

                        //Adding the Gym Branch Name based on the location
                        String GymLocationNameElement = parsedDocument.selectXpath("//*[@id=\"club-detail-page-35ffdaeaba\"]/div[2]/div[1]/div/div/div[2]/div/div/div/h1").text();
                        jsonObject.addProperty("GymLocationNameElement", GymLocationNameElement);
                        //System.out.println("GymLocationNameElement: "+ GymLocationNameElement);

                        //Adding Location Address
                        String GymLocationAddressElement = parsedDocument.selectXpath("//*[@id=\"club-detail-page-35ffdaeaba\"]/div[2]/div[1]/div/div/div[2]/div/div/div/p[2]").text();
                        jsonObject.addProperty("GymLocationAddressElement", GymLocationAddressElement);

                        //Extracting and adding the Address Details
                        String[] addressComponents = Validator.extractAddressComponents(GymLocationAddressElement);
                        if (addressComponents != null) {
                            jsonObject.addProperty("GymLocationAddressStreetName", addressComponents[0]);
                            jsonObject.addProperty("GymLocationAddressCity", addressComponents[1]);
                            jsonObject.addProperty("GymLocationAddressProvince", addressComponents[2]);
                            jsonObject.addProperty("GymLocationAddressPostalCode", addressComponents[3]);
                        } else {
                            jsonObject.addProperty("GymLocationAddressStreetName", " ");
                            jsonObject.addProperty("GymLocationAddressCity", " ");
                            jsonObject.addProperty("GymLocationAddressProvince", " ");
                            jsonObject.addProperty("GymLocationAddressPostalCode", " ");
                        }

                        //Phone Number
                        String GymLocationPhoneElement = parsedDocument.selectXpath("//*[@id=\"club-detail-page-35ffdaeaba\"]/div[2]/div[1]/div/div/div[2]/div/div/div/p[3]/span[1]").text();
                        String extractedPhoneNumber = Validator.extractPhoneNumber(GymLocationPhoneElement);
                        if (extractedPhoneNumber != null) {
                            jsonObject.addProperty("GymLocationPhoneElement", extractedPhoneNumber);
                        } else {
                            jsonObject.addProperty("GymLocationPhoneElement", "");
                        }

                        //Gym Pin Location
                        Elements GymPinLocation = parsedDocument.selectXpath("//*[@id=\"club-detail-page-35ffdaeaba\"]/div[2]/div[3]/div/div/div[2]/div[1]/a");
                        String GymPinLocationElement = GymPinLocation.attr("href");
                        jsonObject.addProperty("GymPinLocationElement", GymPinLocationElement);

                        //Adding Coordinates based on the Gym Pin Location
                        String[] coordinates = Validator.extractCoordinates(GymPinLocationElement);
                        if (coordinates != null) {
                            String latitude = coordinates[0];
                            String longitude = coordinates[1];
                            jsonObject.addProperty("GymPinLocationLatitude", latitude);
                            jsonObject.addProperty("GymPinLocationLongitude", longitude);
                        } else {
                            jsonObject.addProperty("GymPinLocationLatitude", "");
                            jsonObject.addProperty("GymPinLocationLongitude", "");
                        }

                        // Adding Amenities in a Json Array
                        JsonArray GymAmenitiesElement = new JsonArray();
                        Elements GymAmenities = parsedDocument.select("div.c-additional-amenities");
                        Element ListofAmenities = GymAmenities.select("ul").first();
                        if (ListofAmenities != null) {
                            Elements SingleAmenities = ListofAmenities.select("li");
                            for (Element j : SingleAmenities) {
                                //System.out.println("singleAmenities: " + j.text());
                                GymAmenitiesElement.add(j.text());
                            }
                        }
                        jsonObject.add("GymAmenitiesArrayList", GymAmenitiesElement);

                        //Adding count of the amenities offered
                        jsonObject.addProperty("GymAmenitieslength", GymAmenitiesElement.size());

                        //Adding Membership Name
                        String membershipName = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[3]/label").text();
                        jsonObject.addProperty("membershipName", membershipName);

                        //Adding Membership Price
                        String membershipPrice1a = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[3]/div/div/span[2]").text();
                        String membershipPrice1b = parsedDocument.selectXpath("//*[@id=\"Membershiptypes\"]/div/div[1]/table/thead/tr/th[3]/div/div/span[3]").text();
                        String membershipPrice = membershipPrice1a + membershipPrice1b;
                        if (Validator.isValidMembershipPrice(membershipPrice)) {
                            jsonObject.addProperty("membershipPrice", membershipPrice);
                        } else {
                            jsonObject.addProperty("membershipPrice", "");
                        }

                        //Adding Membership Duration
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

                        System.out.println("Details Extracted: " + jsonObject);
                        jsonArray.add(jsonObject);
                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            //To avoid rewriting details for the same gym branch
            //HashSet to store unique value of the key "GymLocationNameElement"
            Set<String> uniqueGymLocations = new HashSet<>();

            //Iterating over jsonArray to remove duplicate
            Iterator<JsonElement> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JsonObject obj = (JsonObject) iterator.next();
                String gymLocationName = obj.get("GymLocationNameElement").getAsString();

                //If "gymLocationName" is present in the set, remove from the json array
                if (uniqueGymLocations.contains(gymLocationName)) {
                    iterator.remove();
                } else {
                    uniqueGymLocations.add(gymLocationName);
                }
            }


            //Writing data in JSON to file
            try (FileWriter fileWriter = new FileWriter("gymData.json")) {
                Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
                gson.toJson(jsonArray, fileWriter);
                System.out.println("Data has been written to gymData.json");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("There are no crawled HTML files.");
        }
    }
}
