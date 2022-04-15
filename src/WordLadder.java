import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordLadder {
    public static void main(String[] args) throws Throwable {
        String start, end;        // the words on which the ladder is based
        Scanner in = new Scanner(System.in);

        // the words in the file, WordNode is class to represent nodes in our graph
        HashMap<String, WordNode> wordlist = new HashMap<String, WordNode>();

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

    // Method definitions here.
    public static void breadthFirstSearch(HashMap<String, WordNode> wordlist, String start, String end) {
        // Search the graph
        Queue<WordNode> queue = new LinkedList<>();
        queue.offer(new WordNode(start, 1));
        Set<String> visited = new HashSet<>();
        int level = 1;
        int index = 0;
        StringBuffer sb2 = new StringBuffer(wordlist.get(start).getWord());
        while (!queue.isEmpty()) {
            WordNode node = queue.poll();
            String word = node.word;
            int numSteps = node.numSteps;
            visited.add(word);
            if (word.equals(start) && numSteps == 1) {
                System.out.println("The word ladder is: " + word + " " + numSteps);
                //level++;
            }
            if (numSteps == 2 && (level == numSteps || index == 0)) {
                System.out.println("The word ladder is: " + word + " " + numSteps);

            }
            if (index < word.length()) {
                if (node.numSteps >= level || word.charAt(index) == end.charAt(index)) {
                    System.out.println("The word ladder is: " + word + " " + numSteps);
                    sb2 = new StringBuffer(word);
                    index++;
                    level++;
                    System.out.println("Visited: " + word);
                }
            }
            //System.out.println(word);






            if (word.equals(end)) {
                System.out.println("The shortest path is " + numSteps);
                return;
            }
            //char[] arr = word.toCharArray();
            StringBuffer sb = new StringBuffer(word);
            for (int i = 0; i < sb.length(); i++) {
                char temp = sb.charAt(i);
                for (char c = 'a'; c <= 'z'; c++) {
                    if (temp == c) {
                        continue;
                    }
                    sb.setCharAt(i, c);
                    String newWord = sb.toString();
                    if (!wordlist.containsKey(newWord)) {
                        continue;
                    }
                    visited.add(newWord);
                    wordlist.remove(newWord);
                    queue.offer(new WordNode(newWord, numSteps + 1));
                    //level = numSteps;
                    //System.out.println(queue.size());
                    //System.out.println(visited.size());

                }
                sb.setCharAt(i, temp);
            }
        }// END WHILE


        System.out.println("No path found");
    }


    private static void readFile(HashMap<String, WordNode> wordlist, String start) throws FileNotFoundException {
        // Read in the appropriate file of words based on the length of start

        String filename = "words." + start.length();
        FileInOut file = new FileInOut(filename, null, false);


        file.openInFile();
        while (file.inputFileScanner.hasNext()) {
            String word = file.inputFileScanner.next();
            addWord(wordlist, word);
        }

        file.closeInFile();
    }

    private static void addWord(HashMap<String, WordNode> wordlist, String word) {
        // Add a word to the graph
        WordNode node = wordlist.get(word);
        if (node == null) {
            node = new WordNode(word, 0);
        }
        wordlist.put(word, node);
        }


   /* private static class WordNode {
        // A node in the graph
        private String word;
        private Set<WordNode> neighbours;

        public WordNode(String word) {
            this.word = word;
            this.neighbours = new HashSet<WordNode>();
        }

        public String getWord() {
            return word;
        }

        public Set<WordNode> getNeighbours() {
            return neighbours;
        }

        public void addNeighbour(WordNode node) {
            neighbours.add(node);
        }

        public void addNeighbor(WordNode next) {
            neighbours.add(next);
        }

        public String toString() {
            return word;
        }
    }*/
}

class WordNode {
    // A node in the graph
    String word;
    int numSteps;

    public WordNode(String word, int numSteps) {
        this.word = word;
        this.numSteps = numSteps;
    }

    public String getWord() {
        return word;
    }

    public int getNumSteps() {
        return numSteps;
    }

    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
    }

    public String toString() {
        return word;
    }
}

