package gui;

import java.awt.*;

/**
 * Created by Robert on 4/12/17.
 */
public class GSpacer implements GUIComponent {

    private int height;

    private Color color = Color.gray;

    private boolean visible = false;

    /**
     * Creates an invisible spacer.
     *
     * @param height Height of spacer
     */
    public GSpacer(final int height) {
        this.height = height;
    }

    /**
     * Creates a colored spacer.
     *
     * @param height Height of spacer.
     * @param color The color of the spacer.
     */
    public GSpacer(final int height, final Color color) {
        this(height);
        setVisible(true);
        setColor(color);
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        if (visible) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
        return height;
    }
}
