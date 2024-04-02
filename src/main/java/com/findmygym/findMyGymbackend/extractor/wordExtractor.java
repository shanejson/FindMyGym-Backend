package com.findmygym.findMyGymbackend.extractor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class wordExtractor {
    public static void main(String args[]) throws IOException {
        extractingCitiesWithIds();
    }
    public void extractingWords() {
        try {
            // Read the JSON file into a string
            String jsonContent = new String(Files.readAllBytes(Paths.get("gymData.json")));

            // Parse the JSON string into a JsonArray
            JsonArray jsonArray = JsonParser.parseString(jsonContent).getAsJsonArray();

            // Set to store unique words
            Set<String> wordsSet = new HashSet<>();

            // Iterate over each object in the array
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();

                // Extract words from each string value in the object, excluding specified fields
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    String fieldName = entry.getKey();
                    if (!fieldName.equals("GymPinLocationElement") && !fieldName.equals("GymLocationPhoneElement")) {
                        JsonElement value = entry.getValue();
                        if (value.isJsonArray()) {
                            JsonArray amenitiesArray = value.getAsJsonArray();
                            for (JsonElement amenityElement : amenitiesArray) {
                                String amenity = amenityElement.getAsString();
                                String[] words = amenity.split("\\W+");
                                for (String word : words) {
                                    // Exclude empty strings and convert to lowercase
                                    if (!word.isEmpty()) {
                                        wordsSet.add(word.toLowerCase());
                                    }
                                }
                            }
                        } else if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                            String[] words;
                            if (fieldName.equals("GymLocationAddressElement")) {
                                String address = value.getAsString();
                                // Split the address by comma to separate parts
                                String[] addressParts = address.split(",");
                                if (addressParts.length > 1) {
                                    // Take only the parts before the last comma (excluding province and postal code)
                                    words = addressParts[0].split("\\W+");
                                } else {
                                    words = address.split("\\W+");
                                }
                            } else {
                                words = value.getAsString().split("\\W+");
                            }
                            for (String word : words) {
                                // Exclude empty strings and convert to lowercase
                                if (!word.isEmpty()) {
                                    wordsSet.add(word.toLowerCase());
                                }
                            }
                        }
                    }
                }
            }

            // Write the words to a text file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("word_list.txt"))) {
                for (String word : wordsSet) {
                    writer.write(word);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Words extracted and written to word_list.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void extractingProvinceCity() throws IOException {
        // Read the JSON file
        BufferedReader br = new BufferedReader(new FileReader("gymData.json"));

        // Parse the JSON content
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(br, JsonArray.class);

        // Create a StringBuilder to store the extracted province and city details
        StringBuilder sb = new StringBuilder();

        // Extract province and city details and append them to the StringBuilder
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            String province = jsonObject.get("province").getAsString();
            String city = jsonObject.get("city").getAsString();
            sb.append(province).append(", ").append(city).append("\n");
        }

        // Write the extracted details to a text file
        FileWriter writer = new FileWriter("province_city_details.txt");
        writer.write(sb.toString());
        writer.close();

        System.out.println("Province and city details have been extracted and stored in province_city_details.txt");
    }

    public void storeUserSearches(String userInput) throws IOException {
        //Check if file exists
        File file = new File("inputRecords.txt");
        if(!file.exists()){
            file.createNewFile();
        }

        //Access file
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);

        // Read user input
        printWriter.println(userInput.toLowerCase());

        System.out.println("User Input written.");

        // Close resources
        printWriter.close();
        bufferedWriter.close();
        fileWriter.close();

    }
    public static void extractingCitiesWithIds() throws IOException {
        // Read the JSON file
        BufferedReader br = new BufferedReader(new FileReader("gymData.json"));

        // Parse the JSON content
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(br, JsonArray.class);

        // Create a StringBuilder to store the extracted province and city details
        StringBuilder sb = new StringBuilder();

        // Extract province and city details and append them to the StringBuilder
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            String province = jsonObject.get("city").getAsString();
            String city = jsonObject.get("ID").getAsString();
            sb.append(province).append(", ").append(city).append("\n");
        }

        // Write the extracted details to a text file
        try {
            FileWriter writer = new FileWriter("data_for_invertedIndexing.txt");
            writer.write(sb.toString());
            writer.close();

            System.out.println("city and ID details have been extracted and stored in data_for_invertedIndexing.txt");
        } catch (IOException e) {
            System.out.println("Couldnt write file");
        }
    }
}
