package com.findmygym.findMyGymbackend;
import com.findmygym.findMyGymbackend.crawler.fit4lessCrawler;
import com.findmygym.findMyGymbackend.crawler.planetFitnessCrawler;
import com.findmygym.findMyGymbackend.crawler.goodLifeFitnessCrawler;
import com.findmygym.findMyGymbackend.extractor.wordExtractor;
import com.findmygym.findMyGymbackend.complete.wordCompletion;
import com.findmygym.findMyGymbackend.model.GymDetails;
import com.findmygym.findMyGymbackend.pageRanking.PageRanking;
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

import static com.findmygym.findMyGymbackend.extractor.wordExtractor.extractingCitiesWithIds;

@SpringBootApplication
public class FindMyGymBackendApplication {
	//public static void main(String[] args) {
	//	SpringApplication.run(FindMyGymBackendApplication.class, args);
	//}
	public static void main(String[] args) throws InterruptedException, IOException {

		extractingCitiesWithIds();

//		Scanner sc = new Scanner(System.in);
//		System.out.println("Enter Province: ");
//
//		String province = sc.next();
//		System.out.println("Province: "+ province);





		//---------------------------SHANE---------------------------------
		//Crawl Fit4Less
		//fit4lessCrawler fit4lessCrawler = new fit4lessCrawler();
		//fit4lessCrawler.getFit4lessDetails("Ontario", "Mississauga");

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

		//--------------------------------------CHARANPREET-----------------------------------
		//WordCounter
		//WordCounter WordCounter = new WordCounter();
		//WordCounter.wordCountFrequency();

		//Spell Checker
		//spellchecker spellchecker = new spellchecker();
		//spellchecker.SpellChecker("Tor");

		//------------------------SOHELEE-----------------------------

		//Search Frequency
		//wordExtractor wordExtractor = new wordExtractor();
		//wordExtractor.storeUserSearches(province);
		//SearchFrequency SearchFrequency = new SearchFrequency();
		//SearchFrequency.searchFrequency("Toronto");

//		PageRanking PageRanking = new PageRanking();
//		PageRanking.PageRanker();


		//--------SMIT-------------------
		////Word Completion
		//wordCompletion wordCompletion = new wordCompletion();
		//wordCompletion.completeWords("Tor");

		//Inverted Indexing

	}

}
