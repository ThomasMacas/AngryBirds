import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameGUI {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Angry Birds");
        User user = new User();
        StartMenu startMenu = new StartMenu();
        startMenu.rework();
        EndMenu endMenu = new EndMenu();

        /* POSSIBLE ISSUE: HOW TO ADD OTHER SCENES... -> Just add other stuff before packing and create sep actionListeners
        eventually make it call another function to deal with switching scenes, just for testing -> need to find difficulty
        eventually */
        ActionListener levelListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                jFrame.getContentPane().removeAll();
                jFrame.revalidate();
                jFrame.repaint();
                endMenu.setHighScore(Integer.parseInt(ae.getActionCommand()));
                jFrame.add(endMenu);
                jFrame.pack();
            }
        };

        ActionListener startMenuListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Get the level the user selected to start the user menu
                String level = startMenu.getDifficulty();

                // Ensure score is reset upon multiple plays
                user.setScore(0);
                
                jFrame.remove(startMenu);

                final int LEVEL_WIDTH = 800;
                final int LEVEL_HEIGHT = 500;
                final int NUM_BIRDS = 5;

                // Create a bird to add to level objects and give the player 3 birds to shoot
                Bird bird = new Bird(0, 0, 40, 40); // adjust width, height, x and y according to level
                LevelObjects levelObjects = new LevelObjects(bird, NUM_BIRDS);

                // Choose level to start based on what the user selected
                if (level.equals("Easy")) {
                    // adjust values based on the level
                    levelObjects.addPigs(new Pig(505, 365, 20, 20)); 
                    levelObjects.addStructures(new Structure(500, 380, 30, 50));
                    jFrame.add(new EasyLevel(LEVEL_WIDTH, LEVEL_HEIGHT, levelObjects, levelListener, user));
                } else if (level.equals("Normal")) {
                    levelObjects.addPigs(new Pig(505, 200, 20, 20));
                    levelObjects.addPigs(new Pig(555,200,20,20));
                    levelObjects.addStructures(new Structure(490,220,100,20)); // Top
                    levelObjects.addStructures(new Structure(500,230,30,50)); // Left
                    levelObjects.addStructures(new Structure(550,230,30,50)); // Right
                    jFrame.add(new NormalLevel(LEVEL_WIDTH, LEVEL_HEIGHT, levelObjects,levelListener, user));
                } else {
                    levelObjects.addPigs(new Pig(530, 410, 20, 20));
                    levelObjects.addPigs(new Pig(505, 335, 20, 20));
                    levelObjects.addPigs(new Pig(650, 335, 20, 20));
                    levelObjects.addStructures(new Structure(490,350,200,20)); // Top
                    levelObjects.addStructures(new Structure(500,370,30,60)); // Left
                    levelObjects.addStructures(new Structure(650,365,30,70)); // Right
                    jFrame.add(new HardLevel(LEVEL_WIDTH, LEVEL_HEIGHT, levelObjects, levelListener, user));
                }

                // Don't want pack() beacause it messes with the image/background zooming
                jFrame.setSize(800, 500);
                jFrame.setResizable(false);
                jFrame.revalidate();
                jFrame.repaint();
            }
        };

        ActionListener endMenuListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                jFrame.remove(endMenu);
                jFrame.add(startMenu);
                jFrame.pack();
            }
        };

        // TODO: ADD SWAPPING FROM A LEVEL TO ENDMENU BOAH

        startMenu.setActionListener(startMenuListener);
        endMenu.setActionListener(endMenuListener);
        jFrame.add(startMenu);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
