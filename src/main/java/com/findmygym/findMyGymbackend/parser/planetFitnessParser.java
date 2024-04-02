package com.findmygym.findMyGymbackend.parser;

import com.findmygym.findMyGymbackend.validators.Validator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class planetFitnessParser {
    public void getPlanetFitnessDetails() throws IOException {

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

        //Parsing Process for the crawled Planet Fitness HTML Pages stored in the folder
        String folderPath = "HTMLFilesPlanetFitness";
        File HTMLFilesFolder = new File(folderPath);
        File[] HTMLfileList = HTMLFilesFolder.listFiles();


        if (HTMLfileList != null) {
            for(File i : HTMLfileList){
                if(i.isFile() && i.getName().endsWith(".html")){
                    try{
                        JsonObject jsonObject = new JsonObject();
                        System.out.println("-----------------------");
                        System.out.println("Visiting file: "+ i);
                        Document parsedDocument = Jsoup.parse(i, "UTF-8", "");

                        //Adding Unique ID
                        UUID uniqueID = UUID.randomUUID();
                        jsonObject.addProperty("ID", String.valueOf(uniqueID));

                        //Adding gym name static
                        jsonObject.addProperty("gym", "Planet Fitness");

                        //Location Name
                        String GymLocationNameElementX = parsedDocument.select("h1.club-name").text();
                        String GymLocationNameElement = GymLocationNameElementX.split(",")[0].trim();
                        jsonObject.addProperty("GymLocationNameElement", GymLocationNameElement);

                        //Location Address
                        String GymLocationAddressElement1 = parsedDocument.selectXpath("//*[@id=\"main-content\"]/div[1]/div[2]/p[1]").text();
                        String GymLocationAddressElement2 = parsedDocument.selectXpath("//*[@id=\"main-content\"]/div[1]/div[2]/p[2]").text();
                        String GymLocationAddressElement = GymLocationAddressElement1 +", "+ GymLocationAddressElement2;
                        jsonObject.addProperty("GymLocationAddressElement", GymLocationAddressElement);

                        //Extracting and adding the Address Details
                        String[] addressComponents = Validator.extractAddressComponents(GymLocationAddressElement);
                        if (addressComponents != null) {
                            jsonObject.addProperty("GymLocationAddressStreetName", addressComponents[0]);
                            jsonObject.addProperty("city", addressComponents[1]);
                            jsonObject.addProperty("GymLocationAddressCity", addressComponents[1]);
                            jsonObject.addProperty("GymLocationAddressProvince", addressComponents[2]);
                            jsonObject.addProperty("province", addressComponents[2]);
                            jsonObject.addProperty("GymLocationAddressPostalCode", addressComponents[3]);
                        }else{
                            jsonObject.addProperty("GymLocationAddressStreetName", "");
                            jsonObject.addProperty("city", "");
                            jsonObject.addProperty("GymLocationAddressCity", "");
                            jsonObject.addProperty("GymLocationAddressProvince", "");
                            jsonObject.addProperty("province", "");
                            jsonObject.addProperty("GymLocationAddressPostalCode", "");
                        }

                        //Phone Number
                        String GymLocationPhoneElement = parsedDocument.selectXpath("//*[@id=\"main-content\"]/div[1]/div[2]/div[2]/a").text();
                        String extractedPhoneNumber = Validator.extractPhoneNumber(GymLocationPhoneElement);
                        if (extractedPhoneNumber != null) {
                            jsonObject.addProperty("GymLocationPhoneElement", extractedPhoneNumber);
                        } else {
                            jsonObject.addProperty("GymLocationPhoneElement", "");
                        }

                        //Gym Pin Location
                        Elements GymPinLocation = parsedDocument.selectXpath("//*[@id=\"main-content\"]/div[1]/div[2]/div[1]/a");
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

                        //Amenities
                        JsonArray GymAmenitiesElement = new JsonArray();
                        Element parentDiv = parsedDocument.selectFirst("div.MuiGrid-root.MuiGrid-container.MuiGrid-spacing-xs-3.MuiGrid-justify-content-xs-center");
                        Elements h3Elements = parentDiv.select("h3");
                        for (Element h3Element : h3Elements) {
                            //System.out.println(h3Element.text());
                            GymAmenitiesElement.add(h3Element.text());
                        }
                        jsonObject.add("GymAmenitiesArrayList", GymAmenitiesElement);

                        //Adding count of the amenities offered
                        jsonObject.addProperty("GymAmenitieslength", GymAmenitiesElement.size());

                        //membership Name
                        String membershipName = parsedDocument.selectXpath("/html/body/div[1]/div[2]/div[1]/div[4]/div[2]/h2").text();
                        //System.out.println("membershipName: "+ membershipName);
                        jsonObject.addProperty("membershipName", membershipName);

                        //Membership Price
                        String membershipPrice = parsedDocument.selectXpath("/html/body/div[1]/div[2]/div[1]/div[4]/div[2]/div[1]/div[3]/div[1]/span[2]").text();
                        //System.out.println("membershipPrice: "+ membershipPrice);
                        //jsonObject.addProperty("membershipPrice", membershipPrice);
                        if(Validator.isValidMembershipPrice(membershipPrice)) {
                            jsonObject.addProperty("membershipPrice", membershipPrice);
                        } else {
                            jsonObject.addProperty("membershipPrice", "");
                        }

                        //Adding Membership Duration
                        String membershipDuration = parsedDocument.selectXpath("/html/body/div[1]/div[2]/div[1]/div[4]/div[2]/div[1]/div[3]/div[1]/div/span").text();
                        String durationExtracted = Validator.extractDuration(membershipDuration);
                        if (durationExtracted != null) {
                            jsonObject.addProperty("membershipDuration", durationExtracted);
                        } else {
                            jsonObject.addProperty("membershipDuration", "");
                        }

                        System.out.println("Details Extracted: "+jsonObject);
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


            // Write JSON to file
            try (FileWriter fileWriter = new FileWriter("gymData.json")) {
                Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
                gson.toJson(jsonArray, fileWriter);
                System.out.println("Planet Fitness data has been written to gymData.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("There are no crawled Planet Fitness HTML files.");
        }
    }
}
