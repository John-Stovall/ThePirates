package gui;

import java.awt.*;

/**
 * Created by Robert on 5/20/17.
 * <p>
 * This class is used to display images.
 */
public class GImage implements GUIComponent {

    private double scaleFactor = 1;
    /**
     * The image to display.
     */
    private Image icon;

    /**
     * Add an image to a page.
     *
     * @param icon The image to draw.
     * @author Robert Cordingly
     */
    public GImage(final Image icon) {
        this.icon = icon;
    }

    /**
     * Add an image to a page.
     *
     * @param icon The image to draw.
     * @author Robert Cordingly
     */
    public GImage(final Image icon, double scaleFactor) {
        this(icon);
        this.scaleFactor = scaleFactor;
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(icon, (int) (x + width / 2 - (icon.getWidth(null) / 2) * scaleFactor), y,
                (int) (icon.getWidth(null) * scaleFactor), (int) (icon.getHeight(null) * scaleFactor),
                null);
        return (int) (icon.getHeight(null) * scaleFactor);
    }
}
