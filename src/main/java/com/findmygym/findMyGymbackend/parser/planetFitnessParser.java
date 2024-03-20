package com.findmygym.findMyGymbackend.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class planetFitnessParser {
    public void getPlanetFitnessDetails() throws IOException {
        JsonArray jsonArray = new JsonArray();
        File outputFile = new File("gymData.json");
        if (outputFile.exists()) {
            try (FileReader fileReader = new FileReader("gymData.json")) {
                jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String folderPath = "HTMLFilesPlanetFitness";
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
                    jsonObject.addProperty("gym", "Planet Fitness");

                    //Location Name
                    String GymLocationNameElementX = parsedDocument.select("h1.club-name").text();
                    String GymLocationNameElement = GymLocationNameElementX.split(",")[0].trim();
                    System.out.println("GymLocationNameElement: "+ GymLocationNameElement);
                    jsonObject.addProperty("GymLocationNameElement", GymLocationNameElement);

                    //Location Address
                    String GymLocationAddressElement = parsedDocument.selectXpath("//*[@id=\"main-content\"]/div[1]/div[2]/p[1]").text();
                    System.out.println("GymLocationAddressElement "+GymLocationAddressElement);
                    jsonObject.addProperty("GymLocationAddressElement", GymLocationAddressElement);

                    String cityProvince = parsedDocument.selectXpath("//*[@id=\"main-content\"]/div[1]/div[2]/p[2]").text();
                    System.out.println("City----------------"+cityProvince);
                    String city = cityProvince.split(",")[0].trim();
                    System.out.println("City----------------"+city);
                    jsonObject.addProperty("city", city);

                    // Define a regular expression pattern to match the first two letters after the comma
                    Pattern pattern = Pattern.compile(",\\s*(\\p{Alpha}{2})");
                    Matcher matcher = pattern.matcher(cityProvince);
                    if (matcher.find()) {
                        // Extract the matched group which contains the first two letters after the comma
                        String province = matcher.group(1);
                        System.out.println("First two letters after the comma: " + province);
                        if(province.equals("AB")) jsonObject.addProperty("province", "Alberta");
                        if(province.equals("BC")) jsonObject.addProperty("province", "British Columbia");
                        if(province.equals("MB")) jsonObject.addProperty("province", "Manitoba");
                        if(province.equals("NB")) jsonObject.addProperty("province", "New Brunswick");
                        if(province.equals("NL")) jsonObject.addProperty("province", "Newfoundland and Labrador");
                        if(province.equals("NS")) jsonObject.addProperty("province", "Nova Scotia");
                        if(province.equals("NT")) jsonObject.addProperty("province", "Northwest Territories");
                        if(province.equals("NU")) jsonObject.addProperty("province", "Nunavut");
                        if(province.equals("ON")) jsonObject.addProperty("province", "Ontario");
                        if(province.equals("PE")) jsonObject.addProperty("province", "Prince Edward Island");
                        if(province.equals("QC")) jsonObject.addProperty("province", "Québec");
                        if(province.equals("SK")) jsonObject.addProperty("province", "Saskatchewan");
                        if(province.equals("YT")) jsonObject.addProperty("province", "Yukon");
                        System.out.println("Province: " + province);
                    } else {
                        System.out.println("No match found");
                    }


                    //Phone Number
                    String GymLocationPhoneElement = parsedDocument.selectXpath("//*[@id=\"main-content\"]/div[1]/div[2]/div[2]/a").text();
                    System.out.println("GymLocationPhoneElement "+GymLocationPhoneElement);
                    jsonObject.addProperty("GymLocationPhoneElement", GymLocationPhoneElement);

                    //Gym Pin Location
                    Elements GymPinLocation = parsedDocument.selectXpath("//*[@id=\"main-content\"]/div[1]/div[2]/div[1]/a");
                    String GymPinLocationElement = GymPinLocation.attr("href");
                    System.out.println("GymPinLocationElement "+GymPinLocationElement);
                    jsonObject.addProperty("GymPinLocationElement", GymPinLocationElement);

                    //Amenities
                    JsonArray GymAmenitiesElement = new JsonArray();
                    // Select the <h3> element with the specified class
                    // Select the parent <div> elements containing the nested <h3> elements
                    //Elements parentDivs = parsedDocument.selectXpath("//*[@id=\"amenities\"]/div/div[2]");
                    // Select the parent <div> with the specified class
                    Element parentDiv = parsedDocument.selectFirst("div.MuiGrid-root.MuiGrid-container.MuiGrid-spacing-xs-3.MuiGrid-justify-content-xs-center");
                    // Check if the parent <div> exists

                        // Select all <h3> elements within the parent <div>
                        Elements h3Elements = parentDiv.select("h3");

                        // Print the text content of each <h3> element
                        for (Element h3Element : h3Elements) {
                            System.out.println(h3Element.text());
                            GymAmenitiesElement.add(h3Element.text());
                        }

                    jsonObject.add("GymAmenitiesArrayList", GymAmenitiesElement);

                    //membership Name
                    String membershipName = parsedDocument.selectXpath("/html/body/div[1]/div[2]/div[1]/div[4]/div[2]/h2").text();
                    System.out.println("membershipName: "+ membershipName);
                    jsonObject.addProperty("membershipName", membershipName);

                    //Membership Price
                    String membershipPrice = parsedDocument.selectXpath("/html/body/div[1]/div[2]/div[1]/div[4]/div[2]/div[1]/div[3]/div[1]/span[2]").text();
                    System.out.println("membershipPrice: "+ membershipPrice);
                    jsonObject.addProperty("membershipPrice", membershipPrice);

                    //membership Duration
                    String membershipDuration = parsedDocument.selectXpath("/html/body/div[1]/div[2]/div[1]/div[4]/div[2]/div[1]/div[3]/div[1]/div/span").text();
                    System.out.println("membershipDuration: "+ membershipDuration);
                    jsonObject.addProperty("membershipDuration", membershipDuration);

                    jsonArray.add(jsonObject);

                }
            }

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
