package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Robert on 4/14/17.
 */
public class GWrapper extends JPanel implements GUIComponent {

    private int height;

    private JComponent item;

    public GWrapper(final int height, final JComponent item) {
        this.setLayout(new BorderLayout());
        this.height = height;
        this.setVisible(true);
        this.setBackground(Color.red);
        this.item = item;
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        System.out.println("Am I being called?");
        setLocation(x, y);
        setSize(new Dimension(width, height));
        add(item, BorderLayout.CENTER);
        repaint();
        return 25;
    }

}
