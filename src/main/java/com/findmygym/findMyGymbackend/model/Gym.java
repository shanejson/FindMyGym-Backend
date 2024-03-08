package com.findmygym.findMyGymbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Gym {
    @Id
    @GeneratedValue
    private Long id;
    private String province;
    private String city;
    private String name;
    private String fees;
    private String amenities;

}
