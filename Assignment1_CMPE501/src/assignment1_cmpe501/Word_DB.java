package assignment1_cmpe501;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Word_DB {

    private File WordDB; // Database file 
    private String word;
    private String defaultDB = "DB\\Words.db"; //default database file

    public Word_DB() {
        this.WordDB = new File(defaultDB);
    }
  
    public Word_DB(String filePath) {
        if (filePath == null) {
            WordDB = new File(defaultDB);
        } else {
            WordDB = new File(filePath);
        }
    }

    /* Methods to reads (Using BufferedReader obj) the words from the DB and then write it into
    an arrayList to choose a random word (Using Random obj) from the (ArrayList.size) number of words. */
    public String selectWord() {
        ArrayList<String> words = new ArrayList<>();
        BufferedReader get = null;
        Random r = new Random(); //Initializes Random obj
        String tempWord;
        try {
            //initializes BufferedReader.
            get = new BufferedReader(new FileReader(WordDB));
            //Adds the words into the ArrayList
            while ((tempWord = get.readLine()) != null) {
                words.add(tempWord);
            }
            get.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        //choose a random word then return it.
        word = words.get(r.nextInt(words.size()));
        return word;
    }

    //getters 
    public File getWordDB() {
        return WordDB;
    }

    public String getWord() {
        return word;
    }

    //setters 
    public void setWordDB(File WordDB) {
        this.WordDB = WordDB;
    }

    public void setWord(String word) {
        this.word = word;
    }

}
