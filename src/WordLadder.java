import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This program uses BFS (breath first search)that finds the shortest word ladder between two words
 *  It does this by using a hashmap to store the list of words we are processing as a graph
 *  and the neighbors of each word as an adjacency list.
 *  Will start with an empty adjacency list and will be built as needed.
 *  using a queue to store the words to be processed,
 *  and a set to store the words that have been processed.
 * @author Connor Norris & Mike Stoj
 * @edu.uwp.cs.340.course CSCI 340 - Data Structures and Algorithm Design
 * @edu.uwp.cs.340.section 001
 * @edu.uwp.cs.340.assignment 5
 * @bugs Not Complete
 */
public class WordLadder {
    public static void main(String[] args) throws Throwable {

        String start, end;        // the words on which the ladder is based
        Scanner in = new Scanner(System.in);

        // the words in the file, WordNode is class to represent nodes in our graph
        HashMap<String, LinkedList<String>> wordlist = new HashMap<>();

        // Read in the two words
        System.out.println("Enter the beginning word");
        start = in.next();

        System.out.println("Enter the ending word");
        end = in.next();

        // Check length of the words
        int length = start.length();
        if (length != end.length()) {
            System.err.println("ERROR! Words not of the same length.");
            System.exit(1);
        }

        // Read in the appropriate file of words based on the length of start
        readFile(wordlist, start);

        // Search the graph
        breadthFirstSearch(wordlist, start, end);

    }


    /**
     * Reads in the appropriate file of words based on the length of beginWord and stores them in the wordlist (i.e. the graph)
     * HashMap contains empty lists to be populated into adjacency lists using the words as keys.
     *
     * @param wordlist - the graph (i.e. HashMap)
     * @param start    - the beginning word
     * @throws FileNotFoundException - if the file is not found
     */
    private static void readFile(HashMap<String, LinkedList<String>> wordlist, String start) throws FileNotFoundException {
        // Read in the appropriate file of words based on the length of start
        String filename;                                                    // String object to hold the filename
        if (start.length() == 8 || start.length() == 9) {                   // if the word is 8 or 9 characters long
            filename = "words." + start.length() + "." + start.length();    // set the filename to words.length.length
        } else {
            filename = "words." + start.length();                           // set the filename to words.length
        }

        try {
            File myObj = new File(filename);                                // create a new file object
            Scanner myReader = new Scanner(myObj);                          // create a new scanner object
            while (myReader.hasNextLine()) {                                // while there are still lines to read
                String word = myReader.nextLine();                          // read in the next line
                wordlist.put(word.trim(), new LinkedList<>());              // add the word to the hashmap

            }
            myReader.close();                                               // close the scanner
        } catch (FileNotFoundException e) {                                 // catch the exception
            System.out.println("An error occurred.");                       // if the file is not found
            e.printStackTrace();                                            // print the stack trace
        }
    }

    /**
     * this method constructs a list that returns a list of all the words that are one letter away from the given word
     *
     * @param currentWord  - the current word
     * @param words - the hashmap of words
     * @return - list of words 1 letter away
     */
    private static LinkedList<String> getNeighbours(String currentWord, HashMap<String, LinkedList<String>> words) {
        LinkedList<String> visited = new LinkedList<>();   // list of neighbours to be returned
        char[] chars = currentWord.toCharArray();          // array of characters in the current word
        Set<String> wordSet = words.keySet();              // set of all words in the wordlist
        // parse through the array
        for (int i = 0; i < chars.length; i++) {
            char ogChar = chars[i];                         // save the original character off in a temp variable
            for (char ch = 'a'; ch <= 'z'; ch++) {          // loop through all the letters in alphabet
                if (ch == ogChar) {                         // if the current letter is the same as the original letter, skip it
                    continue;
                }
                chars[i] = ch;                              // change the current letter to the next letter in the alphabet
                String nextWord = String.valueOf(chars);    // convert the array of characters to a string
                if (wordSet.contains(nextWord)) {           // if the word is in the wordlist, add it to the neighbours list
                    visited.add(nextWord);                  // add the word to the neighbours list
                }
            }
            chars[i] = ogChar;                              // reset the current letter to the original character (i.e. letter)
        }
        return visited;                                     // return the neighbours list
    }

    /**
     * traverseLadder -
     *
     * @param beginWord - the word to start the search from
     * @param words     - the hashmap of words to search through
     * @param distance  - the distance from the start word to the end word
     * @return the path from the start word to the end word
     */
    private static LinkedList<String> traverseLadder(String beginWord, HashMap<String, LinkedList<String>> words, Map<String, Integer> distance) {
        String currentWord = beginWord;                                // current word to be processed (starting with beginWord)
        LinkedList<String> pathToTarget = new LinkedList<>();          // path to return - if path exists
        pathToTarget.add(currentWord);                                 // add the current word to the path
        // while not at start node, find a node that is closer and move back to it.
        while (distance.get(currentWord) != 0) {
            LinkedList<String> neighbours = words.get(currentWord);       // get the neighbors of the current word
            for (String each : neighbours) {                              // iterate through the neighbors
                if (distance.get(each) != null) {
                    if (distance.get(each) < distance.get(currentWord)) { // if the neighbor is closer
                        currentWord = each;                               // move to the neighbor
                        pathToTarget.add(currentWord);                    // add the neighbor to the path
                        break;                                            // break out of the loop
                    }
                }
            }
        }
        if (!pathToTarget.isEmpty()) {                                      // if there is a path
            return printLadder(pathToTarget);                               // print path
        } else {                                                            // if there is no path
            System.out.print("No word ladder found");                       // print error message
        }
        return pathToTarget;// return the path
    }


    /**
     * printLadder Makes a copy of WordLadder, and prints the word ladder
     * from start to end removing each node as steps through the ladder.
     *
     * @param wordLadder - the word ladder to print
     * @return
     */
    private static LinkedList<String> printLadder(LinkedList<String> wordLadder) {
        LinkedList<String> print = new LinkedList<>(wordLadder); // copy the list
        // print word ladder in reverse order to print from start to end.
        while (!print.isEmpty()) {                              // while not empty print
            String each = print.removeLast();                   // remove last element
            System.out.print(each);                             // print the word
            if (print.size() > 0) {                             // if not last element
                System.out.print(" -> ");                       // print arrow indicating next word in traversal
            }
        }
        return print;
    }

    /**
     * This method searches the wordlist as a graph by performing a breadth first search on the graph
     *  to find the shortest path from start to end. Graph is built on-the-fly to find the first occurrence of the
     *  shortest path to end word.
     *  If no path is found, returns null.
     *  If a path is found, returns the path.
     *  If the start or end words are not in the graph, returns null.
     * @param words - the graph
     * @param beginWord - the start node
     * @param endWord - the end node (i.e. target word)
     * @return the path from start to end
     */
    private static LinkedList<String> breadthFirstSearch(HashMap<String, LinkedList<String>> words, String beginWord, String endWord) {
        Queue<String> queue = new LinkedList<>();           // queue to hold words
        Map<String, Integer> distance = new HashMap<>();    // map to hold distance from beginWord
        queue.add(beginWord);                               // add beginWord to queue
        distance.put(beginWord, 0);                         // set distance of beginWord to 0
        // continue until search is completed or path is found
        while (!queue.isEmpty()) {                          // while queue is not empty
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();                 // get word from queue
                if (currentWord != null) {
                    //get neighbours and add to hashmap set to build out graph
                    LinkedList<String> neighbours = getNeighbours(currentWord, words);
                    words.put(currentWord, neighbours);                             // add neighbours to hashmap
                    // break and return if path found
                    if (currentWord.equals(endWord)) {
                        return traverseLadder(currentWord, words, distance);       // return path
                    } else {                                                // else continue search
                        for (String each : neighbours) {                     // for each neighbour
                            if (!distance.containsKey(each)) {              // if neighbour not in distance map
                                queue.add(each);                            // add neighbour to queue
                                distance.put(each, distance.get(currentWord) + 1); // set distance of neighbour to distance of currentWord + 1
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

}





