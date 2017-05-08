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

    /** The height of the wrapper. */
    private int height;

    /** The SWING component that this wrapper will hold. */
    private JComponent item;

    /**
     * Create a wrapper.
     *
     * @param height The height of the area.
     * @param item The swing component that this wrapper will hold.
     */
    @Deprecated
    public GWrapper(final int height, final JComponent item) {
        this.height = height;
        this.item = item;
    }

    /**
     * Called by GUI to setup the swing stuff properly.
     *
     * @param x The x position of the wrapper.
     * @param y The y position of the wrapper.
     * @param width The width.
     */
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
