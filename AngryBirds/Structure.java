import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Structure {
    private int x, y; 
    private int width, height; 
    private ImageIcon imageIcon; 

    public Structure(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageIcon = new ImageIcon("./Images/Structure.png");
        this.imageIcon = new ImageIcon(this.imageIcon.getImage().getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH));
    }

    public boolean collision() {
        return true;
    }

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
        JLabel jLabel = new JLabel(new Structure(20, 20, 60, 100).getImageIcon());
        jFrame.add(jLabel);
       // jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
