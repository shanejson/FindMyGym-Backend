package com.findmygym.findMyGymbackend.controller;

import com.findmygym.findMyGymbackend.crawler.fit4lessCrawler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Crawler_Controller {

    private fit4lessCrawler fit4lessCrawler = new fit4lessCrawler();

    @GetMapping("/fit4lessCrawl")
    public String fit4lessCrawl() throws InterruptedException{
        //fit4lessCrawler.getFit4lessPricing();
        fit4lessCrawler.getFit4lessDetails("Ontario", "Windsor");
        return "Crawl Completed";
    }


}
