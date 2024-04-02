package com.findmygym.findMyGymbackend.utilities;

import com.findmygym.findMyGymbackend.model.GymDetails;
import com.google.gson.Gson;
import org.openqa.selenium.json.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class Utilities {
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

                for (String objId : objIds){
                    for (GymDetails result: bucket){

                        if(objId.trim().equals(result.getID().trim())){

                           results.put(result.getID(), result);
                           break;
                        }
                    }
                }

            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
                System.out.println("Couldnt Search For the result please try again later");
            }
        }
        return results;
    }

    public void printResults(List<String> objIds){
//        utilities utilities = new utilities();
//        List<String> objIds = new ArrayList<>();
//        objIds.add("cc6f5819-8c82-41a9-800d-6ebd2c4e8de7");

        Map<String, GymDetails> results = extractResult(objIds);

        for (Map.Entry<String,GymDetails> result : results.entrySet() ){

            System.out.println("ID : " + result.getKey());
            GymDetails gymInfo = result.getValue();

            System.out.println("Gym Name : " + gymInfo.getGym());
            System.out.println("Gym City : " + gymInfo.getCity());
        }


    }
    public String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}
//  {
//          "ID": "cc6f5819-8c82-41a9-800d-6ebd2c4e8de7",
//          "gym": "Planet Fitness",
//          "GymLocationNameElement": "Etobicoke",
//          "GymLocationAddressElement": "180 Queens Plate Drive",
//          "city": "Toronto",
//          "province": "Ontario",
//          "GymLocationPhoneElement": "(416) 745-7177",
//          "GymPinLocationElement": "https://www.google.com/maps/place/180+Queens+Plate+Dr,+Etobicoke,+ON+M9W+6Y9,+Canada/@43.7186276,-79.5967954,17z/data=!3m1!4b1!4m2!3m1!1s0x882b3a4864182fd3:0xe6ce4c137b67606",
//          "GymAmenitiesArrayList": [
//          "Bring a Guest Anytime",
//          "Use of Any Planet Fitness Worldwide",
//          "Use of Tanning",
//          "Use of Massage Chairs",
//          "Use of HydroMassage™",
//          "Use of Total Body Enhancement",
//          "50% Off Select Drinks",
//          "Premium Perks: Partner Rewards & Discounts",
//          "Unlimited Access to Home Club",
//          "PF App Workouts",
//          "Free Fitness Training",
//          "Perks: Partner Rewards & Discounts"
//          ],
//          "membershipName": "CLASSIC",
//          "membershipPrice": "15",
//          "membershipDuration": "/mo"
//          },