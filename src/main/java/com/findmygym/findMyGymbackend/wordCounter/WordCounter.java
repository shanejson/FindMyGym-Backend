package com.findmygym.findMyGymbackend.wordCounter;

import com.findmygym.findMyGymbackend.extractor.wordExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WordCounter {
    public void wordCountFrequency() throws IOException {
        wordExtractor wordExtractor = new wordExtractor();
        wordExtractor.extractingProvinceCity();

        File outputFile = new File("province_city_details.txt");
        if(outputFile.exists()){
            String text = readFromFile(outputFile);

            // Tokenize and count word frequencies
            Map<String, Integer> wordFrequencyMap = processText(text);

            // Display word frequencies
            System.out.println("\nWord Frequencies:");
            displayWordFrequencies(wordFrequencyMap);

            // Display most frequent words
            System.out.println("\nMost Frequent Words:");
            displayMostFrequentWords(wordFrequencyMap);

            // Display lexical richness
            //displayLexicalRichness(wordFrequencyMap);

        }else{
            System.out.println("province_city_details Does Not Exists!");
        }
    }

    private static String readInteractiveInput(Scanner scanner) {
        StringBuilder inputText = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equalsIgnoreCase("exit")) {
            inputText.append(line).append("\n");
        }
        return inputText.toString().trim();
    }
    private static String readFromFile(File filePath) {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file. Exiting program.");
            System.exit(1);
        }
        return fileContent.toString().trim();
    }
    private static Map<String, Integer> processText(String text) {
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r\f.,;:!?");
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().toLowerCase().replaceAll("[.,;:!?]", "");
            if (!word.isEmpty()) {
                wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
            }
        }
        return wordFrequencyMap;
    }
    private static void displayWordFrequencies(Map<String, Integer> wordFrequencyMap) {
        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            System.out.println(capitalizeFirstLetter(entry.getKey() + ": " + entry.getValue()));
        }
    }
    private static void displayMostFrequentWords(Map<String, Integer> wordFrequencyMap) {
        wordFrequencyMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(5)
                .forEach(entry -> System.out.println(capitalizeFirstLetter(entry.getKey() + ": " + entry.getValue())));
    }

//    private static void displayLexicalRichness(Map<String, Integer> wordFrequencyMap) {
//        int totalWords = wordFrequencyMap.values().stream().mapToInt(Integer::intValue).sum();
//        int uniqueWords = wordFrequencyMap.size();
//
//        double lexicalRichness = (double) uniqueWords / totalWords;
//        System.out.println("\nLexical Richness: " + lexicalRichness);
//    }

    private static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

}
