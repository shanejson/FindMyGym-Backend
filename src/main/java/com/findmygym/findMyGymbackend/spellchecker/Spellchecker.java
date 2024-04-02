package com.findmygym.findMyGymbackend.spellchecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Spellchecker {
    public List<Map.Entry<String, Integer>> checkSpelling(String userInput){
        File outputFile = new File("locations.txt");

        if(outputFile.exists()){
//            System.out.println("Words List Exists!");
            try {
                Set<String> words = readWordsFromFile(String.valueOf(outputFile));


                List<Map.Entry<String, Integer>> closestWords = findClosestWords(userInput, words);

//                System.out.println("Did you mean:");
//                for (Map.Entry<String, Integer> entry : closestWords) {
//                    System.out.println(capitalizeFirstLetter(entry.getKey()));// + " - Edit Distance: " + entry.getValue());
//                }

                return closestWords;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //scanner.close();
            }
        }else{
            System.out.println("Words List Do Not Exists!");
            return null;
        }
        return null;
    }

    private List<Map.Entry<String, Integer>> findClosestWords(String userInput, Set<String> words) {
        Map<String, Integer> wordDistances = new HashMap<>();
        for (String word : words) {
            int distance = calculateEditDistance(userInput, word);
            wordDistances.put(word, distance);
        }
        List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordDistances.entrySet());
        Collections.sort(sortedWords, Comparator.comparingInt(Map.Entry::getValue));
        return sortedWords.subList(0, Math.min(3, sortedWords.size()));
    }

    private int calculateEditDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

    private Set<String> readWordsFromFile(String filePath) throws IOException {
        Set<String> words = new HashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] lineWords = line.trim().split("\\s+");
            words.addAll(Arrays.asList(lineWords));
        }
        reader.close();
        return words;
    }

    private String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }


}
