package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Robert on 4/14/17.
 *
 * This class is broken, don't use it.
 */
public class GWrapper extends JPanel implements GUIComponent {

    private int height;

    private JComponent item;

    public GWrapper(final int height, final JComponent item) {
        this.height = height;
        this.item = item;
    }

    public void build(int x, int y, int width) {
        setLayout(new BorderLayout());
        setBackground(Color.red);
        setLocation(x, y);
        setSize(width, height);
        add(item, BorderLayout.CENTER);
        setVisible(true);
        revalidate();
        repaint();
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        System.out.println("Am I being called?");
        repaint();
        return height;
    }

}
