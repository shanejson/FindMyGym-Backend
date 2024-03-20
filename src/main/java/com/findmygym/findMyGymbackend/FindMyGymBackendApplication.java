package com.findmygym.findMyGymbackend;
import com.findmygym.findMyGymbackend.crawler.fit4lessCrawler;
import com.findmygym.findMyGymbackend.crawler.planetFitnessCrawler;
import com.findmygym.findMyGymbackend.crawler.goodLifeFitnessCrawler;
import com.findmygym.findMyGymbackend.extractor.wordExtractor;
import com.findmygym.findMyGymbackend.complete.wordCompletion;
import com.findmygym.findMyGymbackend.model.GymDetails;
import com.findmygym.findMyGymbackend.parser.planetFitnessParser;
import com.findmygym.findMyGymbackend.searchFrequency.SearchFrequency;
import com.findmygym.findMyGymbackend.spellchecker.spellchecker;
import com.findmygym.findMyGymbackend.validators.Validator;
import com.findmygym.findMyGymbackend.wordCounter.WordCounter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.findmygym.findMyGymbackend.parser.fit4lessParser;
import com.findmygym.findMyGymbackend.parser.goodLifeFitnessParser;

import java.io.IOException;
import java.util.List;
import java.util.*;

@SpringBootApplication
public class FindMyGymBackendApplication {

	//public static void main(String[] args) {
	//	SpringApplication.run(FindMyGymBackendApplication.class, args);
	//}
	public static void main(String[] args) throws InterruptedException, IOException {

//		Scanner sc = new Scanner(System.in);
//
//		while (true) {
//			System.out.println(" ");
//			System.out.println("------------------------------------");
//			System.out.println("          Welcome to FindMyGym       ");
//			System.out.println("------------------------------------");
//
//			System.out.println("Press 1 to Crawl a Website.");
////			System.out.println("Press 2 for inverted indexing.");
////			System.out.println("Press 3 to check frequency count of each gym in the data.");
////			System.out.println("Press 4 to check Page Ranking");
////			System.out.println("Press 5 to check spelling");
//			System.out.println("Press 6 to complete a word");
////			System.out.println("Press 7 to check for a search frequency");
////			System.out.println("Press 8 to find pattern using regular expression");
////			System.out.println("Press 9 to see the top best deals");
////			System.out.println("Press 0 to exit");
//			System.out.println(" ");
//			System.out.print("Enter your choice: ");
//
//
//
//			if(sc.hasNextInt()){
//				int choice = sc.nextInt();
//				sc.nextLine();
//
//				//Getting User input and executing
//				switch (choice) {
//					case 1:
//						System.out.print("Welcome to the crawling feature");
//						String province = "";
//						while(true){
//							//user input
//							while(true){
//								System.out.print("Please enter province: ");
//								province = sc.nextLine().trim();
//								if(Validator.validateAddress(province)){
//									break;
//								}else{
//									System.out.println("Only alphabets are allowed.");
//								}
//							}
//							//Find closest Distance
//						break;
//						}
//
//					case 6:
//						System.out.print("Enter word you want to complete: ");
//						String wordEntered = sc.nextLine();
//						String filePath = "output.json";
//						wordCompletion.vocabluryCreation(wordEntered, filePath);
//						System.out.println("Word removed from the dictionary.");
//						break;
//
//					case 0:
//						System.out.println("Exiting......");
//						sc.close();
//						System.exit(0);
//					default:
//						System.out.println("Invalid choice. Please try again.");
//				}
//
//			}else{
//				System.out.println("Input must be a number.");
//			}
//
//
//		}

		//Crawl Fit4Less
		//fit4lessCrawler fit4lessCrawler = new fit4lessCrawler();
		//fit4lessCrawler.getFit4lessDetails("Ontario", "Windsor");

		//Crawl Planet Fitness
		//planetFitnessCrawler planetFitnessCrawler = new planetFitnessCrawler();
		//planetFitnessCrawler.getPlanetFitnessDetails("Ontario", "Toronto");

		//Crawl Goodlife Fitness
		//goodLifeFitnessCrawler goodLifeFitnessCrawler = new goodLifeFitnessCrawler();
		//goodLifeFitnessCrawler.getGoofLifeFitnessDetails("Ontario", "Toronto");

		//Parse Fit4 Less
		//fit4lessParser fit4lessParser = new fit4lessParser();
		//fit4lessParser.parsefit4Less();

		//Parse Goodlife Fitness
		//goodLifeFitnessParser goodLifeFitnessParser = new goodLifeFitnessParser();
		//goodLifeFitnessParser.parseGoodLifeFitness();

		//Parse Planet Fitness
		//planetFitnessParser planetFitnessParser = new planetFitnessParser();
		//planetFitnessParser.getPlanetFitnessDetails();

		//Creating Text file
		//wordExtractor wordExtractor = new wordExtractor();
		//wordExtractor.extractingWords();

		//Word Completion
		//wordCompletion wordCompletion = new wordCompletion();
		//wordCompletion.completeWords("Tor");

		//Spell Checker
		//spellchecker spellchecker = new spellchecker();
		//spellchecker.SpellChecker("Toranto");

		//Search Frequency
		//SearchFrequency SearchFrequency = new SearchFrequency();
		//SearchFrequency.searchFrequency("Toronto");

		//WordCounter
		WordCounter WordCounter = new WordCounter();
		WordCounter.wordCountFrequency();
	}

}
