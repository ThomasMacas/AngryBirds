import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;

// TODO: WORK ON CENTERING COMPONENTS + ADDING UI ELEMENTS

public class StartMenu extends JPanel {
    private final static int NUMBER_OF_LEVELS = 3;
    private String[] difficulties;
    private JLabel title;
    private JLabel difficulty;
    private JButton[] difficultyButtons;
    private JButton startButton; 
    private Image background;

    public StartMenu() {
        // call the superclass for JPanel
        super ();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        try {
            background = ImageIO.read(getClass().getResource("./Images/HomeScreen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        title = new JLabel("Angry Birds!", JLabel.CENTER);

        difficulty = new JLabel("Normal", JLabel.CENTER);

        difficulties = new String[]{"Easy", "Normal", "Hard"};

        startButton = new JButton("Start");

        // index = 0 easy, 1 = normal, 2 = hard
        difficultyButtons = new JButton[NUMBER_OF_LEVELS];

        // Create the number of buttons with actionListeners
        for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
            JButton jButton = new JButton(difficulties[i]);
            jButton.addActionListener(new ActionListener() {

                // When a difficulty button is clicked update the text
                public void actionPerformed(ActionEvent ae) {
                    // Ensure the button doesn't glitch if clicked when already that difficulty
                    if (!getDifficulty().equals(jButton.getText())) {
                        difficulty.setText(jButton.getText());
                        rework();
                    }
                }
            });

            difficultyButtons[i] = jButton;
        }
    }

    // Returns the currently selected difficulty 
    public String getDifficulty() {
        return difficulty.getText();
    }

    // Set the actionListener of startButton
    public void setActionListener(ActionListener startMenuListener) {
        startButton.addActionListener(startMenuListener);
    }

    // remove all previous instances of variables on the list and add them back to update the JPanel
    public void rework() {
        removeAll();

        add(title);
        add(difficulty);
        add(startButton);
        
        JPanel jPanel = new JPanel();

        for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
            jPanel.add(difficultyButtons[i]);
        }

        add(jPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            Dimension size = getSize();
            g.drawImage(background, 0, 0, size.width, size.height, null);
        }
    }
}

