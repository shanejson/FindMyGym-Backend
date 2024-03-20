package com.findmygym.findMyGymbackend.crawler;

import lombok.AllArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.handler.FilteringWebHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@AllArgsConstructor

public class fit4lessCrawler {
    public void getFit4lessDetails(String provinceName, String cityName) throws InterruptedException{
        System.out.println("Crawling Fit4Less.....");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.fit4less.ca/locations");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        //Province Selection
        driver.findElement(By.id("province-dropdown")).click();
        Thread.sleep(1000);
        WebElement dropdownElement = driver.findElement(By.cssSelector("#province-dropdown > ul > li[data-provname=\"Ontario\"]"));
        Actions actions = new Actions(driver);
        actions.scrollToElement(dropdownElement).perform();
        dropdownElement.click();

        //City Selection
        driver.findElement(By.id("city-dropdown")).click();
        Thread.sleep(1000);
        WebElement cityElement = driver.findElement(By.cssSelector("#city-dropdown > ul > li[data-cityname=\"Brampton\"]"));
        actions.scrollToElement(cityElement).perform();
        cityElement.click();

        //Find Button
        driver.findElement(By.xpath("//*[@id=\"btn-find-your-gym\"]")).click();
        Thread.sleep(1000);
        //Add gym location URLs
        List<String> availableGymLinks = new ArrayList<>();

        //All the Gyms Available
        List<WebElement> availableGyms = driver.findElements(By.className("find-gym__result"));

        //Adding the links in Available Gym Links Array List
        availableGyms.forEach(x -> {
            String GymLink = x.findElement(By.tagName("a")).getAttribute("href");
            availableGymLinks.add(GymLink);
        });

        System.out.println(availableGymLinks);

        //Folder to store HTML files
        String folderPath = "HTMLFilesfit4Less";

        // Create the folder if it doesn't exist
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        //Iterate over the links
        for(String i: availableGymLinks){
            driver.get(i);
            // wait for html to be loaded
            Thread.sleep(5000);

            System.out.println("Visiting: " + i);

            //Get HTML Source
            String pageSource = driver.getPageSource();

            //Gym Location Name
            String lastPath = i.substring(i.lastIndexOf('/') + 1);

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(folderPath+"/"+"fit4less-"+lastPath+".html"));
                writer.write(pageSource);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }



        driver.close();
    }
}
