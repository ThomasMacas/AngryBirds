import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Image;


public class Bird {
    private int x, y; // Position of the bird
    private int width, height; // Size of the bird
    private ImageIcon imageIcon; // Image icon for the bird

    public Bird(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageIcon = new ImageIcon("./Images/Bird.png");
        this.imageIcon = new ImageIcon(this.imageIcon.getImage().getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH));
    }

    // Getters and setters for the variables
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // returns the image icon for the angryBirds image
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    // takes the x and y coordinates of whatever is passed in and check if it's in contact with the Bird
    public boolean isCollisionDetected(int x, int y) {
        int xLowerBound = getX() - getWidth();
        int xHigherBound = getX() + getWidth();

        // Note that the higherbound for y is based on the top left being 0,0 in the coordinate system
        int yLowerBound = getY() - getHeight();
        int yHigherBound = getY() + getHeight();

        if ((xLowerBound >= x && xHigherBound <= x) && (yLowerBound >= y && yHigherBound <= y)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Angry Birds");
        jFrame.setSize(200,200);
        jFrame.setResizable(false);
        JLabel jLabel = new JLabel(new Bird(20, 20, 40, 40).getImageIcon());
        jFrame.add(jLabel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
