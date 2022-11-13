package assignment1_cmpe501;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HangmanGameFrame {

    //Game frame and its components variables
    private JFrame frame = new JFrame("Hangman Game");
    private JPanel panel1, panel2, panel3;
    private JMenu file, options;
    private JMenuItem NewGame, SaveGame, LoadGame, Exit, SubOptions;
    private JMenuBar mb;
    private JLabel letters[]; // the dashes label
    private JButton alphabetButtons[] = new JButton[26];
    private JTextField guessWord;
    private JButton submitGuess;
    private JLabel RC; //Remaining Chances label
    
    private HangmanGameCtrl Ctrl; // HangmanGame frame controller and listener

    //Constructor when there isn't a specified database file, the program will use the default file
    public HangmanGameFrame() {
        Ctrl = new HangmanGameCtrl(this, null);
    }

    //Constructor when there is a specified database file
    public HangmanGameFrame(String DBfilePath) {
        Ctrl = new HangmanGameCtrl(this, DBfilePath);
    }

    //Constructor when game info is restored from an outside file (Game Loaded)
    public HangmanGameFrame(HangmanGameCtrl Ctrl) {
        this.Ctrl = Ctrl;
    }

    public void setFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 370);
        frame.setResizable(false);
        frame.setLayout(null);
        //Below are the methods to show the frame contents
        menuBar();
        TheWord(Ctrl.getWord().replaceAll(".", "_")); // the dashes' labels
        alphabet(); //alphabet buttons
        guess(); // guessing the whole word at once, text field and submit button
        remainingChance(Ctrl.getChances()); // remaining chances label

        //changes the game BG color if there was a color chosen
        Color c = Ctrl.getBGColor();
        if (c != null) {
            frame.getContentPane().setBackground(c);
            panel1.setBackground(c);
            panel2.setBackground(c);
            panel3.setBackground(c);
        }

        frame.setVisible(true);
    }

    public void menuBar() {
        //initializes MenuBar, Menu, and MenuItem
        mb = new JMenuBar();
        file = new JMenu("File");
        options = new JMenu("Options");
        NewGame = new JMenuItem("New Game", resizeIcon(new ImageIcon(System.getProperty("user.dir") + "\\Icons\\NewGame.png")));
        SaveGame = new JMenuItem("Save Game", resizeIcon(new ImageIcon(System.getProperty("user.dir") + "\\Icons\\SaveGame.png")));
        LoadGame = new JMenuItem("Load Game", resizeIcon(new ImageIcon(System.getProperty("user.dir") + "\\Icons\\LoadGame.png")));
        Exit = new JMenuItem("Exit", resizeIcon(new ImageIcon(System.getProperty("user.dir") + "\\Icons\\Exit.png")));
        SubOptions = new JMenuItem("Oprions", resizeIcon(new ImageIcon(System.getProperty("user.dir") + "\\Icons\\Options.png")));
        //adds MenuItems to Menus
        file.add(NewGame);
        file.add(SaveGame);
        file.add(LoadGame);
        file.add(Exit);
        options.add(SubOptions);
        //adds action listeners
        NewGame.addActionListener(Ctrl);
        SaveGame.addActionListener(Ctrl);
        LoadGame.addActionListener(Ctrl);
        Exit.addActionListener(Ctrl);
        SubOptions.addActionListener(Ctrl);
        //add Menus to MenuBar
        mb.add(file);
        mb.add(options);

        frame.setJMenuBar(mb);
    }

    public void TheWord(String word) {
        //initializes and prepares panel1
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setBounds(10, 0, 770, 150);
        //initializes an array of the same size as the word to be guessed to put the required number of dashes labels
        letters = new JLabel[word.length()];
        //adds dashes labels to the panel then adds the panel to the game frame
        for (int i = 0; i < letters.length; i++) {
            letters[i] = new JLabel(String.valueOf(word.charAt(i) + " "));
            letters[i].setFont(new Font("San-Serif", Font.BOLD, 32));
            panel1.add(letters[i]);
        }
        frame.add(panel1);
    }

    public void alphabet() {
        //initializes and prepares panel2
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2, 0, 5, 5));
        panel2.setBounds(10, 150, 770, 80);
        //adds alphabet buttons to panel2 then adds panel2 to the game frame
        for (char i = 0; i < alphabetButtons.length; i++) {
            char c = (char) (65 + i);
            alphabetButtons[i] = new JButton(Character.toString(c) + "");
            alphabetButtons[i].addActionListener(Ctrl);
            alphabetButtons[i].setSize(100, 50);
            alphabetButtons[i].setFont(new Font("San-Serif", Font.BOLD, 14));
            panel2.add(alphabetButtons[i]);
        }
        frame.add(panel2);
    }

    public void guess() {
        //initializes and prepares panel3
        panel3 = new JPanel();
        panel3.setLayout(new GridLayout(1, 2, 2, 2));
        panel3.setBounds(12, 235, 766, 30);
        //initializes the text field and submit button for guessing the word whole once
        guessWord = new JTextField(letters.length);
        submitGuess = new JButton("Guess");
        submitGuess.addActionListener(Ctrl);
        //adds text field and button to the panel, and panel to the frame
        panel3.add(guessWord);
        panel3.add(submitGuess);
        frame.add(panel3);
    }

    public void remainingChance(int chances) {
        //initializes and sets remaining chance label then adds it to the frame
        RC = new JLabel("Remaining Chance: " + chances);
        RC.setFont(new Font("San-Serif", Font.BOLD, 14));
        RC.setBounds(12, 260, 200, 30);
        frame.add(RC);
    }

    //Method to resize the ImageIcons using Image's getScaledInstance method
    public ImageIcon resizeIcon(ImageIcon icon) {
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        return icon;
    }

    //getters
    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JPanel getPanel2() {
        return panel2;
    }

    public JPanel getPanel3() {
        return panel3;
    }

    public JMenu getFile() {
        return file;
    }

    public JMenu getOptions() {
        return options;
    }

    public JMenuItem getNewGame() {
        return NewGame;
    }

    public JMenuItem getSaveGame() {
        return SaveGame;
    }

    public JMenuItem getLoadGame() {
        return LoadGame;
    }

    public JMenuItem getExit() {
        return Exit;
    }

    public JMenuItem getSubOptions() {
        return SubOptions;
    }

    public JMenuBar getMb() {
        return mb;
    }

    public JLabel[] getLetters() {
        return letters;
    }

    public JButton[] getAlphabetButtons() {
        return alphabetButtons;
    }

    public JTextField getGuessWord() {
        return guessWord;
    }

    public JButton getSubmitGuess() {
        return submitGuess;
    }

    public JLabel getRC() {
        return RC;
    }

    public HangmanGameCtrl getCtrl() {
        return Ctrl;
    }

    //setters
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public void setPanel2(JPanel panel2) {
        this.panel2 = panel2;
    }

    public void setPanel3(JPanel panel3) {
        this.panel3 = panel3;
    }

    public void setFile(JMenu file) {
        this.file = file;
    }

    public void setOptions(JMenu options) {
        this.options = options;
    }

    public void setNewGame(JMenuItem NewGame) {
        this.NewGame = NewGame;
    }

    public void setSaveGame(JMenuItem SaveGame) {
        this.SaveGame = SaveGame;
    }

    public void setLoadGame(JMenuItem LoadGame) {
        this.LoadGame = LoadGame;
    }

    public void setExit(JMenuItem Exit) {
        this.Exit = Exit;
    }

    public void setSubOptions(JMenuItem SubOptions) {
        this.SubOptions = SubOptions;
    }

    public void setMb(JMenuBar mb) {
        this.mb = mb;
    }

    public void setLetters(JLabel[] letters) {
        this.letters = letters;
    }

    public void setAlphabetButtons(JButton[] alphabetButtons) {
        this.alphabetButtons = alphabetButtons;
    }

    public void setGuessWord(JTextField guessWord) {
        this.guessWord = guessWord;
    }

    public void setSubmitGuess(JButton submitGuess) {
        this.submitGuess = submitGuess;
    }

    public void setRC(String remain) {
        RC.setText("Remaining Chance: " + remain);
    }

}
