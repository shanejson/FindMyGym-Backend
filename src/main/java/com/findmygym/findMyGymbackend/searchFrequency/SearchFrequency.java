package com.findmygym.findMyGymbackend.searchFrequency;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SearchFrequency {
    public void searchFrequency(String userInput){
        File outputFile = new File("word_list.txt");

        if(outputFile.exists()) {
            // Reading from a text file
            try {
                Scanner scanner = new Scanner(outputFile);
                while (scanner.hasNext()) {
                    String word = scanner.next();
                    insert(word.toLowerCase()); // Convert the word to lowercase before inserting
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + e.getMessage());
                return;
            }

            String searchWord = userInput.toLowerCase(); // Convert input to lowercase
            int frequency = search(searchWord);
            System.out.println("The word '" + searchWord + "' appears " + frequency + " times in the list.");

        }else{
            System.out.println("Words List Does Not Exist!");
        }
    }

    static class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;
        int frequency;

        TrieNode() {
            children = new TrieNode[62]; // Assuming lowercase letters, uppercase letters, and digits
            isEndOfWord = false;
            frequency = 0;
        }
    }

    static TrieNode root = new TrieNode(); // Initialize root here

    static void insert(String word) {
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

    static int search(String word) {
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
}
