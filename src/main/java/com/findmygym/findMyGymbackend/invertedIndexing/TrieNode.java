package com.findmygym.findMyGymbackend.invertedIndexing;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {
    /**
     * -- SETTER --
     *
     *
     * -- GETTER --
     *
     @param c the c to set
      * @return the c
     */
    @Getter
    @Setter
    private char c;
    /**
     * -- GETTER --
     *
     * @return the children
     */
    @Getter
    private final Map<Character,TrieNode> children = new HashMap<>();
    @Setter
    @Getter
    private List<String> objId;
    private boolean isLeaf = false;

    TrieNode(){

    }

    /**
     * @return the isLeaf
     */
    public boolean isLeaf() {
        return isLeaf;
    }

    /**
     * @param isLeaf the isLeaf to set
     */
    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

}
