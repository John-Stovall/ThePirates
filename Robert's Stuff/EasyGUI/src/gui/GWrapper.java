package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Robert on 4/14/17.
 *
 * This class is not that good. Avoid using it if possible and it's only
 * been tested using JButton.
 */
public class GWrapper extends JPanel implements GUIComponent {

    private int height;

    private JComponent item;

    public GWrapper(final int height, final JComponent item) {
        this.height = height;
        this.item = item;
    }

    void build(int x, int y, int width) {
        setLayout(new BorderLayout());
        setLocation(x, y);
        setSize(width, height);
        add(item, BorderLayout.CENTER);
        setVisible(true);
        revalidate();
        repaint();
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        repaint();
        return height;
    }
}
