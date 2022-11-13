package assignment1_cmpe501;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OptionsFrame {

    //Game Options frame and its components variables
    private JFrame frame = new JFrame("Game Options");
    private JPanel panel1, panel2, panel3;
    private JLabel DBfile_label, BGcolor_label;
    private JTextField DBfile_TxtField;
    private JComboBox ColorChooser;
    private JButton ChangeBtn, OkBtn, ApplyBtn, CancelBtn;
    //Colors' names array for ComboBox
    private String[] BGColorName = {"NONE", "BLUE", "CYAN", "GRAY", "GREEN",
        "MAGENTA", "ORANGE", "PINK", "WHITE", "YELLOW"};

    private HangmanGameFrame GameFrame;  //parent frame 
    private OptionsCtrl optionsCtrl; // options frame controller and listener

    public OptionsFrame(HangmanGameFrame gameFrame) {
        this.GameFrame = gameFrame;
        optionsCtrl = new OptionsCtrl(this, GameFrame);
    }

    public void setFrame() {
        //Initializes panels and their layout properties then adds them to the frame
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();

        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new GridBagLayout());
        panel3.setLayout(new GridBagLayout());

        panel1.setBounds(7, 10, 620, 50);
        panel2.setBounds(7, 50, 620, 50);
        panel3.setBounds(7, 100, 620, 50);

        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);

        //Initializes panels' components
        DBfile_label = new JLabel("Word database file: ");
        DBfile_TxtField = new JTextField(optionsCtrl.getDBfile());
        ChangeBtn = new JButton("Change");
        BGcolor_label = new JLabel("Background color: ");
        ColorChooser = new JComboBox();
        OkBtn = new JButton("OK");
        ApplyBtn = new JButton("Apply");
        CancelBtn = new JButton("Cancel");
        for (String c : BGColorName) {
            ColorChooser.addItem(c);
        }

        //Adds action listeners to the buttons
        ChangeBtn.addActionListener(optionsCtrl);
        ApplyBtn.addActionListener(optionsCtrl);
        OkBtn.addActionListener(optionsCtrl);
        CancelBtn.addActionListener(optionsCtrl);

        //Adds components to the panels
        panel1.add(DBfile_label);
        panel1.add(DBfile_TxtField);
        panel1.add(ChangeBtn);
        panel2.add(BGcolor_label);
        panel2.add(ColorChooser);
        panel3.add(OkBtn);
        panel3.add(Box.createRigidArea(new Dimension(5, 0)));
        panel3.add(ApplyBtn);
        panel3.add(Box.createRigidArea(new Dimension(5, 0)));
        panel3.add(CancelBtn);

        frame.setLocationRelativeTo(GameFrame.getFrame());
        frame.setSize(650, 200);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
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

    public void setDBfile_label(JLabel DBfile_label) {
        this.DBfile_label = DBfile_label;
    }

    public void setBGcolor_label(JLabel BGcolor_label) {
        this.BGcolor_label = BGcolor_label;
    }

    public void setDBfile_TxtField(JTextField DBfile_TxtField) {
        this.DBfile_TxtField = DBfile_TxtField;
    }

    public void setColorChooser(JComboBox ColorChooser) {
        this.ColorChooser = ColorChooser;
    }

    public void setChangeBtn(JButton ChangeBtn) {
        this.ChangeBtn = ChangeBtn;
    }

    public void setOkBtn(JButton OkBtn) {
        this.OkBtn = OkBtn;
    }

    public void setApplyBtn(JButton ApplyBtn) {
        this.ApplyBtn = ApplyBtn;
    }

    public void setCancelBtn(JButton CancelBtn) {
        this.CancelBtn = CancelBtn;
    }

    public void setBGColorName(String[] BGColorName) {
        this.BGColorName = BGColorName;
    }

    public void setGameFrame(HangmanGameFrame GameFrame) {
        this.GameFrame = GameFrame;
    }

    public void setOptionsCtrl(OptionsCtrl optionsCtrl) {
        this.optionsCtrl = optionsCtrl;
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

    public JLabel getDBfile_label() {
        return DBfile_label;
    }

    public JLabel getBGcolor_label() {
        return BGcolor_label;
    }

    public JTextField getDBfile_TxtField() {
        return DBfile_TxtField;
    }

    public JComboBox getColorChooser() {
        return ColorChooser;
    }

    public JButton getChangeBtn() {
        return ChangeBtn;
    }

    public JButton getOkBtn() {
        return OkBtn;
    }

    public JButton getApplyBtn() {
        return ApplyBtn;
    }

    public JButton getCancelBtn() {
        return CancelBtn;
    }

    public String[] getBGColorName() {
        return BGColorName;
    }

    public HangmanGameFrame getGameFrame() {
        return GameFrame;
    }

    public OptionsCtrl getOptionsCtrl() {
        return optionsCtrl;
    }

}
