import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Pig {
    private int x, y; // Position of the pig
    private int width, height; // Size of the pig
    private ImageIcon imageIcon; // Image icon for the pig

    public Pig(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageIcon = new ImageIcon("./Images/Pig.png");
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

    public ImageIcon getImageIcon() {
        return imageIcon;
        
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Angry Birds");
        jFrame.setSize(200,200);
        jFrame.setResizable(false);
        JLabel jLabel = new JLabel(new Pig(20, 20, 20, 20).getImageIcon());
        jFrame.add(jLabel);
       // jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
