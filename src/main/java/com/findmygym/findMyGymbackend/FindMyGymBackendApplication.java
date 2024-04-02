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

			System.out.print("Enter City (Add * at the end to get auto Complete): ");
			Scanner scanner1 = new Scanner(System.in);
			String location = scanner1.nextLine().toLowerCase();
			boolean checkForAutoCompletion = utils.endsWithAsterisk(location);

			if(checkForAutoCompletion){
				location = utils.removeAsteriskFromEnd(location);

				boolean isValid = validator.validateCity(location);

				if(isValid){

					List<String> suggestedWords = feature.autoComplete(location);
					if(suggestedWords == null){
						System.out.println("No Such Cities in our data");
					}
					else {
						System.out.println("Did you mean...");
						for(int i = 0; i < suggestedWords.size() ; i++){
							System.out.println(i + 1 + " " + utils.capitalizeFirstLetter(suggestedWords.get(i)));
						}
						try{
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
						}catch (InputMismatchException e){
							System.out.println("Please give valid input");
						}
					}
				}else {
					System.out.println("Please give valid input");

				}
			}else{
				List<Map.Entry<String, Integer>> suggestedWords2 = spellchecker.checkSpelling(location);

				boolean isValid = validator.validateCity(location);

				if (isValid){
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
						System.out.println(i + 1  + " : " + suggestedWords2.get(i).getKey());
					}
					System.out.println(suggestedWords2.size()+1 + " if you want to try again");
					System.out.println("Select any options from above : ");
					try{
						int choice = scanner1.nextInt();
						if(choice == suggestedWords2.size()+1){
							continue;
						}
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
					}catch (InputMismatchException e){
						System.out.println("Please enter valid input");
					}

				}
				else {
					System.out.println("Give Valid Input");
				}
			}
			System.out.println();
			System.out.println( " =============================================================================== ");
		}



	}


}

