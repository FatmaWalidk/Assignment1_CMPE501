package assignment1_cmpe501;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class HangmanGameCtrl implements ActionListener {

    private String word; //The word to be guessed
    private static Color BGColor = null; //Game BG Color
    private String file = null; //to store file path of the file chosen from file chooser
    private Word_DB wordFile; // for interacting with the database

    private String Guessing; // to trace word guessing progress
    private int chances; // to trace the remaining chances 
    private String tries = " "; //to trace disabled alphabet buttons 
    private boolean win = false;

    private HangmanGameFrame GameFrame; // game view (frame)

    public HangmanGameCtrl(HangmanGameFrame gameFrame, String DBfilePath) {
        GameFrame = gameFrame;
        // gets a word from the database using Word class 
        wordFile = new Word_DB(DBfilePath);
        word = wordFile.selectWord();
        System.out.println(word);
        //Calculates the chances of guessing the word
        chances = word.length() / 2;
        Guessing = word;

    }

    public HangmanGameCtrl(String word, String guess, String tries, int chances) {
       wordFile = new Word_DB();
       wordFile.setWord(word);
        this.word = word;
        Guessing = guess;
        this.tries = tries;
        this.chances = chances; // remaining chances
        this.GameFrame = new HangmanGameFrame(this);
        GameFrame.setFrame();
        //Displays the remaining chances 
        GameFrame.setRC(String.valueOf(chances));
        load(tries);// modifies the frame according to the saved game info
    }

    public void newGame() {
        //Closes the current game and opens a new one
        GameFrame.getFrame().dispose();
        new HangmanGameFrame(wordFile.getWordDB().getAbsolutePath()).setFrame();
    }

    public void saveGame(String filePath) {
        //Save the current game info into a file using FileWriter when the save action activated. 
        try {
            FileWriter fw = new FileWriter(filePath);
            //Writes info in one line, separated by commas, and in a KEY:VALUE form
            fw.write("the Word:" + word + ",the Gueese:" + Guessing + ",the Tries:" + tries
                    + ",chances remain:" + chances);
            fw.close();
            JOptionPane.showMessageDialog(GameFrame.getFrame(), "Game Saved!", "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void loadGame(String filePath) {
        //Gets a saved game info and set the frame for it. 
        BufferedReader load = null;
        String loadedGame = null;
        String LG[] = new String[4];
        try {
            load = new BufferedReader(new FileReader(filePath));
            if ((loadedGame = load.readLine()) != null) {
                // Copy saved info into an array.
                LG = loadedGame.split(",");
                System.out.println(loadedGame);
                // closes current game and opens the loaded game
                GameFrame.getFrame().dispose();
                //LG[0] = the word to be guessed, LG[1] = the previous guess of the word, LG[2]= the disenabled alphabet buttons, LG[3]= the remaining chances count 
                new HangmanGameCtrl(LG[0].split(":")[1], LG[1].split(":")[1], LG[2].split(":")[1], Integer.parseInt(LG[3].split(":")[1]));
            }
            load.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void exit() {
        //shows dialog to confirm closing the current game, and saving the game before closing
        int leave, save;
        leave = JOptionPane.showConfirmDialog(GameFrame.getFrame(), "You want to leave the game?", "Sure?", JOptionPane.YES_NO_OPTION);
        if (leave == JOptionPane.YES_OPTION) {
            save = JOptionPane.showConfirmDialog(GameFrame.getFrame(), "Want to save the game?", "Save Game?", JOptionPane.YES_NO_OPTION);
            if (save == JOptionPane.YES_OPTION) {
                this.saveGame(this.FileChooser("save") + ".sav"); //calls saveGame method to save the game then close the game
                GameFrame.getFrame().dispose();
            } else {
                GameFrame.getFrame().dispose(); // close the game without saving 
                System.exit(0);
            }
        }
    }

    /* Method to be used when loading a saved game; to modify the game frame according to the loaded info.
        Paramete: The Alphabet which their buttons will be disenabled.*/
    public void load(String tries) {
        /* Comparing the original word with the guessed loaded word to display the alphabet instead of dashes.  
        e.g. Original word = stars, Guessed = s**rs >> the displayed will be = - t a - -  */
        for (int i = 0; i < word.length(); i++) {
            if (!(String.valueOf(word.charAt(i)).equalsIgnoreCase(String.valueOf(Guessing.charAt(i))))) {
                GameFrame.getLetters()[i].setText(String.valueOf(word.charAt(i)) + " ");
            }
        }
        /* Walks through all chars at the "tries" String (which was loaded from the saved game) 
            and disenables the buttons that contain those chars */
        for (int i = 0; i < tries.toCharArray().length; i++) {
            for (int j = 0; j < GameFrame.getAlphabetButtons().length; j++) {
                if (GameFrame.getAlphabetButtons()[j].getText().equalsIgnoreCase(String.valueOf(tries.charAt(i))) && !(Guessing.contains(String.valueOf(tries.charAt(i))))) {
                    GameFrame.getAlphabetButtons()[j].setEnabled(false);
                    break;
                }
            }
        }
        
        if(chances == 0)
            GameFrame.getSubmitGuess().setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Action perfomed when clicking a menu item
        Object o = e.getSource();
        if (o == GameFrame.getNewGame()) {
            newGame();
        } else if ((o == GameFrame.getSaveGame() && (FileChooser("Save") != null))) {
            //Call the saveGame method after getting (From file chooser) a path and a file name to save the file
            saveGame(file + ".sav");
        } else if (o == GameFrame.getLoadGame() && (FileChooser("Open") != null)) {
            //Call the LoadGame method after getting the file name from file chooser
            loadGame(file);
        } else if (o == GameFrame.getExit()) {
            exit();
        } else if (o == GameFrame.getSubOptions()) {
            //sets the game options frame visible when the options menuitem has been clicked      
            new OptionsFrame(GameFrame).setFrame();
        }

        String letter; //To save the letter that has been clicked       
        //If there are remaining chances and the user didn't lose.
        if (chances != 0 && !win) {
            for (int i = 0; i < GameFrame.getAlphabetButtons().length; i++) {
                // Checks the clicked alphabet button char 
                if (o == GameFrame.getAlphabetButtons()[i]) {
                    letter = GameFrame.getAlphabetButtons()[i].getText().toLowerCase();
                    //Checks the clicked alphabet button char is in the word to be guessed
                    if (Guessing.contains(letter)) {
                        //Replaces the dash label with the correctly guessed char
                        GameFrame.getLetters()[Guessing.indexOf(letter)].setText(letter + " ");
                        //Replaces the correctly guessed char with * to trace out the remaining to-be-guessed chars 
                        this.setGuessing(Guessing.replaceFirst(letter, "*"));
                    } else { // Wrong guessed 
                        chances--;
                        if (chances == 0) { // if there is no more chances displays dialog message about losing the game.
                            JOptionPane.showMessageDialog(GameFrame.getFrame(), "The word is: " + word + "\nGood Game! Try Agian.", "Lost",
                                    JOptionPane.INFORMATION_MESSAGE);
                            GameFrame.getSubmitGuess().setEnabled(false); // Disable the whole word guessing
                            System.out.println("GG");
                        }
                    }
                    //Checks if there is another occurrence of the char of the clicked button before disenabling it. 
                    if (!(Guessing.contains(letter))) {
                        GameFrame.getAlphabetButtons()[i].setEnabled(false);
                        tries += letter; //To save the disenabled buttons chars
                    }
                    //If all chars in the word to be guessed are replaced with * then the word has been guessed. Shows the  dialog message about winning
                    if (Guessing.replaceAll(".", "*").equals(Guessing)) {
                        JOptionPane.showMessageDialog(GameFrame.getFrame(), "You Won!!", "Amazing!", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("win!");
                        GameFrame.getSubmitGuess().setEnabled(false);
                        win = true;
                    }
                    break;
                }
            }
            //Modifying the remaining chances label with the updated chances counter
            GameFrame.setRC(String.valueOf(chances));
        }

        /*If the user submit a whole word guessing, if correct show message "u won",
        and open new game, else show "u lost" and open new game*/
        if (o == GameFrame.getSubmitGuess()) {
            if (!win && !(GameFrame.getGuessWord().getText().isEmpty()) && chances != 0) {
                if (GameFrame.getGuessWord().getText().equalsIgnoreCase(word)) {
                    JOptionPane.showMessageDialog(GameFrame.getFrame(), "You Won!!", "Amazing!", JOptionPane.INFORMATION_MESSAGE);
                    newGame();
                } else {
                    JOptionPane.showMessageDialog(GameFrame.getFrame(), "The word is: " + word + "\nGood Game! Try Agian.", "Lost", JOptionPane.INFORMATION_MESSAGE);
                    newGame();
                }
            }
        }
    }

    public String FileChooser(String s) {
        // initializes a file chooser with the path of the SavedGame folder 
        JFileChooser FC = new JFileChooser("SavedGames");
        FC.setAcceptAllFileFilterUsed(false);
        FC.setDialogTitle(s);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("*.sav", "sav"); //Only *.sav file
        FC.addChoosableFileFilter(restrict);
        int r = FC.showDialog(GameFrame.getFrame(), s);
        // Get the absolute path of the chosen file the return it to be saved or opened
        if (r == JFileChooser.APPROVE_OPTION) {
            file = FC.getSelectedFile().getAbsolutePath();
        }
        return file;
    }

    //getters 
    public String getWord() {
        return word;
    }

    public Color getBGColor() {
        return BGColor;
    }

    public HangmanGameFrame getGameFrame() {
        return GameFrame;
    }

    public String getFile() {
        return file;
    }

    public String getGuessing() {
        return Guessing;
    }

    public int getChances() {
        return chances;
    }

    public String getTries() {
        return tries;
    }

    public Word_DB getWordFile() {
        return wordFile;
    }

    public boolean isWin() {
        return win;
    }

    //setters
    public void setWord(String word) {
        this.word = word;
    }

    public void setBGColor(Color BGColor) {
        HangmanGameCtrl.BGColor = BGColor;
    }

    public void setGameFrame(HangmanGameFrame GameFrame) {
        this.GameFrame = GameFrame;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setGuessing(String Guessing) {
        this.Guessing = Guessing;
    }

    public void setChances(int chances) {
        this.chances = chances;
    }

    public void setTries(String tries) {
        this.tries = tries;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void setWordFile(Word_DB wordFile) {
        this.wordFile = wordFile;
    }

}
