package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
     * Create a mystical button!
     *
     * @param height The height of the button.
     * @param main The main color of the button.
     * @param hover The secondary color of the button.
     * @param text The text on the button.
     * @param font The font of the button.
     */
    public GButton(final int height, final Color main, final Color hover, final String text , final Font font) {
        this.color = main;
        this.hover = hover;
        this.height = height;
        this.font = font;
        this.text = text;
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
        g.fillRect(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        g.setColor(Color.white);
        g.setFont(font);

        int textWidth = g.getFontMetrics().stringWidth(text);

        g.drawString(text, x + width / 2 - textWidth / 2, y + font.getSize());
        return height;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height && isActive) {
            pressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height && pressed) {
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
