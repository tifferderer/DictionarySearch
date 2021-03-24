package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *This class creates a tree object based on words
 * added. Used in the DictionarySearch class
 * @author Tiffany Ferderer
 * @version 1.0
 */
public class Tree {

    public static final int ALPHABET_START = 97;
    private static final int ALPHABET_SIZE = 26;
    private Node root;
    private final Map<Character, Integer> charsToIndices = new HashMap<>();
    private ArrayList<String> partialMatches;
    /**
     *Instantiates the first node of the tree and
     * fills a map for referencing where to place the next
     * Node in the tree.
     */
    public Tree() {
        root = new Node();

        //create a map that hold the key value pairs
        //of alphabetic letters and the respective index
        for (int i = 0; i < ALPHABET_SIZE ; i++) {
            charsToIndices.put((char) (ALPHABET_START+i), i);
        }
    }

    /**
     * Adds a word to the tree structure
     * @param word a string to add
     */
    public void add(String word) {
        //call recursive method
        root = add(root, word);
    }

    private Node add(Node current, String word) {
        //base case
        if(word.isEmpty()) {
            return root;
        }
        //first grab the letter from the string & its index
        char letterToAdd = word.charAt(0);
        int index = charsToIndices.get(letterToAdd);

        //search through the root array to see if the letter has been added already
        if(current.children[index] == null) {
            //new node will take over- current node no longer a leaf
            current.leaf = false;
            current.children[index] = new Node(letterToAdd);
        }
        //flag the last letter to show word being ended
        if(word.length()==1) {
            current.children[index].endOfWord = true;
            //check to see if there are any nodes below the current node
            if(Arrays.equals(current.children[index].children, new Node[ALPHABET_SIZE])) {
                //last node is now a leaf
                current.children[index].leaf = true;
            }
        }
        //if the root array contains the letter, return the node
        return add(current.children[index], word.substring(1)); //word= word.substring(1); remove letter in the word after adding to array.
    }

    /**
     * Checks to see if the word given is part of a word
     * in the tree
     * @param word input
     * @return the word matches
     */
    public String[] contains(String word) {
        //set up a list
        partialMatches = new ArrayList<>();
        //if the user types a space in the search bar
        if(word.contains(" ")) {
            //return an empty list
            //instead of error
            return partialMatches.toArray(new String[0]);
        }
        return contains(root, word, word);
    }

    private String[] contains(Node current, String word, String wordToAdd) {

        //the word in the search bar has been filtered:
        if(word.isEmpty()) {
            //if the user types a word in the dictionary
            if(current.endOfWord) {
                //add to the list
            partialMatches.add(wordToAdd);
        }
            //call method to check for partial matches
           partialMatching(current, wordToAdd, "");
           return partialMatches.toArray(new String[0]);
       }
        //first grab the letter from the string & its index
        char letter = word.charAt(0);
        int index = charsToIndices.get(letter);
       //we want to go through the given word
        if(current.children[index] == null) {
            return partialMatches.toArray(new String[0]);
        }
        //call itself until the word typed is gone through
        return contains(current.children[index], word.substring(1), wordToAdd);
    }

    private void partialMatching(Node current, String wordToAdd, String letter) {
        //dont search through a leaf
        if(!current.leaf) {
            //add the node's character to a string
            letter += current.data;

            //if the current node has any nodes in its array
            for(Node node : current.children) {
                if (node != null) {
                    wordToAdd = findMatches(node, wordToAdd, letter);
                    partialMatching(node, wordToAdd, letter);
                }
            }
        }
    }

    //this method checks the node for an end to the word, then adds the word to
    //a list and return the part of the word
    private String findMatches(Node current, String wordToAdd, String letter) {
        //wordToAdd +=current.data;
        String addition = wordToAdd + letter.substring(1);
        if(current.leaf) {
             partialMatches.add(addition + current.data);
        }
        else if (current.endOfWord) {
            partialMatches.add(addition + current.data);
        }
        return wordToAdd;
    }

    //Node class
    private static class Node {
        private char data;
        private final Node[] children = new Node[ALPHABET_SIZE];
        private boolean endOfWord = false;
        private boolean leaf = false;

        public Node(char letter) {
            data = letter;
        }
        //root node
        public Node() { }

        @Override
        public String toString() {
            return "Node{" + data +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Tree{" +
                "root=" + root +
                '}';
    }
}
