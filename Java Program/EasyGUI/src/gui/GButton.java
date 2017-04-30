package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;

/**
 * Created by Robert on 4/12/17.
 *
 * This class handles a logic and stuff for buttons.
 */
public class GButton implements GUIComponent, MouseListener {

    /** The height of the button. Used to get mouse clicks. */
    private int height;

    /** The x position of the button. Used to get mouse clicks. */
    private int x;

    /** The y position of the button. Used to get mouse clicks. */
    private int y;

    /** The width of the button. Used to get mouse clicks. */
    private int width;

    /** The 'standby' color of the button. */
    private Color color;

    /** The color the button changes to when you click it. */
    private Color hover;

    /** Whether the button is pressed or not. */
    private boolean pressed = false;

    /** The font of the text on the button. */
    private Font font;

    /** The text on the button. */
    private String text;

    /** Whether button can be clicked or not. */
    private boolean isActive = true;

    /** The amount of padding to be added to the side of the button. */
    private int padding = 0;

    /** The image to be drawn on this button. */
    private Image icon;

    /**
     * Create a button!
     *
     * @param height The height of the button.
     * @param main The main color of the button.
     * @param hover The secondary color of the button.
     * @param text The text on the button.
     */
    public GButton(final int height, final Color main, final Color hover, final String text) {
        this.color = main;
        this.hover = hover;
        this.height = height;
        this.font = new Font("Helvetica", Font.PLAIN, height - 6);
        this.text = text;
    }

    /**
     * Create a somewhat special button!
     *
     * @param height The height of the button.
     * @param main The main color of the button.
     * @param hover The secondary color of the button.
     * @param text The text on the button.
     * @param padding The amount of padding to be added to the left and right side of the button.
     */
    public GButton(final int height, final Color main, final Color hover, final String text, final int padding) {
        this.color = main;
        this.hover = hover;
        this.height = height;
        this.font = new Font("Helvetica", Font.PLAIN, height - 6);
        this.text = text;
        this.padding = padding;
    }

    /**
     * Create a mystical button!
     *
     * @param height The height of the button.
     * @param main The main color of the button.
     * @param hover The secondary color of the button.
     * @param text The text on the button.
     * @param font The font of the button.
     */
    public GButton(final int height, final Color main, final Color hover, final String text, final Font font) {
        this.color = main;
        this.hover = hover;
        this.height = height;
        this.font = font;
        this.text = text;
    }

    /**
     * Create a super special awesome button!
     *
     * @param height The height of the button.
     * @param main The main color of the button.
     * @param hover The secondary color of the button.
     * @param text The text on the button.
     * @param font The font of the button.
     * @param padding The amount of padding to be added to the left and right side of the button.
     */
    public GButton(final int height, final Color main, final Color hover, final String text,
                   final Font font, final int padding) {
        this.color = main;
        this.hover = hover;
        this.height = height;
        this.font = font;
        this.text = text;
        this.padding = padding;
    }

    /**
     * Create a the GREATEST BUTTON THE WORLD HAS EVER SEEN!!!
     *
     * @param height The height of the button.
     * @param main The main color of the button.
     * @param hover The secondary color of the button.
     * @param text The text on the button.
     * @param font The font of the button.
     * @param padding The amount of padding to be added to the left and right side of the button.
     */
    public GButton(final int height, final Color main, final Color hover, final String text,
                   final Font font, final int padding, final Image icon) {
        this.color = main;
        this.hover = hover;
        this.height = height;
        this.font = font;
        this.text = text;
        this.padding = padding;
        this.icon = icon;
    }

    /**
     * Set whether the button can be pressed or not.
     *
     * @param state Can it be pressed?
     */
    public void setActive(boolean state) {
        isActive = state;
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        if (pressed) {
            g.setColor(hover);
        } else {
            g.setColor(color);
        }
        g.fillRect(x + padding / 2, y, width - padding, height);
        this.x = x;
        this.y = y;
        this.width = width;
        g.setColor(Color.white);
        g.setFont(font);

        int textWidth = g.getFontMetrics().stringWidth(text);
        int textHeight = g.getFontMetrics().getHeight();

        if (icon != null) {
            double imageRatio = icon.getHeight(null) / icon.getWidth(null);
            int imageHeight = (int) ((width - padding * 2) * imageRatio);
            g.drawImage(icon, x + padding, y + padding, width - padding * 2, imageHeight, null);
            g.drawString(text, x + width / 2 - textWidth / 2, y + imageHeight + padding + (height - imageHeight) / 2 + textHeight / 2 - 4);

        } else {
            g.drawString(text, x + width / 2 - textWidth / 2, y + height / 2 + textHeight / 2 - 4);
        }

        return height;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > x + padding / 2 && e.getX() < x + width - padding / 2 && e.getY() > y && e.getY() < y + height && isActive) {
            pressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX() > x + padding / 2 && e.getX() < x + width - padding / 2 && e.getY() > y && e.getY() < y + height && pressed) {
            clickAction();
        }
        pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * When you create a button override this method to add
     * custom functionality to certain buttons.
     */
    public void clickAction() {}
}
