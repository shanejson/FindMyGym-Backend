package com.findmygym.findMyGymbackend.searchFrequency;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SearchFrequency {
    public void insertSearchFrequency(String userInput){
        //File outputFile = new File("C:\\Users\\banwa\\OneDrive\\Desktop\\ACC\\Final Project\\Spell checker.txt");
        File outputFile = new File("inputRecords.txt");

        if(outputFile.exists()) {
            // Reading from a text file
            try {
//                Scanner scanner = new Scanner(outputFile);
//                while (scanner.hasNext()) {
//                    String word = scanner.next();
                insert(userInput.toLowerCase()); // Convert the word to lowercase before inserting
//                }
//                scanner.close();
//            } catch (FileNotFoundException e) {
//                System.err.println("File not found: " + e.getMessage());
//                return;
//            }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //String searchWord = userInput.toLowerCase(); // Convert input to lowercase
            //int frequency = search(searchWord);
            //System.out.println("The word '" + searchWord + "' appears " + frequency + " times in the list.");

            // Display top 10 frequencies
            //displayTopFrequencies();
        }else{
            System.out.println("User input record does not exist!");
        }
    }

    public class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;
        int frequency;

        TrieNode() {
            children = new TrieNode[62]; // Assuming lowercase letters, uppercase letters, and digits
            isEndOfWord = false;
            frequency = 0;
        }
    }

    public TrieNode root = new TrieNode(); // Initialize root here

    private void insert(String word) {
        TrieNode curr = root;
        for (char ch : word.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                int index;
                if (Character.isUpperCase(ch)) {
                    index = ch - 'A' + 26; // Map uppercase letters to indices 26 to 51
                } else if (Character.isLowerCase(ch)) {
                    index = ch - 'a'; // Map lowercase letters to indices 0 to 25
                } else {
                    index = ch - '0' + 52; // Map digits to indices 52 to 61
                }
                if (index < 0 || index >= 62) {
                    // Skip characters that are not letters or digits
                    continue;
                }
                if (curr.children[index] == null) {
                    curr.children[index] = new TrieNode();
                }
                curr = curr.children[index];
            }
        }
        curr.isEndOfWord = true;
        curr.frequency++;
    }

    private int search(String word) {
        TrieNode curr = root;
        for (char ch : word.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                int index;
                if (Character.isUpperCase(ch)) {
                    index = ch - 'A' + 26; // Map uppercase letters to indices 26 to 51
                } else if (Character.isLowerCase(ch)) {
                    index = ch - 'a'; // Map lowercase letters to indices 0 to 25
                } else {
                    index = ch - '0' + 52; // Map digits to indices 52 to 61
                }
                if (index < 0 || index >= 62 || curr.children[index] == null) {
                    return 0; // Word not found
                }
                curr = curr.children[index];
            } else {
                return 0; // Word not found
            }
        }
        return curr.frequency;
    }

    public void displayTopFrequencies() {
        List<Map.Entry<String, Integer>> frequencyList = new ArrayList<>();
        collectFrequencies(root, "", frequencyList);

        // Sort the list by frequency in descending order
        frequencyList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Display top 10 frequencies
        System.out.println("People also search for");
        int count = 0;
        for (Map.Entry<String, Integer> entry : frequencyList) {
            if (count < 10) {
                System.out.println(capitalizeFirstLetter(entry.getKey()) + ": " + entry.getValue());
                count++;
            } else {
                break;
            }
        }
    }

    private void collectFrequencies(TrieNode node, String word, List<Map.Entry<String, Integer>> frequencyList) {
        if (node == null)
            return;
        if (node.isEndOfWord) {
            frequencyList.add(new AbstractMap.SimpleEntry<>(word, node.frequency));
        }
        for (int i = 0; i < node.children.length; i++) {
            char ch = (char) (i < 26 ? i + 'a' : (i < 52 ? i - 26 + 'A' : i - 52 + '0'));
            collectFrequencies(node.children[i], word + ch, frequencyList);
        }
    }

//    public static void main(String[] args) {
//        SearchFrequency searchFrequency = new SearchFrequency();
//        searchFrequency.searchFrequency("");//Empty string is passed to skip the word search
//    }

    private static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}

