import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * write a program using BFS (breath first search)that finds the shortest word ladder between two words
 *
 * @author Connor Norris, Mike Stoge
 * @edu.uwp.cs.340.course CSCI 340 - Data Structures/Algorithm Design
 * @edu.uwp.cs.340.section 001
 * @edu.uwp.cs.340.assignment 5
 */
public class WordLadder {

    /**
     * A Hashmap for storing the list of words we are processing as a graph
     * and the neighbors of each word as an adjacency list.
     * <p>
     * Will start with an empty adjacency list and will be built as needed.
     */
    public static HashMap<String, LinkedList<String>> words = new HashMap<>(); // adjacency list

    /** method to read words from file into the appropriate hashmap. Will create a hashmap with
     * empty lists for the adjacency lists and the words as keys.
     *
     * @param fileName String name of the file to read.
     * @param words the map to write input to.
     */
    public static void readFile(String fileName, HashMap<String, LinkedList<String>> words) {
        try {
            Scanner tFile = new Scanner(new File(fileName)); // create scanner for file
            while (tFile.hasNext()) {
                // add words to the hashset
                words.put(tFile.next().trim(), new LinkedList<>()); // add word to hashmap
            }
            tFile.close(); // close file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This will find one of the shortest word ladders that spans from the start word until the
     * end word. Will return null if no ladder found.
     *
     * @param beginWord word to begin searching from.
     * @param endWord word to find path to.
     * @param words hashmap of words to use for search.
     * @return linked list of one of the shortest word ladders possible for the two words.
     */
    public static LinkedList<String> findWordLadder(String beginWord, String endWord, HashMap<String, LinkedList<String>> words) {
        // if the word set does not contain beginWord or endWord, return an empty arrayList
        if (!words.containsKey(beginWord) || !words.containsKey(endWord)) {
            System.out.println("Error: a word is not contained in the data set");
            return new LinkedList<>(); // return empty arrayList
        }
        else {
            // Start breadth first search
            return wordLadderBfs(beginWord, endWord, words);
        }
    }

    /**
     * This method will find every single neighbor of a given word in the list. A neighbor is defined as
     * a valid word of even length and with only one letter changed from the original word.
     *
     * @param curr the current node to find adjacent neighbors for.
     * @param words the hashmap of words to use for searching.
     * @return a linked list of all adjacent neighbors of the word.
     */
    private static LinkedList<String> getNeighbors(String curr, HashMap<String, LinkedList<String>> words) {
        // neighbors list to return
        LinkedList<String> neighbors = new LinkedList<>();
        // holds the sequence of characters in the current string
        char[] chars = curr.toCharArray();
        Set<String> wordSet = words.keySet();
        // parse through the array
        for (int i = 0; i < chars.length; i++) {
            char old = chars[i];
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (ch == old) {
                    // end current iteration and continue to next iteration
                    continue;
                }
                chars[i] = ch;
                String nextWord = String.valueOf(chars);
                if (wordSet.contains(nextWord)) {
                    neighbors.add(nextWord);
                }
            }
            chars[i] = old;
        }
        return neighbors;
    }

    /**
     * Handles searching the word list as a graph using Breadth first search. This method will also construct
     * the graph dynamically as it searches to only build the parts of the graph it needs to find the first
     * shortest path to the end word.
     *
     * @param beginWord the word to begin searching for a path from.
     * @param endWord the word end the path at.
     * @param words the list of words to use for the graph.
     * @return the shortest path from the ending word back to the beginning word.
     */
    private static LinkedList<String> wordLadderBfs(String beginWord, String endWord, HashMap<String, LinkedList<String>> words) {
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> distance = new HashMap<>();
        queue.add(beginWord);
        distance.put(beginWord, 0);
        // continue until search is completed or path is found
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (curr != null) {
                    //get neighbors and add to hashmap set to build out graph
                    LinkedList<String> neighbors = getNeighbors(curr, words);
                    words.put(curr, neighbors);
                    // break and return if path found
                    if (curr.equals(endWord)) {
                        return walkWordLadder(curr, words, distance);
                    } else { // else continue search
                        for (String each : neighbors) {
                            if (!distance.containsKey(each)) {
                                queue.add(each);
                                distance.put(each, distance.get(curr) + 1);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Takes in a distance map and walks from an end node back to the start node, and return the list form of the path.
     *
     * @param beginWord word to walk back to the starting node.
     * @param words words hashmap to use to traverse the word graph.
     * @param distance a hashmap of each node on the path back to the start node and their distance from it.
     * @return a list representation of the path back to the start node.
     */
    private static LinkedList<String> walkWordLadder(String beginWord, HashMap<String, LinkedList<String>> words, Map<String, Integer> distance) {
        String curr = beginWord;
        LinkedList<String> path = new LinkedList<>();
        path.add(curr);
        // while not at start node, find a node that is closer and move back to it.
        while (distance.get(curr) != 0) {
            LinkedList<String> neighbors = words.get(curr);
            for (String each: neighbors) {
                if (distance.get(each) != null) {
                    if (distance.get(each) < distance.get(curr)) {
                        curr = each;
                        path.add(curr);
                        break;
                    }
                }
            }
        }
        return path;
    }

    /**
     * Handles printing the list generated from the findWordLadder method in reverse order (start word to end word).
     *
     * @param wordLadder the list to print.
     */
    private static void printWordLadder(LinkedList<String> wordLadder) {
        LinkedList<String> print = new LinkedList<>(wordLadder);
        // print word ladder in reverse order to print from start to end.
        while (!print.isEmpty()) {
            String each = print.removeLast();
            System.out.print(each);
            if (print.size() > 0) {
                System.out.print(" -> ");
            }
        }
    }

    /**
     * Main running method handles taking input for the start and end words and prints out the wordLadder and length.
     * @param args unused.
     */
    public static void main(String[] args) {

        // scanner for user input
        Scanner scan = new Scanner(System.in);

        // ask user for input
        System.out.println("Enter the beginning word");
        String word1 = scan.next();

        System.out.println("Enter the ending word");
        String word2 = scan.next();

        // check if lengths match, and match word length to use correct data set
        if(word1.length() == word2.length()) {
            switch (word1.length()) {
                case 3 -> readFile("words.3", words);
                case 4 -> readFile("words.4", words);
                case 5 -> readFile("words.5", words);
                case 6 -> readFile("words.6", words);
                case 7 -> readFile("words.7", words);
                case 8 -> readFile("words.8.8", words);
                case 9 -> readFile("words.9.9", words);
            }
            LinkedList<String> path = findWordLadder(word1, word2, words);
            if (path != null){
                printWordLadder(path);
            } else {
                System.out.print("No word ladder found");
            }
        }
        else {
            System.out.print("ERROR! Words not of the same length.");
        }
    }
}