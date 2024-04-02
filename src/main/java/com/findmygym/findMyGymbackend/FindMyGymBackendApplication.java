package com.findmygym.findMyGymbackend;
import com.findmygym.findMyGymbackend.invertedIndexing.InvertedIndexing;
import com.findmygym.findMyGymbackend.pageRanking.PageRanking;
import com.findmygym.findMyGymbackend.searchFrequency.SearchFrequency;
import com.findmygym.findMyGymbackend.validators.Validator;
import com.findmygym.findMyGymbackend.wordCounter.WordCounter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.findmygym.findMyGymbackend.utilities.Utilities;
import com.findmygym.findMyGymbackend.spellchecker.Spellchecker;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.List;
import java.util.*;

@SpringBootApplication
public class FindMyGymBackendApplication {

	public static void main(String[] args) throws InterruptedException, IOException {

		InvertedIndexing feature = new InvertedIndexing();
		Utilities utils = new Utilities();
		Spellchecker spellchecker = new Spellchecker();
		PageRanking pageRanking = new PageRanking();
		SearchFrequency searchFrequency = new SearchFrequency();
		Validator validator = new Validator();
		WordCounter wordCounter = new WordCounter();

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println( " =============================================================================== ");
			System.out.println();
			System.out.println("Hello user welcome to \"Find My Gym\"...");

					System.out.print("Enter location: ");
					Scanner scanner1 = new Scanner(System.in);
					String location = scanner1.nextLine().toLowerCase();
					boolean checkForAutoCompletion = utils.endsWithAsterisk(location);

					if(checkForAutoCompletion){
						location = utils.removeAsteriskFromEnd(location);

						boolean isValid = validator.validateCity(location);
						System.out.println(isValid+ " "+ location);
						if(isValid){

								List<String> suggestedWords = feature.autoComplete(location);
								if(suggestedWords == null){
									System.out.println("No Such Cities in our data");
								}
								System.out.println("Did you mean...");
								for(int i = 0; i < Objects.requireNonNull(suggestedWords).size() ; i++){
									System.out.println(i + 1 + " " + utils.capitalizeFirstLetter(suggestedWords.get(i)));
								}
								int choice = scanner1.nextInt();
								List<String> objIds = feature.getObjIds(suggestedWords.get(choice - 1));
								searchFrequency.insertSearchFrequency(suggestedWords.get(choice-1));
								if(objIds.isEmpty()){
									System.out.println("No Gyms in this city");
									break;
								}
								System.out.println("Search results for " + suggestedWords.get(choice - 1));
								utils.printResults(objIds);
								pageRanking.rankGym(objIds);
								searchFrequency.displayTopFrequencies();

								System.out.println("Total Number of gyms in individaul loactions");
								wordCounter.wordCounterGym();
						}else {
							System.out.println("Please give valid input");

						}
					}else{
							List<Map.Entry<String, Integer>> suggestedWords2 = spellchecker.checkSpelling(location);

							if(suggestedWords2.get(0).getValue() == 0){
								List<String> objIds2 = feature.getObjIds(suggestedWords2.get(0).getKey());
								searchFrequency.insertSearchFrequency(suggestedWords2.get(0).getKey());
								System.out.println("Search results for " + suggestedWords2.get(0));
								utils.printResults(objIds2);
								pageRanking.rankGym(objIds2);
								searchFrequency.displayTopFrequencies();

							}
							if(suggestedWords2.get(0) == null){
								System.out.println("No Such Cities in our data");

							}
							System.out.println("Did you mean...");
							for(int i = 0 ; i < suggestedWords2.size() ; i++){
								System.out.println(i + 1  + " " + suggestedWords2.get(i).getKey());
							}
							System.out.println(suggestedWords2.size() + " if you want to try again");
							int choice = scanner1.nextInt();

							List<String> objIds2 = feature.getObjIds(suggestedWords2.get(choice - 1).getKey());
							searchFrequency.insertSearchFrequency(suggestedWords2.get(choice - 1).getKey());
							if(objIds2.isEmpty()){
								System.out.println("No Gyms in this city");

							}
							System.out.println("Search results for " + suggestedWords2.get(choice - 1));
							utils.printResults(objIds2);
							pageRanking.rankGym(objIds2);
							searchFrequency.displayTopFrequencies();
							System.out.println("Total Number of gyms in individaul loactions");
							wordCounter.wordCounterGym();
					}
			System.out.println();
			System.out.println( " =============================================================================== ");


		}


	}

}

