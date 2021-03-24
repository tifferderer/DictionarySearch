package search;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Stores a dictionary that provides definitions given a word
 * or partial matching for words in the dictionary.
 *
 * @author Tiffany Ferderer
 * @version 1.0
 */
public class DictionarySearch implements IDictionary
{

    private final TreeMap<String, String> definitions;
    private final Tree tree;
    /**
     * Creates a new search object with a dictionary loaded and
     * ready for searching.
     */
    public DictionarySearch()
    {
        tree = new Tree();
        definitions = new TreeMap<>();
        try (Scanner reader = new Scanner(new FileInputStream("files/dictionary80.txt"))) {
            //create a scanner

            //read an entire file into a program
            while (reader.hasNextLine()) {
                //read a line
                String line = reader.nextLine();
                String[] wordAndDefinition = line.split(":");
                definitions.put(wordAndDefinition[0], wordAndDefinition[1]);
                tree.add(wordAndDefinition[0]);

               // System.out.println(line);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("error reading from file");
        }
    }

    @Override
    public String getDefinition(String word)
    {
        return definitions.get(word);
    }

    @Override
    public String[] getPartialMatches(String search)
    {
       return tree.contains(search);
    }

    @Override
    public String toString() {
        return "DictionarySearch";
    }
}
