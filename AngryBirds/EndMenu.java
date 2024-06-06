import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;


public class EndMenu extends JPanel {
    private JLabel highScoreText;
    private JLabel highScorePoints;
    private JButton quitButton;
    private JButton mainMenuButton;
    private Image background;

    public EndMenu() {
        // call the superclass for JPanel
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        try {
            background = ImageIO.read(getClass().getResource("./Images/EndMenu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Instantiate instance variables
        highScoreText = new JLabel("High Score!");
        
        // assigns highscore based on user value
        highScorePoints = new JLabel();

        quitButton = new JButton("Quit");

        // If the quitButton is pressed, end the program
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(-1);
            }
        });

        // If the mainMenuButton is pressed switch the scene to the startMenu
        mainMenuButton = new JButton("Return to Main Menu");

        add(highScoreText);
        add(highScorePoints);

        // Ensure quitButton and mainMenuButton are on the same line
        JPanel jPanel = new JPanel();
        jPanel.add(quitButton);
        jPanel.add(mainMenuButton);

        add(jPanel);
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            Dimension size = getSize();
            g.drawImage(background, 0, 0, size.width, size.height, null);
        }
    }

    // Set the actionListener for mainmenu
    public void setActionListener(ActionListener endMenuListener) {
        mainMenuButton.addActionListener(endMenuListener);
    }

    public void setHighScore(int score) {
        highScorePoints.setText("" + score);
        this.revalidate();
        this.repaint();
    }
}
