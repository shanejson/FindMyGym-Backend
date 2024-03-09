package com.findmygym.findMyGymbackend.controller;

import com.findmygym.findMyGymbackend.model.GymDetails;
import com.findmygym.findMyGymbackend.parser.fit4lessParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class Parser_Controller {
    private fit4lessParser fit4lessParser = new fit4lessParser();
    @GetMapping("/parseFiles")
    public String parseFiles() throws IOException {
        System.out.println("Parsing fit4less Files");
        List<GymDetails> fit4lessGymDetails = fit4lessParser.parsefit4Less();
        return "Parsing Done";
    }
}
