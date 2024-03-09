package com.findmygym.findMyGymbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class GymDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String location;
    private String fees;
    private String amenities;
    private String url;

    public GymDetails(Long id, String name, String location, String fees, String amenities, String url){
        this.id = id;
        this.name = name;
        this.location = location;
        this.fees = fees;
        this.amenities = amenities;
        this.url = url;
    }
}
