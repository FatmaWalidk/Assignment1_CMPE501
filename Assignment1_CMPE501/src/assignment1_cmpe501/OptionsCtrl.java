package assignment1_cmpe501;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OptionsCtrl implements ActionListener {

    // Colors' constants for game's frame background
    private Color pervColor;
    private Color[] BackgroundColor = {pervColor, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN,
        Color.MAGENTA, Color.ORANGE, Color.PINK, Color.WHITE, Color.YELLOW};
    // for the database file's path
    private String DBfile;

    private OptionsFrame optionsFrame; //options view (frame)
    private HangmanGameFrame GameFrame; // game view (frame)

    public OptionsCtrl(OptionsFrame optionsFrame, HangmanGameFrame GameFrame) {
        this.optionsFrame = optionsFrame;
        this.GameFrame = GameFrame;
        // gets the current background color of the game frame; in case the user canceled color changing
        pervColor = GameFrame.getFrame().getContentPane().getBackground();
        DBfile = this.getDBfile(); // gets the current database file path
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        //Opens file chooser when change button has been clicked
        if (o == optionsFrame.getChangeBtn()) {
            JFileChooser FC = new JFileChooser(DBfile); // current DB file floder
            FC.setAcceptAllFileFilterUsed(false);
            FC.setDialogTitle("Database");
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("*.db", "db"); //only *.db
            FC.addChoosableFileFilter(restrict);
            int r = FC.showDialog(optionsFrame.getFrame(), "OK");
            //changes the shown database file path when Ok has been clicked. Also, stores the new path.
            if (r == JFileChooser.APPROVE_OPTION) {
                DBfile = FC.getSelectedFile().getAbsolutePath();
                optionsFrame.getDBfile_TxtField().setText(DBfile);
            }
        }
        // Get the color selected from the ComboBox
        Color BGColor = (Color) BackgroundColor[optionsFrame.getColorChooser().getSelectedIndex()];

        // Apply button clicked -> changes the game frame BG color to the selected color
        if (o == optionsFrame.getApplyBtn()) {
            changeBGColor(BGColor);
        }
        // Ok button clicked -> changes the game frame BG color and saves the chosen color as static for the BG color
        if (o == optionsFrame.getOkBtn()) {
            changeBGColor(BGColor);
            GameFrame.getCtrl().setBGColor(BGColor);
            // And if a new Database file has been chosen, open a new game with a word from the new file
            if (DBfile != this.getDBfile()) {
                GameFrame.getFrame().dispose();
                GameFrame = new HangmanGameFrame(DBfile);
                GameFrame.setFrame();
            }
            optionsFrame.getFrame().dispose(); //closes the Game Options frame
        }
        // Cancel button clicked -> removes any changes to the game frame BG color then closes the Game Options frame
        if (o == optionsFrame.getCancelBtn()) {
            changeBGColor(pervColor);
            optionsFrame.getFrame().dispose();
        }
    }

    //To change the game BG frame and panels color
    public void changeBGColor(Color c) {
        GameFrame.getFrame().getContentPane().setBackground(c);
        GameFrame.getPanel1().setBackground(c);
        GameFrame.getPanel2().setBackground(c);
        GameFrame.getPanel3().setBackground(c);
    }
    
    // To get the database file path 
    public String getDBfile() {
        return GameFrame.getCtrl().getWordFile().getWordDB().getAbsolutePath();
    }

}
