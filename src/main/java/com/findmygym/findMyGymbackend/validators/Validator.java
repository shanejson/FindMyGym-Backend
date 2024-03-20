package com.findmygym.findMyGymbackend.validators;

import java.util.regex.Pattern;

public class Validator {

    public static boolean validateAddress(String provinceName){
        String addressPattern = "^[A-Za-z\\s-]+$";
        return Pattern.matches(addressPattern, provinceName);
    }

    //Validate City

    //Validate phone number

    //Validate email

    //Validate
}
