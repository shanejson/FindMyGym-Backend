package com.findmygym.findMyGymbackend.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;

public class planetFitnessCrawler {
    public void getPlanetFitnessDetails(String provinceName, String cityName) throws InterruptedException {
        System.out.println("I am in Planet Fitness");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36");

        // Initialize the WebDriver
        WebDriver driver = new ChromeDriver(options);
        //WebDriver driver = new ChromeDriver();
        driver.get("https://www.planetfitness.ca/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        //Searching for clubs
        driver.findElement(By.xpath("//*[@id=\":R1lb5lel6:\"]")).sendKeys(cityName + ", " + provinceName);
        driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[1]/div[4]/div/form/div/button")).click();

        //Add Gym Location URLs
        List<String> availableGymLinks = new ArrayList<>();

        //All the Gyms Available
        WebElement parentDiv = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div"));
        List<WebElement> availableGym = parentDiv.findElements(By.tagName("a"));

        for(WebElement i : availableGym){
            String href = i.getAttribute("href");
            if(!href.endsWith("offers")){
                availableGymLinks.add(href);
            }

        }

        System.out.println(availableGymLinks);

        //Folder to store HTML files
        String folderPath = "HTMLFilesPlanetFitness";
        // Create the folder if it doesn't exist
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }


        // Visit each URL in the list
        for (String i : availableGymLinks) {

            //This is done to cater to cloud flare
            WebDriver driver1 = new ChromeDriver();
            driver1.get(i);
            driver1.manage().window().maximize();
            System.out.println("Visiting: " + i);



            //Handling Popper
            // Check if the popup appears
            try {
                //WebElement popupElement = driver.findElement(By.cssSelector("[aria-describedby='emergency-alert']"));
                WebElement popupElement = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div"));
                System.out.println("Popper Found: "+ i);

                // If the popup is found, close it
                if (popupElement.isDisplayed()) {
                    //WebElement closeButton = driver.findElement(By.xpath("//button[@name='Close']"));
                    WebElement closeButton = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div[1]/div/button"));
                    closeButton.click();
                    System.out.println("Closed popup on URL: " + i);
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {
                // Popup not found, continue to next URL

            }
            Thread.sleep(3000);
            //Get HTML Source
            String pageSource = driver1.getPageSource();
            //Gym Location Name
            String lastPath = i.substring(i.lastIndexOf('/') + 1);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(folderPath+"/"+"planetFitness-"+lastPath+".html"));
                writer.write(pageSource);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            driver1.quit();
        }

        driver.quit();
    }


}
