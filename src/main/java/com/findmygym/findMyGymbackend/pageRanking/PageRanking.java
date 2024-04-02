package com.findmygym.findMyGymbackend.pageRanking;

import com.findmygym.findMyGymbackend.model.GymDetails;
import com.findmygym.findMyGymbackend.utilities.Utilities;

import java.util.*;
import java.util.stream.Stream;

public class PageRanking {
    public void rankGym(List<String> objIds) {
        Utilities utils = new Utilities();
        Map<String, Integer> ranksAccordingToOccurences = new HashMap<>();

        Map<String, GymDetails> results = utils.extractResult(objIds);

        for (Map.Entry<String, GymDetails> gym : results.entrySet()){

            if(ranksAccordingToOccurences.containsKey(gym.getValue().getGym())){
                ranksAccordingToOccurences.put(gym.getValue().getGym(), ranksAccordingToOccurences.get(gym.getValue().getGym())+1);
            }else {
                ranksAccordingToOccurences.put(gym.getValue().getGym(), 1);
            }
        }
        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>();
        sorted_map.putAll(ranksAccordingToOccurences);
        for(Map.Entry<String, Integer> rank : sorted_map.entrySet()){
            System.out.println(rank.getKey() + " " + rank.getValue());
        }
    }
}

