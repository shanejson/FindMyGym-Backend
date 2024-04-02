package com.findmygym.findMyGymbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.List;

@Entity
public class GymDetails {

    @Id
    @GeneratedValue
    private final String ID;
    @Getter
    private final String gym;

    private final String city;
    private final String province;
    private final String GymLocationPhoneElement;

    private final String GymPinLocationElement;
    private final String GymLocationNameElement;
    private final List<String> GymAmenitiesArrayList;
    private final Integer GymAmenitieslength;
    private final String GymLocationAddressElement;
    private final String GymLocationAddressStreetNumber;
    private final String GymLocationAddressStreetName;
    private final String GymLocationAddressCity;
    private final String GymLocationAddressProvince;
    private final String GymLocationAddressPostalCode;
    private final String GymPinLocationLatitude;
    private final String GymPinLocationLongitude;

    private final String membershipName;
    private final String membershipPrice;
    private final String membershipDuration;
//
//    {
//        "membershipName": "4Less Card",
//            "membershipPrice": "7.99",
//            "membershipDuration": "Every 2 Weeks",
//            "ID": "57bcdf7f-db0c-47b9-96df-7a037b23c883",
//            "gym": "Fit4Less",
//            "province": "Ontario",
//            "city": "Brampton",
//            "GymLocationNameElement": "Brampton Shoppers World",
//            "GymLocationAddressElement": "105-499 Main St South, Brampton, ON - L6Y 1N7",
//            "GymLocationAddressStreetNumber": "105-",
//            "GymLocationAddressStreetName": "499 Main St South",
//            "GymLocationAddressCity": "Brampton",
//            "GymLocationAddressProvince": "ON ",
//            "GymLocationAddressPostalCode": "L6Y 1N7",
//            "GymLocationPhoneElement": "(905) 451-1742",
//            "GymPinLocationElement": "https://www.google.ca/maps/dir//Fit4Less+105-499 Main St South,Brampton/@43.667076,-79.738351",
//            "GymPinLocationLatitude": "43.667076",
//            "GymPinLocationLongitude": "-79.738351",
//            "GymAmenitiesArrayList": [
//        "Cooler Drinks",
//                "Hydro Massage Beds",
//                "Massage Chairs",
//                "Tanning",
//                "Black Card Workout Area*",
//                "Wi-Fi"
//    ],
//        "GymAmenitieslength": 6
//    }

    public GymDetails(String id, String name, String location, String city, String province, String phone, String pinLocation, List<String> amenities,
                      String membershipName, String membershipDuration, Integer GymAmenitieslength, String GymLocationAddressElement,String GymLocationAddressStreetNumber,
                      String GymLocationAddressCity,String GymLocationAddressProvince,String GymPinLocationLatitude,String GymPinLocationLongitude,
                      String GymLocationAddressPostalCode, String GymLocationAddressStreetName, String membershipPrice){
        this.ID = id;
        this.gym = name;
        this.GymLocationNameElement = location;
        this.city = city;
        this.province = province;
        this.GymLocationPhoneElement = phone;
        this.GymPinLocationElement = pinLocation;
        this.GymAmenitiesArrayList = amenities;
        this.membershipName = membershipName;
        this.membershipPrice = membershipPrice;
        this.GymAmenitieslength = GymAmenitieslength;
        this.GymLocationAddressElement = GymLocationAddressElement;
        this.GymLocationAddressStreetNumber = GymLocationAddressStreetNumber;
        this.GymLocationAddressCity = GymLocationAddressCity;
        this.GymLocationAddressStreetName = GymLocationAddressStreetName;
        this.GymLocationAddressProvince = GymLocationAddressProvince;
        this.GymLocationAddressPostalCode = GymLocationAddressPostalCode;
        this.GymPinLocationLatitude = GymPinLocationLatitude;
        this.membershipDuration = membershipDuration;
        this.GymPinLocationLongitude = GymPinLocationLongitude;


    }

    public String getID() {
        return ID;
    }

    public String getGymLocationNameElement() {
        return GymLocationNameElement;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getGymLocationPhoneElement() {
        return GymLocationPhoneElement;
    }

    public String getGymPinLocationElement() {
        return GymPinLocationElement;
    }

    public List<String> getGymAmenitiesArrayList() {
        return GymAmenitiesArrayList;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public String getMembershipPrice() {
        return membershipPrice;
    }

    public String getMembershipDuration() {
        return membershipDuration;
    }

    public Integer getGymAmenitieslength() {
        return GymAmenitieslength;
    }

    public String getGymLocationAddressElement() {
        return GymLocationAddressElement;
    }

    public String getGymLocationAddressStreetNumber() {
        return GymLocationAddressStreetNumber;
    }

    public String getGymLocationAddressStreetName() {
        return GymLocationAddressStreetName;
    }

    public String getGymLocationAddressCity() {
        return GymLocationAddressCity;
    }

    public String getGymLocationAddressProvince() {
        return GymLocationAddressProvince;
    }

    public String getGymLocationAddressPostalCode() {
        return GymLocationAddressPostalCode;
    }

    public String getGymPinLocationLatitude() {
        return GymPinLocationLatitude;
    }

    public String getGymPinLocationLongitude() {
        return GymPinLocationLongitude;
    }
}


