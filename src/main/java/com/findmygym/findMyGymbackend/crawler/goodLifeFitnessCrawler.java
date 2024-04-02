package com.findmygym.findMyGymbackend.crawler;

import org.openqa.selenium.*;
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
        //System.out.println("Crawling GoodLifeFitness.....");
        try {
            //Chrome Drive initialization
            WebDriver driver = new ChromeDriver();

            //URL to navigate to
            driver.get("https://www.goodlifefitness.com/clubs.html");

            //Setting an implicit wait so the driver can wait for the elements that have to be found & Maximizing
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();



            //Adding user provided city name and province name clicking
            driver.findElement(By.xpath("//*[@id=\"club-search\"]")).sendKeys(cityName + ", " + provinceName);
            Actions actions = new Actions(driver);
            WebElement otherElement = driver.findElement(By.xpath("//*[@id=\"findaclub\"]/div[4]/div[1]/div[1]/div/div[2]/button"));
            actions.moveToElement(otherElement).click().perform();
            Thread.sleep(3000);

            //Array List to store the links of branches for that location
            List<String> availableGymLinks = new ArrayList<>();

            //Adding available gym links into the array list
            List<WebElement> liElements = driver.findElements(By.xpath("//ul[@class='c-card-list js-card-list']/li"));
            for (WebElement liElement : liElements) {
                WebElement aElement = liElement.findElement(By.xpath(".//a[@class='c-card__title-link']"));
                String locationUrl = aElement.getAttribute("href");
                availableGymLinks.add(locationUrl);
            }

            System.out.println("GoodLife Fitness Available Gyms: "+availableGymLinks);


            //HTML files will be stored in the respected folder
            String folderPath = "HTMLFilesGoodLifeFitness";

            //If folder is not present will simply create it
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            //Iterate over the links in the arraylist, access the page source and store it in the folder
            for(String i: availableGymLinks){
                try{
                    driver.get(i);
                    Thread.sleep(5000);
                    System.out.println("Crawling: " + i);
                    String pageSource = driver.getPageSource();
                    String lastPath = i.substring(i.lastIndexOf('/') + 1);

                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(folderPath+"/"+"goodLife-"+lastPath+".html"));
                        writer.write(pageSource);
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } catch (NoSuchElementException e) {
                    // Handle NoSuchElementException when finding elements
                    throw new RuntimeException("NoSuchElementException occurred: " + e.getMessage(), e);
                } catch (TimeoutException e) {
                    // Handle TimeoutException when WebDriverWait times out
                    throw new RuntimeException("TimeoutException occurred: " + e.getMessage(), e);
                } catch (WebDriverException e) {
                    // Handle WebDriverException when navigating to URL
                    throw new RuntimeException("WebDriverException occurred: " + e.getMessage(), e);
                } catch (InterruptedException e) {
                    // Handle InterruptedException when sleeping
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    throw new RuntimeException("InterruptedException occurred: " + e.getMessage(), e);
                }


            }
            System.out.println("Crawling GoodLife Fitness completed.");
            driver.quit();
        }catch (WebDriverException e) {
            // Handling WebDriver initialization failure
            System.err.println("WebDriver: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
