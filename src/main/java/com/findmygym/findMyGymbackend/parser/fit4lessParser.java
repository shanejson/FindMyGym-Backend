package com.findmygym.findMyGymbackend.parser;
import com.findmygym.findMyGymbackend.model.GymDetails;
import com.findmygym.findMyGymbackend.validators.Validator;
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
import java.util.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class fit4lessParser {
    public List<GymDetails> parsefit4Less() throws IOException {

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

        //The "jsonObject1" stores the membership details as it lies on the home page
        JsonObject jsonObject1 = new JsonObject();
        String url = "https://www.fit4less.ca/membership";
        Document membershipPage = Jsoup.connect(url).get();

        //Extracting Fit4Less Membership ONE Details, which is costly
        //String membershipName1 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[1]/div[1]/div[1]/div/span[1]").text();
        //String membershipPrice1 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[1]/div[1]/div[1]/div/span[2]/span[2]").text();
        //String membershipDuration1 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[1]/div[1]/div[1]/div/span[3]").text();

        //Extracting Fit4Less Membership TWO Details, which is reasonable
        String membershipName2 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/span[1]").text();
        jsonObject1.addProperty("membershipName", membershipName2);

        //Verifying is Price is in correct format
        String membershipPrice2 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/span[2]/span[2]").text();
        if(Validator.isValidMembershipPrice(membershipPrice2)) {
            jsonObject1.addProperty("membershipPrice", membershipPrice2);
        } else {
            jsonObject1.addProperty("membershipPrice", "");
        }

        //Extracting the Duration from the string
        String membershipDuration2 = membershipPage.selectXpath("//*[@id=\"main\"]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/span[3]").text();
        String extractedDuration = Validator.extractMembershipDuration(membershipDuration2);
        if (extractedDuration != null) {
            jsonObject1.addProperty("membershipDuration", extractedDuration);
        } else {
            System.out.println("No valid membership duration found.");
            jsonObject1.addProperty("membershipDuration", "");
        }

        //GymDetails Array List to store the details
        List<GymDetails> GymDetails = new ArrayList<>();

        //Parsing Process for the crawled Fit4Less HTML Pages stored in the folder
        String folderPath = "HTMLFilesfit4Less";
        File HTMLFilesFolder = new File(folderPath);
        File[] HTMLfileList = HTMLFilesFolder.listFiles();

        if (HTMLfileList != null) {
            for(File i : HTMLfileList){
                if(i.isFile() && i.getName().endsWith(".html")){
                    try {
                        //Creating a fresh jsonObject for each HTML file
                        JsonObject jsonObject = new JsonObject();
                        System.out.println("-----------------------------------------------------");
                        System.out.println("Parsing file: "+ i);
                        Document parsedHTMLFile = Jsoup.parse(i, "UTF-8", "");

                        //Adding the Membership details which was extracted earlier for each Object
                        for (String key : jsonObject1.keySet()) {
                            JsonElement value = jsonObject1.get(key);
                            jsonObject.add(key, value);
                        }

                        //Adding Unique ID
                        UUID uniqueID = UUID.randomUUID();
                        jsonObject.addProperty("ID", String.valueOf(uniqueID));

                        //Adding static gym name
                        jsonObject.addProperty("gym", "Fit4Less");

                        //Adding Province and City name
                        Elements GymLocationDetails = parsedHTMLFile.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[1]/a[3]");
                        String GymLocationDetailsX = GymLocationDetails.attr("href");
                        String[] parts = GymLocationDetailsX.split("/");
                        String province = parts[3];
                        province = capitalizeFirstLetter(province);
                        jsonObject.addProperty("province", province);
                        String city = parts[4];
                        city = capitalizeFirstLetter(city);
                        jsonObject.addProperty("city", city);

                        //Adding the Gym Branch Name based on the location
                        String GymLocationNameElement = parsedHTMLFile.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[3]/div[1]/h1/span").text();
                        jsonObject.addProperty("GymLocationNameElement", GymLocationNameElement);

                        //Adding Location Address
                        String GymLocationAddressElement = parsedHTMLFile.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[5]/div[2]/div[1]/div/div[1]").text();
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


                        //Adding and Validating Phone Number
                        String GymLocationPhoneElement = parsedHTMLFile.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[5]/div[2]/div[1]/div/div[2]/a").text();
                        String extractedPhoneNumber = Validator.extractPhoneNumber(GymLocationPhoneElement);
                        if (extractedPhoneNumber != null) {
                            jsonObject.addProperty("GymLocationPhoneElement", extractedPhoneNumber);
                        } else {
                            jsonObject.addProperty("GymLocationPhoneElement", "");
                        }

                        //Adding Gym Pin Address Location
                        Elements GymPinLocation = parsedHTMLFile.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[5]/div[2]/div[1]/div/div[3]/a");
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
                        Elements GymAmenities = parsedHTMLFile.selectXpath("//*[@id=\"LocationDetailsPage\"]/div[5]/div[2]/div[3]/div[2]/div");
                        Element ListofAmenities = GymAmenities.select("ul").first();
                        if(ListofAmenities != null){
                            Elements SingleAmenities = ListofAmenities.select("li");
                            for (Element j : SingleAmenities) {
                                //System.out.println("singleAmenities: " + j.text());
                                GymAmenitiesElement.add(j.text());
                            }
                        }
                        jsonObject.add("GymAmenitiesArrayList", GymAmenitiesElement);

                        //Adding count of the amenities offered
                        jsonObject.addProperty("GymAmenitieslength", GymAmenitiesElement.size());


                        //Validate Gym Name, if a Gym contains "Opening", it means the required data is not provided
                        if(Validator.validateGymName(jsonObject.get("GymLocationNameElement").getAsString())){
                            //Creating the JSON Array
                            jsonArray.add(jsonObject);
                        }else{
                            System.out.println("Discarding Object as name contains 'Opening'.");
                        }


                        System.out.println("Details Extracted: "+jsonObject);
//                        System.out.println("Details Extracted: ");
//                        for (String key : jsonObject.keySet()) {
//                            System.out.println(key + ": " + jsonObject.get(key));
//                        }



                    } catch (IOException e) {
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

            //Writing data in JSON file
            try (FileWriter fileWriter = new FileWriter("gymData.json")) {
                Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
                gson.toJson(jsonArray, fileWriter);
                System.out.println("Data succesfully written to gymData.json file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("HTMLFilesfit4less folder does not exist.");
        }
        return GymDetails;
    }

    //Captializing first letter
    private static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }



}
