package com.findmygym.findMyGymbackend.pageRanking;

import com.findmygym.findMyGymbackend.model.GymDetails;
import com.findmygym.findMyGymbackend.utilities.Utilities;

import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.stream.Stream;

//////////////////////////////////////// PAGE RANKING ///////////////////////////////////////////
// RANKING PAGE ACCORDING TO THE MOST NUMBER OF GYMS IN THE CITY SEARCHED /////////////
public class PageRanking {
    ////////////////////////////////// RANK GYM METHOD ////////////////////////
    public void rankGym(List<String> objIds) {
        Utilities utils = new Utilities();

        // STORING GYM NAME AND ITS OCCURRENCES

        Map<String, Integer> ranksAccordingToOccurences = new HashMap<>();

        // EXTRACTING THE DATA OF OBJ IDS

        Map<String, GymDetails> results = utils.extractResult(objIds);

        // INCREMENT OCCURRENCES ACCORDING TO CITY NAME

        for (Map.Entry<String, GymDetails> gym : results.entrySet()){

            if(ranksAccordingToOccurences.containsKey(gym.getValue().getGym())){
                ranksAccordingToOccurences.put(gym.getValue().getGym(), ranksAccordingToOccurences.get(gym.getValue().getGym())+1);
            }else {
                ranksAccordingToOccurences.put(gym.getValue().getGym(), 1);
            }
        }

        // PRINTING RESULT ACCORDING TO THE MOST NUMBER OF GYMS IN THAT CITY

        System.out.println("Gym ranked according to most number of gyms in the city");
        System.out.println("Rank  =  Number of gyms");
        ranksAccordingToOccurences.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(System.out::println);

    }
}

