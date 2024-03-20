package com.findmygym.findMyGymbackend.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class goodLifeFitnessCrawler {
    public void getGoofLifeFitnessDetails(String provinceName, String cityName) throws InterruptedException {
        System.out.println("Crawling GoodLifeFitness.....");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.goodlifefitness.com/clubs.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        //driver.quit();


        //Input Field
        driver.findElement(By.xpath("//*[@id=\"club-search\"]")).sendKeys(cityName + ", " + provinceName);

        //Click search button
        Actions actions = new Actions(driver);
        WebElement otherElement = driver.findElement(By.xpath("//*[@id=\"findaclub\"]/div[4]/div[1]/div[1]/div/div[2]/button"));
        actions.moveToElement(otherElement).click().perform();

        Thread.sleep(3000);

        //Add gym location URLs
        List<String> availableGymLinks = new ArrayList<>();

        // Find all <li> elements within the <ul> tag using XPath
        List<WebElement> liElements = driver.findElements(By.xpath("//ul[@class='c-card-list js-card-list']/li"));

        // Loop through each <li> element to extract location URLs
        for (WebElement liElement : liElements) {
                // Find the <a> tag within the <li> element using XPath
                WebElement aElement = liElement.findElement(By.xpath(".//a[@class='c-card__title-link']"));
                // Extract the href attribute, which contains the location URL
                String locationUrl = aElement.getAttribute("href");
                // Print the location URL
                System.out.println("Location URL: " + locationUrl);
                availableGymLinks.add(locationUrl);

        }

        System.out.println("availableGymLinks: "+availableGymLinks);


        //Folder to store HTML files
        String folderPath = "HTMLFilesGoodLifeFitness";

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
                BufferedWriter writer = new BufferedWriter(new FileWriter(folderPath+"/"+"goodLife-"+lastPath+".html"));
                writer.write(pageSource);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

       driver.quit();
    }
}
