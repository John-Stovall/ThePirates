package gui;

import java.awt.*;

/**
 * Created by Robert on 4/12/17.
 * <p>
 * This class is so you can make blank spaces used for positioning things in places.
 */
public class GSpacer implements GUIComponent {

    /**
     * The height of the spacer.
     */
    private int height;

    /**
     * The background color of the spacer.
     */
    private Color color = Color.gray;

    /**
     * Whether the spacer is visible.
     */
    private boolean visible = false;

    /**
     * Creates an invisible spacer.
     *
     * @param height Height of spacer
     * @author Robert
     */
    public GSpacer(final int height) {
        this.height = height;
    }

    /**
     * Creates a colored spacer.
     *
     * @param height Height of spacer.
     * @param color  The color of the spacer.
     * @author Robert
     */
    public GSpacer(final int height, final Color color) {
        this(height);
        setVisible();
        setColor(color);
    }

    /**
     * Sets the spacer to visible or not. Probably useless.
     *
     * @author Robert
     */
    private void setVisible() {
        this.visible = true;
    }

    /**
     * Sets the color of the spacer if you ever need to do that for some reason.
     *
     * @param color The color of the spacer.
     * @author Robert
     */
    public void setColor(final Color color) {
        this.color = color;
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
