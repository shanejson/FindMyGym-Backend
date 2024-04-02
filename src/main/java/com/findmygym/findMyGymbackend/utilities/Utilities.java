package com.findmygym.findMyGymbackend.utilities;

import com.findmygym.findMyGymbackend.model.GymDetails;
import com.google.gson.Gson;
import org.openqa.selenium.json.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utilities {
    ///////////////////////////////// EXTRACT RESULTS FROM JSON ///////////////////////
    public Map<String , GymDetails> extractResult(List<String> objIds) {

        Map<String, GymDetails> results = new HashMap<>();
        List<GymDetails> bucket;

        File theFile = new File("D:\\Project\\ACC project\\findMyGym2\\findMyGym-backend\\gymData.json");
        if(theFile.exists()){
            try{
                FileReader rdr = new FileReader(theFile);
                Type type = new TypeToken<List<GymDetails>>(){}.getType();
                Gson g = new Gson();
                bucket = g.fromJson(rdr, type);

                // GETTING DATA ONLY WITH THIS IDS
                for (String objId : objIds){
                    for (GymDetails result: bucket){
                        // CHECKING IDS ONLY GET THOSE
                        if(objId.trim().equals(result.getID().trim())){

                           results.put(result.getID(), result);
                           break;
                        }
                    }
                }
                /// HANDLING FILE NOT FOUND EXCEPTIONS
            } catch (FileNotFoundException e) {
                System.out.println("Couldnt Search For the result please try again later");
            }
        }
        return results;
    }

    ///////////////////////////////// PRINTING RESULTS ////////////////////////////////////
    public void printResults(List<String> objIds){

        Map<String, GymDetails> results = extractResult(objIds);

        // SHOWING DATA ONLY WITH HIGHEST NUMBER OF AMENITIES
        Map<String,GymDetails> sortedNewMap = results.entrySet().stream().sorted((e1,e2)->
                Integer.max(e1.getValue().getGymAmenitieslength(), e2.getValue().getGymAmenitieslength()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println("Top 5 Gyms for the city are : ");
        int index = 0;
        for (Map.Entry<String,GymDetails> result : sortedNewMap.entrySet() ){
            if(index < 5){
                GymDetails gymInfo = result.getValue();
                System.out.println();
                System.out.println( "******************************************************************************************************************");
                System.out.println("    Gym Name : " + gymInfo.getGym());
                System.out.println("    Gym Membership Price : " + gymInfo.getMembershipPrice());
                System.out.println("    Gym Membership Name : " + gymInfo.getMembershipName());
                System.out.println("    Gym Working Hours : " + gymInfo.getMembershipDuration());
                System.out.println("    Gym City : " + gymInfo.getCity());
                System.out.println("    Gym Province : " + gymInfo.getProvince());
                System.out.println("    Gym Location : " + gymInfo.getGymLocationNameElement());
                System.out.println("    Gym Address : " + gymInfo.getGymLocationAddressElement());
                System.out.println("    Gym Contact : " + gymInfo.getGymLocationPhoneElement());
                System.out.println("    Gym Google Map Point : " + gymInfo.getGymPinLocationElement());
                System.out.println("    Gym Total Amenities : " + gymInfo.getGymAmenitieslength());
                System.out.println("    Gym Amenities are as follows : " );

                List<String> amenities = gymInfo.getGymAmenitiesArrayList();

                for(String amenity : amenities){
                    System.out.println("        " +amenity);
                }

                System.out.println( "******************************************************************************************************************");
            }else {
                break;
            }
            index++;

        }


    }
    public String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    // REMOVE ASTERIK METHOD
    public String removeAsteriskFromEnd(String str) {
        // Check if the string ends with '*'
        if (str.endsWith("*")) {
            // If it does, remove the last character
            return str.substring(0, str.length() - 1);
        } else {
            // If it doesn't, return the original string
            return str;
        }
    }

    // CHECK ASTERIK METHOD
    public boolean endsWithAsterisk(String str) {
        // Check if the string ends with '*'
        return str.endsWith("*");
    }
}
