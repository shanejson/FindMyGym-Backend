package com.findmygym.findMyGymbackend.invertedIndexing;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import java.util.regex.Pattern;
public class InvertedIndexing {
    TrieNode rootNode;

    public InvertedIndexing(){
        rootNode = new TrieNode();
    }

    private void insertWord(String word, List<String> objId){
        TrieNode current = rootNode;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            Map<Character,TrieNode> children = current.getChildren();
            if(children.containsKey(c)){
                current = children.get(c);
            }
            else{
                TrieNode trieNode = new TrieNode();
                children.put(c, trieNode);
                current = children.get(c);
            }
        }
        current.setLeaf(true);
        current.setObjId(objId);
    }

    private List<String> searchWord(String word){
        TrieNode current = rootNode;
        for (int i = 0; i < word.length(); i++) {
            Map<Character,TrieNode> children = current.getChildren();
            char c = word.charAt(i);
            if(children.containsKey(c)){
                current = children.get(c);
            }
            else{
                return null;
            }
        }

        if(current.isLeaf() && current!=null){
            return current.getObjId();
        }
        else{
            return null;
        }
    }

    private boolean searchPrefix(String prefix){
        TrieNode current = rootNode;
        String autoCompletedWord = "";
        for (int i = 0; i < prefix.length(); i++) {
            Map<Character,TrieNode> children = current.getChildren();
            char c = prefix.charAt(i);
            if(children.containsKey(c)){
                current = children.get(c);
            }
            else{
                return false;
            }
        }
        return true;
    }
    private List<String> autoCompleteInternal(String prefix){
        TrieNode current = rootNode;

        List<String> suggestedWords = new ArrayList<>();
        for (int i = 0; i < prefix.length(); i++) {
            Map<Character,TrieNode> children = current.getChildren();
            char c = prefix.charAt(i);
            if(children.containsKey(c)){
                current = children.get(c);
            }
            else{
                return null;
            }
        }
        completeWords(current,suggestedWords,prefix);
        return suggestedWords;
    }

    private void completeWords(TrieNode currNode, List<String> suggestedWords, String word ){
        if(currNode == null) return;

        if(currNode.isLeaf()) suggestedWords.add(word);

        Map<Character, TrieNode> map = currNode.getChildren();
        for(Character c:map.keySet()){
            completeWords(map.get(c), suggestedWords, word+String.valueOf(c));
        }
    }

    public List<String> getObjIds(String inputWord){

        Map<String, String > citiesWithId = exctractCitiesWithId();

        for (String city: citiesWithId.keySet()) {

            String cityName = citiesWithId.get(city);
            //System.out.println(city + " " + value);

            List<String> objIds = getIds(citiesWithId, cityName);
            insertWord(cityName, objIds);

        }

        return searchWord("windsor");

    }

    public List<String> autoComplete(String inputWord){
        Map<String, String > citiesWithId = exctractCitiesWithId();

        for (String city: citiesWithId.keySet()) {

            String cityName = citiesWithId.get(city);
            //System.out.println(city + " " + value);

            List<String> objIds = getIds(citiesWithId, cityName);
            insertWord(cityName, objIds);

        }
        return autoCompleteInternal("win");
    }

    private List<String> getIds(Map<String, String> data ,String cityName){
        List<String> idsAssociatedToCity = new ArrayList<>();
        for(String id : data.keySet()){

            if(Objects.equals(data.get(id), cityName)){
                idsAssociatedToCity.add(id);
            }
        }

        return idsAssociatedToCity;
    }
    public Map<String, String> exctractCitiesWithId(){
        Map<String, String> citiesWithID = new HashMap<>();
        Path path = Paths.get("D:\\Project\\ACC project\\findMyGym-backend\\data_for_invertedIndexing.txt");

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

