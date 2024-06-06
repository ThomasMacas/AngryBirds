import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Rectangle;

public class HardLevel extends JPanel implements Level {

    private LevelObjects levelObjects;
    private JLabel background;
    private boolean inMotion = false;
    private JLabel bird;
    private int numBirds;
    private ArrayList<JLabel> pigs;
    private ArrayList<JLabel> structures;
    private ActionListener levelListener;
    private int numPigs;
    private User user;

    public HardLevel(int width, int height, LevelObjects levelObjects, ActionListener levelListener, User user) {
        // Ensure there is no layout manager so we can control the objects through coordinates
        super();
        this.setSize(width, height);
        this.setLayout(null);
        
        // set the user and actionListener
        this.levelListener = levelListener;
        this.user = user;

        pigs = new ArrayList<>();
        this.numBirds = levelObjects.getNumBirds();
        this.numPigs = levelObjects.getPigs().size();
        structures = new ArrayList<>();

        this.levelObjects = levelObjects;

        // Add the background to the JPanel
        ImageIcon bg = new ImageIcon("./Images/HardLevel.png");
        bg = new ImageIcon(bg.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        this.background = new JLabel(bg);
        background.setBounds(0, 0, width, height);

        this.bird = new JLabel(levelObjects.getBird().getImageIcon());
        this.bird.setBounds(100, 325, 40, 40);
        this.add(bird);

        for (Pig pig : levelObjects.getPigs()) {
            JLabel newPig = new JLabel(pig.getImageIcon());
            newPig.setBounds(pig.getX(), pig.getY(), pig.getWidth(), pig.getHeight());
            pigs.add(newPig);
            this.add(newPig);
        }

        for (Structure structure : levelObjects.getStructures()) {
            JLabel newStructure = new JLabel(structure.getImageIcon());
            newStructure.setBounds(structure.getX(), structure.getY(), structure.getWidth(), structure.getHeight());
            structures.add(newStructure);
            this.add(newStructure);
        }

        this.add(background);

        // add MouseListener to Panel
        MouseAdapter mouseAdapter = new MouseAdapter() {
            // Creates bounds for the bird and tracks previous position
            private Rectangle birdBounds; 
            private boolean isDragging = false;
            private int xBird;
            private int yBird;
            private int xClick;
            private int yClick;
            private int newX;
            private int newY;

            @Override
            public void mousePressed(MouseEvent e) {
                if (!inMotion) {
                    birdBounds = bird.getBounds();

                    int x = e.getX();
                    int y = e.getY();

                    
                    if (birdBounds.contains(x, y)) {
                        xBird = birdBounds.getLocation().x;
                        yBird = birdBounds.getLocation().y;

                        xClick = x;
                        yClick = y;

                        isDragging = true;
                    } 
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!inMotion) {
                    int x = e.getX();
                    int y = e.getY();
                    
                    if (isDragging) {
                        newX = xBird + (x - xClick);
                        newY = yBird + (y - yClick);
                        updateBird(newX, newY);
                    }
                }   
            }

            @Override 
            public void mouseReleased(MouseEvent e) {
                if (isDragging) { 
                    inMotion = true; 
                    isDragging = false;

                    int x = newX;  
                    int y = newY;

                    // find the distnace pulled back and then find acceleration given 
                    int distanceX = Math.abs(x - xBird);
                    int distanceY = y - yBird;

                    // calculate initial velocity for the bird and pig (note initial velocity = avg velocity for x direction)
                    int vx = (int)(Physics.Xa * distanceX);
                    int yx = (int) Physics.Ya * distanceY;

                    Timer timer = new Timer(10, new ActionListener() {
                        private int currentX = x;
                        private int currentY = y;
                        private int eventOccured = 1;
            
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Update bird's position gradually
                            currentX += vx / 10; 
                            currentY += ((yx * -1) + (eventOccured++ * Physics.g)) / 10;
                            updateBird(currentX, currentY);
            
                            // Check if the bird has reached the final position
                            if (currentY > 410 || updateOnCollision()) {
                                ((Timer) e.getSource()).stop();
                                inMotion = false;

                                if (--numBirds > 0) {
                                    updateBird(100, 325);
                                } else {
                                    updateBird(1000, 1000); // take it off screen -> consider trying to remove instead
                                }

                                // Call actionListener if game Over
                                if (gameOver() != 0) {

                                    // add 1k to score for every leftover bird
                                    for (int i = 0; i < numBirds; i++) {
                                        user.addToScore(1000);
                                    }

                                    // pass the user score as a command to be parsed
                                    levelListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "" + user.getScore()));
                                }
                            }
                        }
                    });

                    timer.start();
                }
            }
        };

        // MouseListener is for pressed, MouseMotionListener is for dragged
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    // x and y are the x,y coordinates for the new position
    public void updateBird(int x, int y) {
        this.bird.setBounds(x, y, 40, 40);

        // call for the JPanel to be updated 
        this.revalidate();
        this.repaint();
    }

    private class Item {
        private JLabel item;
        private Rectangle bounds;

        // if type == 'p' then its a pig, 's' for strucutre
        private char type;

        public Item(JLabel item, Rectangle bounds, char type) {
            this.item = item;
            this.bounds = bounds;
            this.type = type;
        }

        public Rectangle getBounds() {
            return this.bounds;
        }

        public JLabel getItem() {
            return this.item;
        }

        public char getType() {
            return this.type;
        }
    }

    public boolean updateOnCollision() {
        Rectangle birdBounds = bird.getBounds();
        birdBounds.setSize(bird.getWidth()/2, bird.getHeight()/2);
        ArrayList<Item> objectsBounds = new ArrayList<>();

        for (JLabel pig : pigs) {
            Rectangle pigBounds = pig.getBounds();
            pigBounds.setSize(pig.getWidth()/2, pig.getHeight()/2);
            Item item = new Item(pig, pigBounds, 'p');
            objectsBounds.add(item);
        }

        for (JLabel structure : structures) {
            Rectangle structureBounds = structure.getBounds();
            structureBounds.setSize(structure.getWidth()/2, structure.getHeight()/2);
            Item item = new Item(structure, structureBounds, 's');
            objectsBounds.add(item);
        }

        for (Item item : objectsBounds) {
            if (birdBounds.intersects(item.getBounds())) {
                this.remove(item.getItem());

                if (item.getType() == 'p') {
                    pigs.remove(item.getItem());
                    user.addToScore(400);
                    numPigs--;
                } else {
                    structures.remove(item.getItem());
                    user.addToScore(100);
                }

                return true;
            }
        }        

        return false;
    }

    // 0 == game not over, 1 == game is over
    public int gameOver() {
        if (numBirds == 0 || numPigs == 0) {
            return 1; 
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        // Example testing for EasyLevel Coordinates
        JFrame jFrame = new JFrame();
        jFrame.setSize(800, 500);
        jFrame.setResizable(false);
        Bird bird = new Bird(20, 20, 40, 40);
        LevelObjects levelObjects = new LevelObjects(bird, 3);
        HardLevel hardlevel = new HardLevel(800, 500, levelObjects, null, null);
        jFrame.add(hardlevel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);    
        /* 

        int x = 100;
        boolean collided = false;

        while (!easyLevel.updateOnCollision()) {
            Physics physics =  new Physics();
            x += 2;
            easyLevel.updateOnCollision();

            try {
                Thread.sleep(10);
                easyLevel.updateBird(x, 325);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */

        System.out.println("Done!");
    }
}
