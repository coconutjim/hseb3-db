package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/***
 * For image
 */
public class DrawPanel extends JPanel {

    private BufferedImage image;

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }

}
