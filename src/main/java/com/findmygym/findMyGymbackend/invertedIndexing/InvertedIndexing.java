package com.findmygym.findMyGymbackend.invertedIndexing;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import java.util.regex.Pattern;

///////// INVERTED INDEXING ///////
public class InvertedIndexing {

    ////// ROOT NODE OF THE TRIE ///////
    TrieNode rootNode;

    public InvertedIndexing(){
        rootNode = new TrieNode();
    }


    ///////// METHOD FOR INSERTING WORDS IN TRIE //////////////
    private void insertWord(String word, List<String> objId){
        // TAKING THE ROOT NODE AND STARTING WITH IT
        TrieNode current = rootNode;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            Map<Character,TrieNode> children = current.getChildren();

            //IF THERE IS CHARACTER CHANGE CURRENT WORD TO THIS NODE
            if(children.containsKey(c)){
                current = children.get(c);
            }
            //IF NOT CREATE NEW NODE AND CHANGE THE CURRENT NODE TO NEW NODE
            else{
                TrieNode trieNode = new TrieNode();
                children.put(c, trieNode);
                current = children.get(c);
            }
        }

        //FLAGGING THE WORD END
        current.setLeaf(true);

        //ADDING IDS RELEATED TO THE CITY NAME AT THE END OF THE NODE
        current.setObjId(objId);
    }

    ////////////////////////////////////// METHOD TO SEARCH WORDS IN TRIE AND RETURNS //////////////
    /////////////////////////////////// OBJECT IDS ///////////////////////////////////
    private List<String> searchWord(String word){
        // TAKING ROOT NODE AS CURRENT
        TrieNode current = rootNode;

        for (int i = 0; i < word.length(); i++) {
            Map<Character,TrieNode> children = current.getChildren();
            char c = word.charAt(i);
            // IF THE CHARACTER AT THE NODE EXISTS MOVE TO NEXT NODE
            if(children.containsKey(c)){
                current = children.get(c);
            }
            //ELSE RETURN NULL
            else{
                return null;
            }
        }

        // IT WILL ONLY RETURN OBJECT ID IF THE WORDS ENDS THERE
        if(current.isLeaf() && current!=null){
            return current.getObjId();
        }
        else{
            return null;
        }
    }

    ///////////////////////// THIS METHOD IS USED TO CHECK WHETHER THE PREFIX EXISTS AND IF EXISTS RETURN
    //////////////////////// REMAINING WORDS //////////////////////////////////////
    private List<String> autoCompleteInternal(String prefix){
        TrieNode current = rootNode;

        List<String> suggestedWords = new ArrayList<>();

        for (int i = 0; i < prefix.length(); i++) {
            Map<Character,TrieNode> children = current.getChildren();
            char c = prefix.charAt(i);
            if(children.containsKey(c)){
                current = children.get(c);
            }
            ///// IF THE CHARACTER DOES NOT EXIST AT NODE RETURN
            else{
                return null;
            }
        }

        // HERE WE HAVE CHECKED THAT PREFIX EXIST NOW WE WILL EXTRACT THE DATA USING COMPLETE WORDS METHOD
        completeWords(current,suggestedWords,prefix);

        // RETURNS THIS WORDS
        return suggestedWords;
    }

    private void completeWords(TrieNode currNode, List<String> suggestedWords, String word ){
        // BASE CASE : IF THE NODE IS NULL RETURN
        if(currNode == null) return;

        // IF IT IS LEAF NODE RETURN THE PREFIX AS IT IS
        if(currNode.isLeaf()) suggestedWords.add(word);

        Map<Character, TrieNode> map = currNode.getChildren();

        // RECURSIVELY CALLING THIS METHOD BY APPENDING THE PREFIX WITH CURRENT NODE
        for(Character c:map.keySet()){
            completeWords(map.get(c), suggestedWords, word+String.valueOf(c));
        }
    }

    // GET OBJECT IDS METHOD FOR GETTING THE IDS FROM TRIE FOR THE SEARCH WORDS
    public List<String> getObjIds(String inputWord){
        // EXTRACTING DATA
        Map<String, String > citiesWithId = exctractCitiesWithId();

        // IF DATA IS EMPTY RETURN NULL
        if(citiesWithId.isEmpty()){
            return null;
        }
        for (String city: citiesWithId.keySet()) {

            String cityName = citiesWithId.get(city);
            //System.out.println(city + " " + value);

            List<String> objIds = getIds(citiesWithId, cityName);
            insertWord(cityName, objIds);

        }

        // RETURNING THE OBJECT IDS
        return searchWord(inputWord);

    }

    // AUTO COMPLETE FEATURE
    public List<String> autoComplete(String inputWord){

        //EXTRACTING DATA
        Map<String, String > citiesWithId = exctractCitiesWithId();

        for (String city: citiesWithId.keySet()) {

            String cityName = citiesWithId.get(city);
            //System.out.println(city + " " + value);

            List<String> objIds = getIds(citiesWithId, cityName);
            insertWord(cityName, objIds);

        }

        // RETURNING LIST OF SUGGESTIONS
        return autoCompleteInternal(inputWord);
    }

    // GET IDS METHOD WILL RETURN IDS ASSOCIATED TO THAT CITY
    private List<String> getIds(Map<String, String> data ,String cityName){
        List<String> idsAssociatedToCity = new ArrayList<>();
        for(String id : data.keySet()){

            if(Objects.equals(data.get(id), cityName)){
                idsAssociatedToCity.add(id);
            }
        }

        return idsAssociatedToCity;
    }

    // EXTRACTING DATA FROM TEXT FILE
    private Map<String, String> exctractCitiesWithId(){
        Map<String, String> citiesWithID = new HashMap<>();
        Path path = Paths.get("D:\\Project\\ACC project\\findMyGym2\\findMyGym-backend\\data_for_invertedIndexing.txt");

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                String[] cityWithID = Pattern.compile(",").split(line);
                String city = cityWithID[0].toLowerCase();
                String id = cityWithID[1];
                citiesWithID.put(id, city);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return citiesWithID;
    }
}

