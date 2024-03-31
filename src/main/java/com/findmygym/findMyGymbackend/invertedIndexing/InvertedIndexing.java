package com.findmygym.findMyGymbackend.invertedIndexing;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.w3c.dom.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import java.util.regex.Pattern;
public class InvertedIndexing {
    TrieNode rootNode;

//    public InvertedIndex(){
//        rootNode = new TrieNode();
//    }

    public InvertedIndexing(){
        rootNode = new TrieNode();
    }

    public void insertWord(String word, List<String> objId){
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

    public List<String> searchWord(String word){
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

//    public List<String> autoComplete(String prefix){
//
//    }

//    public void print(TrieNode rootNode,int level, StringBuilder sequence) {
//        if(rootNode.isLeaf()){
//            sequence = sequence.insert(level, rootNode.getC());
//            System.out.println(sequence);
//        }
//
//        Map<Character, TrieNode> children = rootNode.getChildren();
//        Iterator<Character> iterator = children.keySet().iterator();
//        while (iterator.hasNext()) {
//            char character = iterator.next();
//            sequence = sequence.insert(level, character);
//            print(children.get(character), level+1, sequence);
//            sequence.deleteCharAt(level);
//        }
//    }

    public static void main(String args[]){
        InvertedIndexing invertedIndex = new InvertedIndexing();
        Map<String, String > citiesWithId = invertedIndex.exctractCitiesWithId();

        for (String city: citiesWithId.keySet()) {

            String cityName = citiesWithId.get(city);
            //System.out.println(city + " " + value);

            List<String> objIds = invertedIndex.getIds(citiesWithId, cityName);
            invertedIndex.insertWord(cityName, objIds);

        }

        List<String> associatedObjId =invertedIndex.searchWord("windsor");
        System.out.println(associatedObjId);
//        InvertedIndex trie = new InvertedIndex();
//        List<String> objIds = new ArrayList<>();
//
//        objIds.add("10");
//        objIds.add("11");
//
//        trie.insertWord("hello", objIds);
//     ;
//        List<String> objId = trie.searchWord("hello");
////
//        if(objId == null){
//            System.out.println("no ids found");
//        }else {
//            for(String id : objId ){
//                System.out.println(id);
//            }
//        }
    }

    public List<String> getIds(Map<String, String> data ,String cityName){
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
//public class InvertedIndex {
//
//    private TrieNode root;
//
//    public InvertedIndex(){
//        root = new TrieNode('\0');
//    }
//
//    public void insertWord(String word){
//        TrieNode curr = root;
//
//        for (int i = 0 ; i < word.length() ; i++){
//            char c = word.charAt(i);
//            if(curr.children[c - 'a'] == null){
//                curr.children[c-'a'] = new TrieNode(c);
//
//            }
//            curr = curr.children[c-'a'];
//
//        }
//        curr.isWord = true;
//    }
//
//    public TrieNode getNode(String word){
//        TrieNode curr = root;
//
//        for (int i = 0 ; i < word.length() ; i++){
//            char c = word.charAt(i);
//            if(curr.children[c - 'a'] == null){
//                return null;
//            }
//            curr = curr.children[c-'a'];
//        }
//        return curr;
//    }
//
//    public boolean search(String word) {
//        TrieNode node = getNode(word);
//        return node != null && node.isWord;
//    }
//
//
//
//
//
//
//
//
//
//
//
//
////    private TrieNode root;
////    public void insert(String word) {
////        TrieNode current = root;
////
////        for (char l: word.toCharArray()) {
////            current = current.getChildren().computeIfAbsent(l, c -> new TrieNode());
////        }
////        current.setEndOfWord(true);
////    }
//    public static void main(String args[]){
//
//        InvertedIndex trie = new InvertedIndex();
//
//        trie.insertWord("hello");
//        boolean isWordPartOfTrie = trie.search("hello");
//        System.out.println(isWordPartOfTrie);
////        InvertedIndex advanceSearch = new InvertedIndex();
////        //List<String> cityNames ;
////        Path path = Paths.get("D:\\Project\\ACC project\\findMyGym-backend\\data_for_invertedIndexing.txt");
////
////        try {
////            List<String> lines = Files.readAllLines(path);
////
////            for (String line : lines) {
////                String[] cityWithID = Pattern.compile(",").split(line);
////                advanceSearch.insertWords(cityWithID[0], cityWithID[1]);
////            }
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//    }
//
//
//}

//}
