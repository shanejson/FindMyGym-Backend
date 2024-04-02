package com.findmygym.findMyGymbackend.wordCounter;
//
//import com.findmygym.findMyGymbackend.extractor.wordExtractor;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//import java.util.StringTokenizer;
//
//public class WordCounter {
//    public void wordCountFrequency() throws IOException {
//        wordExtractor wordExtractor = new wordExtractor();
//        wordExtractor.extractingProvinceCity();
//
//        File outputFile = new File("province_city_details.txt");
//        if(outputFile.exists()){
//            String text = readFromFile(outputFile);
//
//            // Tokenize and count word frequencies
//            Map<String, Integer> wordFrequencyMap = processText(text);
//
//            // Display word frequencies
//            System.out.println("\nWord Frequencies:");
//            displayWordFrequencies(wordFrequencyMap);
//
//            // Display most frequent words
//            System.out.println("\nMost Frequent Words:");
//            displayMostFrequentWords(wordFrequencyMap);
//
//            // Display lexical richness
//            //displayLexicalRichness(wordFrequencyMap);
//
//        }else{
//            System.out.println("province_city_details Does Not Exists!");
//        }
//    }
//
//    private static String readInteractiveInput(Scanner scanner) {
//        StringBuilder inputText = new StringBuilder();
//        String line;
//        while (!(line = scanner.nextLine()).equalsIgnoreCase("exit")) {
//            inputText.append(line).append("\n");
//        }
//        return inputText.toString().trim();
//    }
//    private static String readFromFile(File filePath) {
//        StringBuilder fileContent = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                fileContent.append(line).append("\n");
//            }
//        } catch (IOException e) {
//            System.err.println("Error reading file. Exiting program.");
//            System.exit(1);
//        }
//        return fileContent.toString().trim();
//    }
//    private static Map<String, Integer> processText(String text) {
//        Map<String, Integer> wordFrequencyMap = new HashMap<>();
//        StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r\f.,;:!?");
//        while (tokenizer.hasMoreTokens()) {
//            String word = tokenizer.nextToken().toLowerCase().replaceAll("[.,;:!?]", "");
//            if (!word.isEmpty()) {
//                wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
//            }
//        }
//        return wordFrequencyMap;
//    }
//    private static void displayWordFrequencies(Map<String, Integer> wordFrequencyMap) {
//        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
//            System.out.println(capitalizeFirstLetter(entry.getKey() + ": " + entry.getValue()));
//        }
//    }
//    private static void displayMostFrequentWords(Map<String, Integer> wordFrequencyMap) {
//        wordFrequencyMap.entrySet().stream()
//                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
//                .limit(5)
//                .forEach(entry -> System.out.println(capitalizeFirstLetter(entry.getKey() + ": " + entry.getValue())));
//    }
//
////    private static void displayLexicalRichness(Map<String, Integer> wordFrequencyMap) {
////        int totalWords = wordFrequencyMap.values().stream().mapToInt(Integer::intValue).sum();
////        int uniqueWords = wordFrequencyMap.size();
////
////        double lexicalRichness = (double) uniqueWords / totalWords;
////        System.out.println("\nLexical Richness: " + lexicalRichness);
////    }
//
//    private static String capitalizeFirstLetter(String word) {
//        if (word == null || word.isEmpty()) {
//            return word;
//        }
//        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
//    }
//
//}


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WordCounter {

    public void wordCounterGym() {


        String text;

        String filePath = "D:\\Project\\ACC project\\findMyGym2\\findMyGym-backend\\province_city_details.txt";

        try {
            // Reading_text from the path c.e from the_file
            text = readFromFile(filePath);

            // Process text to create word frequency map
            Map<String, Integer> wordFrequencyMap = processText(text);

            // Display word frequencies
            System.out.println("\nGym Frequencies:");
            displayWordFrequencies(wordFrequencyMap);

            // Displaying_the_frequent words c.e gyms
            System.out.println("\nDecreasing order of number of gyms in different provinces:");
            displayMostFrequentWords(wordFrequencyMap);


        } catch (IOException e) {
            // Handling_the_file_reading_exception
            System.err.println("not reading the file properly");
        }
    }



    // Method to read text from a file
    private String readFromFile(String filePath) throws IOException {
        //creating the instance for String_BUilder class
        StringBuilder fileContent = new StringBuilder();
        // Using try_with_resources
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            //creating a variable line
            String line;
            // Reading line_by_line from the file
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }
        //returning all the content
        return fileContent.toString().trim();
    }

    // Processing text and counting no_of_occurences
    private Map<String, Integer> processText(String text) {
        //Creating Insyance to use Map data_structure
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        // Tokenizing_the_text using String_Tokenizer
        StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r\f.,;:!?");
        // Iterate through tokens and count frequencies
        while (tokenizer.hasMoreTokens()) {
            // Get the next token (word)
            String word = tokenizer.nextToken().toLowerCase().replaceAll("[.,;:!?]", "");
            // Add word to the map and update frequency
            if (!word.isEmpty()) {
                //Checking and adding the word_to_Map
                wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
            }
        }
        //returning the map with key value pairs
        return wordFrequencyMap;
    }

    // Method to display No of gyms
    private void displayWordFrequencies(Map<String, Integer> wordFrequencyMap) {
        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            // Print total no of gyms in: <word>: <frequency>
            System.out.println("Total no of gyms in " + entry.getKey() + "::: " + entry.getValue());
        }
    }

    // Method to display the most frequent words
    private void displayMostFrequentWords(Map<String, Integer> wordFrequencyMap) {
        // Created a max heap of word-frequency pairs
        Queue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        //Creating the instance and storing all the frequencies in max_heap
        maxHeap.addAll(wordFrequencyMap.entrySet());

        // Display the top 5 most frequent words
        for (int c = 0; c < 5 && !maxHeap.isEmpty(); c++) {
            Map.Entry<String, Integer> entry = maxHeap.poll();
            // Print total no of gyms in: <word>: <frequency>
            System.out.println("Total no of gyms in " + entry.getKey() + "::: " + entry.getValue());
        }
    }


}