package com.findmygym.findMyGymbackend.controller;

import com.findmygym.findMyGymbackend.crawler.fit4lessCrawler;
import com.findmygym.findMyGymbackend.crawler.planetFitnessCrawler;
import com.findmygym.findMyGymbackend.crawler.goodLifeFitnessCrawler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Crawler_Controller {

    private final fit4lessCrawler fit4lessCrawler = new fit4lessCrawler();

    private final planetFitnessCrawler planetFitnessCrawler = new planetFitnessCrawler();

    private final goodLifeFitnessCrawler goodLifeFitnessCrawler = new goodLifeFitnessCrawler();

    @GetMapping("/fit4lessCrawl")
    public String fit4lessCrawl() throws InterruptedException{
        //fit4lessCrawler.getFit4lessPricing();
        fit4lessCrawler.getFit4lessDetails("Ontario", "Windsor");
        return "Crawl Completed";
    }

    @GetMapping("/goodLifeFitnessCrawl")
    public String goodlifeCrawl() throws InterruptedException{
        //goodLifeFitnessCrawler.getGoofLifeFitnessDetails();
        return "Crawl Completed";
    }

    @GetMapping("/planetFitnessCrawl")
    public String planetFitnessCrawl() throws InterruptedException{
        //goodLifeFitnessCrawler.getGoofLifeFitnessDetails();
        planetFitnessCrawler.getPlanetFitnessDetails("Ontario", "Windsor");
        return "Crawl Completed";
    }


}
