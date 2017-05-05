package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Robert on 4/12/17.
 *
 * This class handles all the stuffs for the text boxes. Ya.
 */
public class GTextBox implements GUIComponent, MouseListener, KeyListener {

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

    /** Whether the text box has been properly clicked on. */
    private boolean selected;

    /** The font of the text in the text box. */
    private Font font;

    /** The currently typed text in the text box. */
    private String text;

    /** The max number of characters in a textbox. */
    private int maxLength = Integer.MAX_VALUE;

    /** Whether a failed input has been passed in. */
    private boolean failed = false;

    /** The message to be displayed when a failed even has triggered. */
    private String failedMessage = "Invalid Input!";

    /** Just a number representing the time for this box. */
    private double ticks = 0;

    /** The position of the failed message. */
    private int failMessagePos = 0;

    private GText textObj;

    /**
     * Create a super awesome text box!
     *
     * @param height The height of the text box.
     * @param main The main color of the text box.
     * @param hover The secondary color of the text box.
     * @param text The starting text in the box. Leave this blank for the most part.
     */
    public GTextBox(final int height, final Color main, final Color hover, final String text) {
        this.color = main;
        this.hover = hover;
        this.height = height;
        this.font = new Font("Helvetica", Font.PLAIN, height - 6);
        this.text = text;
        textObj = new GText(text, font);
    }

    /**
     * Create an even better textbox!
     *
     * @param height The height of the text box.
     * @param main The main color of the text box.
     * @param hover The secondary color of the text box.
     * @param text The starting text in the box. Leave this blank for the most part.
     */
    public GTextBox(final int height, final Color main, final Color hover, final String text, final int maxLength) {
        this.color = main;
        this.hover = hover;
        this.height = height;
        this.font = new Font("Helvetica", Font.PLAIN, height - 6);
        this.text = text;
        this.maxLength = maxLength;
        textObj = new GText(text, font);
    }

    /**
     * This method returns whatever text is in the text box.
     *
     * @return The typed text.
     */
    public String getText() {
        return text;
    }

    /**
     * Open the error message for this text box.
     *
     * @param message The error to show.
     */
    public void failed(final String message) {
        failed = true;
        failedMessage = message;
        pressed = false;
        selected = false;
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {

        ticks += 0.02;
        int boxHeight = textObj.draw(g, x + 4, y, width - 8) + 6;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = boxHeight;

        if (failed) {
            failMessagePos += ((boxHeight / 2 + 35) - failMessagePos) / 10.0;
        } else {
            failMessagePos += (-failMessagePos) / 10.0;
        }

        g.setColor(Color.red);
        g.setFont(new Font("Helvetica", Font.PLAIN, 16));
        g.drawString(failedMessage, x, y + failMessagePos + boxHeight / 2);

        if (pressed || selected) {
            g.setColor(hover);
        } else {
            g.setColor(color);
        }

        g.fillRect(x, y, width, boxHeight);

        g.setColor(Color.white);
        g.setFont(font);
        g.setColor(Color.black);

        textObj.setText(text);

        if (selected) {
            if (((int)ticks) % 2 == 0) {
                textObj.setText(text + "|");
            }
            g.drawRect(x, y, width, boxHeight);
        }

        textObj.draw(g, x + 4, y, width - 8);

        if (failed) {
            g.setColor(Color.red);
            g.drawRect(x, y, width, boxHeight);
        }

        return boxHeight + Math.max(failMessagePos - boxHeight / 2 + 10, 0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        selected = false;
        if (e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height) {
            pressed = true;
            failed = false;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height && pressed) {
            selected = true;
        }
        pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
        if (selected) {
            if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && text.length() > 0) {
                text = text.substring(0, text.length() - 1);
            } else {
                if (e.getKeyChar() != KeyEvent.VK_TAB && text.length() < maxLength) {
                    text += e.getKeyChar();
                }
            }

            if (e.getKeyChar() == KeyEvent.VK_TAB) {
                boolean found = false;
                for (GUIComponent c : GUI.window.getItems()) {
                    if (c == this) {
                        found = true;
                    } else if (found && c instanceof GTextBox) {
                        selected = false;
                        ((GTextBox)c).selected = true;
                    }
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
