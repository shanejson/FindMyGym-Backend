package com.findmygym.findMyGymbackend.invertedIndexing;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

////////////////////////////// TRIE NODE IMPLEMENTATION ///////////////////////////////
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

    // THE CHARACTER THAT WILL BE STORED AT NODE
    private char c;
    /**
     * -- GETTER --
     *
     * @return the children
     */
    @Getter

    // CHARACTER AND NEXT TRIE NODE
    private final Map<Character,TrieNode> children = new HashMap<>();
    @Setter
    @Getter

    // LIST OF OBJECT IDS
    private List<String> objId;

    // FLAG THAT INDICATES END OF WORD
    private boolean isLeaf = false;

    TrieNode(){

    }

    /**
     * @return the isLeaf
     */

    // RETURNS LEAF
    public boolean isLeaf() {
        return isLeaf;
    }

    // SET LEAF

    /**
     * @param isLeaf the isLeaf to set
     */
    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

}
