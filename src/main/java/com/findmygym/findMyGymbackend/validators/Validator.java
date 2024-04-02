package com.findmygym.findMyGymbackend.validators;


import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    /*
     *
     *
     *  /////////////////////////VALIDATORS/////////////////////
     *
     * */

    //Validate City
    //Won't accept empty strings, length must be less than 50 characters
    public boolean validateCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            return false;
        }
        city = city.trim();
        String cityPattern ="^[a-zA-Z]+(?:\\*{0})?$"; //regex pattern for valid province names
        Pattern pattern = Pattern.compile(cityPattern);
        Matcher matcher = pattern.matcher(city);
        return matcher.matches() && city.length() >= 2 && city.length() <= 50;
    }

    //Validate Gym Name
    //Checks if the word "opening" is present and return false to remove
    public boolean validateGymName(String gymName){
        String regex = "\\bOpening\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(gymName);
        if (matcher.find()) {
            return false;
        } else {
            return true;
        }
    }

    //Checks if the Gym Price is in correct format
    public boolean isValidMembershipPrice(String membershipPrice) {
        String pricePattern = "^\\d+(\\.\\d{1,2})?$";
        Pattern pattern = Pattern.compile(pricePattern);
        Matcher matcher = pattern.matcher(membershipPrice);
        return matcher.matches();
    }


    /*
     *
     *
     *  /////////////////////////FINDING PATTERNS/////////////////////
     *
     * */

    //Extracts Membership Duration from string provided
    public String extractMembershipDuration(String membershipDuration) {
        String durationPattern = "^Every\\s+\\d+\\s+\\w+";
        Pattern pattern = Pattern.compile(durationPattern);
        Matcher matcher = pattern.matcher(membershipDuration);
        if (matcher.find()) {
            return matcher.group(); //Sends back the matched part
        } else {
            return null; //Sends back null, if no matches found
        }
    }

    //Extracts Phone number from string provided
    public String extractPhoneNumber(String gymLocationPhoneElement) {
        String phonePattern = "\\(\\d{3}\\)\\s*\\d{3}-\\d{4}"; //(###) ###-####
        Pattern pattern = Pattern.compile(phonePattern);
        Matcher matcher = pattern.matcher(gymLocationPhoneElement);
        if (matcher.find()) {
            return matcher.group(); //Sends back the matched part
        } else {
            return null; //Sends back null, if no matches found
        }
    }

    //From the string, extracts the Street Name, City, Province and Postal Code
    public String[] extractAddressComponents(String gymLocationAddressElement) {
        String addressPattern = "(.*?)(?:,\\s*([A-Za-z ]+))?,\\s*([A-Z]{2})\\s*(?:-)?\\s*([A-Z]\\d[A-Z]\\s*\\d[A-Z]\\d)$";

        Pattern pattern = Pattern.compile(addressPattern);
        Matcher matcher = pattern.matcher(gymLocationAddressElement);
        if (matcher.find()) {
            String streetName = matcher.group(1) != null ? matcher.group(1).trim() : "";
            String city = matcher.group(2) != null ? matcher.group(2).trim() : "";

            String province = matcher.group(3).trim();
            if(province.equals("BC")) province = "British Columbia";
            if(province.equals("MB")) province = "Manitoba";
            if(province.equals("NB")) province = "New Brunswick";
            if(province.equals("NL")) province = "Newfoundland and Labrador";
            if(province.equals("NS")) province = "Nova Scotia";
            if(province.equals("NT")) province = "Northwest Territories";
            if(province.equals("NU")) province = "Nunavut";
            if(province.equals("ON")) province = "Ontario";
            if(province.equals("PE")) province = "Prince Edward Island";
            if(province.equals("QC")) province = "Québec";
            if(province.equals("SK")) province = "Saskatchewan";
            if(province.equals("YT")) province = "Yukon";

            String postalCode = matcher.group(4).trim();
            return new String[]{streetName, city, province, postalCode}; //Sends back the details needed
        } else {
            return null; //Sends back null if match not found
        }
    }

    //Extracts Longitude and Latitude from the google maps URL provided
    public String[] extractCoordinates(String gymPinLocationElement) {
        String coordinatesPattern = "/@(-?\\d+\\.\\d+),(-?\\d+\\.\\d+)";
        Pattern pattern = Pattern.compile(coordinatesPattern);
        Matcher matcher = pattern.matcher(gymPinLocationElement);
        if (matcher.find()) {
            String latitude = matcher.group(1); //Extracts latitude
            String longitude = matcher.group(2); //Extracts longitude
            return new String[]{latitude, longitude}; //Send back the latitude and longitude
        } else {
            return null; //Sends back null if pattern is not matched
        }
    }

    //Used to extract duration for GoodLife Fitness
    public String extractDuration(String duration){
        String regex = "/mo";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(duration);
        if (matcher.find()) {
            return matcher.replaceAll("per month");
        } else {
            return duration; //Sends back original string, as text is not found
        }

    }
}



//import com.google.gson.JsonObject;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Validator {
//
//    //VALIDATORS
//
//    //Validate Province
//    public boolean validateProvince(String province) {
//        if (province == null || province.trim().isEmpty()) {
//            return false; // Input is null or empty
//        }
//
//        province = province.trim();
//        // Define a regex pattern for valid province names
//        //String provincePattern = "^[A-Za-z\\s-]+$";
//        String provincePattern ="^[a-zA-Z]+(?:\\*{0,1})$";
//        Pattern pattern = Pattern.compile(provincePattern);
//        Matcher matcher = pattern.matcher(province);
//        return matcher.matches() && province.length() >= 2 && province.length() <= 50;
//    }
//
//    //Validate City
//    public boolean validateCity(String city) {
//        if (city == null || city.trim().isEmpty()) {
//            return false; // Input is null or empty
//        }
//
//        city = city.trim();
//        // Define a regex pattern for valid province names
//        //String provincePattern = "^[A-Za-z\\s-]+$";
//        String cityPattern ="^[a-zA-Z]+(?:\\*{0})?$";
//        Pattern pattern = Pattern.compile(cityPattern);
//        Matcher matcher = pattern.matcher(city);
//        return matcher.matches() && city.length() >= 2 && city.length() <= 50;
//    }
//
//    //Validate Gym Name
//    public boolean validateGymName(String gymName){
//        String regex = "\\bOpening\\b";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(gymName);
//        if (matcher.find()) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public boolean isValidMembershipPrice(String membershipPrice) {
//        // Define a regex pattern for a valid membership price format (numeric value with two decimal places)
//        //String pricePattern = "^\\d+(\\.\\d{1,2})?$"; // Matches one or more digits followed by an optional decimal point and up to two decimal places
//        String pricePattern = "^\\d+(\\.\\d{1,2})?$"; // Matches a whole number or a numeric value with up to two decimal places
//        Pattern pattern = Pattern.compile(pricePattern);
//        Matcher matcher = pattern.matcher(membershipPrice);
//        return matcher.matches();
//    }
//
//    public void validateGymDetails(JsonObject gymDetailsObject){
//        System.out.println("-------->"+gymDetailsObject);
//
//        // Access keys and values
//        String gymLocationName = gymDetailsObject.get("GymLocationNameElement").getAsString();
//        System.out.println("gymLocationName"+gymLocationName);
//
//        boolean GymNameValidity = validateGymName(gymLocationName);
//        System.out.println("GymNameValidity: "+ GymNameValidity);
//    }
//
//    //FINDING PATTERNS
//    public String extractMembershipDuration(String membershipDuration) {
//        // Define a regex pattern to extract the membership duration
//        String durationPattern = "^Every\\s+\\d+\\s+\\w+"; // Matches "Every [number] [time unit]" format
//        Pattern pattern = Pattern.compile(durationPattern);
//        Matcher matcher = pattern.matcher(membershipDuration);
//        if (matcher.find()) {
//            return matcher.group(); // Return the matched part
//        } else {
//            return null; // Return null if no match found
//        }
//    }
//
//
//    public String extractPhoneNumber(String gymLocationPhoneElement) {
//        // Define a regex pattern to extract the phone number
//        String phonePattern = "\\(\\d{3}\\)\\s*\\d{3}-\\d{4}"; // Matches phone numbers in the format (###) ###-####
//        Pattern pattern = Pattern.compile(phonePattern);
//        Matcher matcher = pattern.matcher(gymLocationPhoneElement);
//        if (matcher.find()) {
//            return matcher.group(); // Return the matched phone number
//        } else {
//            return null; // Return null if no match found
//        }
//    }
//
//    public String[] extractAddressComponents(String gymLocationAddressElement) {
//        //String addressPattern = "(.*?)(?:,\\s*([A-Za-z ]+))?\\s*([A-Z]{2})\\s*(?:-)?\\s*([A-Z]\\d[A-Z]\\s*\\d[A-Z]\\d)$";
//        //String addressPattern = "(.*?)(?:,\\s*([A-Za-z ]+))?\\s*([A-Z]{2})\\s*(?:-)?\\s*([A-Z]\\d[A-Z]\\s*\\d[A-Z]\\d)$";
//        String addressPattern = "(.*?)(?:,\\s*([A-Za-z ]+))?,\\s*([A-Z]{2})\\s*(?:-)?\\s*([A-Z]\\d[A-Z]\\s*\\d[A-Z]\\d)$";
//
//
//
//        Pattern pattern = Pattern.compile(addressPattern);
//        Matcher matcher = pattern.matcher(gymLocationAddressElement);
//        if (matcher.find()) {
//            System.out.println("Inside IF!!!!");
//            String streetName = matcher.group(1) != null ? matcher.group(1).trim() : "";
//            System.out.println("streetName:"+streetName);
//            String city = matcher.group(2) != null ? matcher.group(2).trim() : "";
//            System.out.println("city:"+city);
//
//            String province = matcher.group(3).trim();
//            if(province.equals("BC")) province = "British Columbia";
//            if(province.equals("MB")) province = "Manitoba";
//            if(province.equals("NB")) province = "New Brunswick";
//            if(province.equals("NL")) province = "Newfoundland and Labrador";
//            if(province.equals("NS")) province = "Nova Scotia";
//            if(province.equals("NT")) province = "Northwest Territories";
//            if(province.equals("NU")) province = "Nunavut";
//            if(province.equals("ON")) province = "Ontario";
//            if(province.equals("PE")) province = "Prince Edward Island";
//            if(province.equals("QC")) province = "Québec";
//            if(province.equals("SK")) province = "Saskatchewan";
//            if(province.equals("YT")) province = "Yukon";
//
//            System.out.println("province:"+province);
//            String postalCode = matcher.group(4).trim();
//            System.out.println("postalCode:"+postalCode);
//            return new String[]{streetName, city, province, postalCode};
//        } else {
//            System.out.println("Matcher Failed");
//            return null; // Return null if address format is invalid
//        }
//    }
//
//    public String[] extractCoordinates(String gymPinLocationElement) {
//
//        //Define a regex pattern to extract latitude and longitude from the URL
//        String coordinatesPattern = "/@(-?\\d+\\.\\d+),(-?\\d+\\.\\d+)";
//        Pattern pattern = Pattern.compile(coordinatesPattern);
//        Matcher matcher = pattern.matcher(gymPinLocationElement);
//        if (matcher.find()) {
//            String latitude = matcher.group(1); // Extract latitude
//            String longitude = matcher.group(2); // Extract longitude
//            return new String[]{latitude, longitude}; // Return latitude and longitude as an array
//        } else {
//            return null; // Return null if no match found
//        }
//    }
//
//    public String extractDuration(String duration){
//        String regex = "/mo";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(duration);
//        if (matcher.find()) {
//            // Replace "/mo" with "per month"
//            return matcher.replaceAll("per month");
//        } else {
//            // "/mo" not found, return the original input
//            return duration;
//        }
//
//    }
//}
