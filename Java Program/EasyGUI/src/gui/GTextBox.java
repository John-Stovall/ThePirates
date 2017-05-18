package gui;

import control.General;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Robert on 4/12/17.
 *
 * This class handles all the stuffs for the text boxes. Ya.
 */
public class GTextBox implements GUIComponent, GMouseListener, GKeyListener {

    /** The height of the button. Used to get mouse clicks. */
    private int height;

    /** The x position of the button. Used to get mouse clicks. */
    private int x;

    /** The y position of the button. Used to get mouse clicks. */
    private int y;

    /** The width of the button. Used to get mouse clicks. */
    private int width;

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
     * @param text The starting text in the box. Leave this blank for the most part.
     * @author Robert
     */
    public GTextBox(final int height, final String text) {
        this.height = height;
        this.font = new Font("Helvetica", Font.PLAIN, height - 6);
        this.text = text;
        textObj = new GText(text, font);
    }

    /**
     * Create an even better textbox!
     *
     * @param height The height of the text box.
     * @param text The starting text in the box. Leave this blank for the most part.
     * @author Robert
     */
    public GTextBox(final int height, final String text, final int maxLength) {
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
     * @author Robert
     */
    public String getText() {
        return text;
    }

    /**
     * Open the error message for this text box.
     *
     * @param message The error to show.
     * @author Robert
     */
    public void failed(final String message) {
        failed = true;
        failedMessage = message;
        pressed = false;
        selected = false;
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {

        //Save some variables for later.
        int boxHeight = textObj.draw(g, x + 4, y, width - 8) + 6;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = boxHeight;

        //Draw the failed message text under everything.
        g.setColor(Style.textBoxErrorColor);
        g.setFont(Style.textBoxFail);
        g.drawString(failedMessage, x, y + failMessagePos + boxHeight / 2);

        //Draw the background rectangle.
        if (pressed || selected) {
            g.setColor(Style.textBoxSecondaryColor);
        } else {
            g.setColor(Style.textBoxColor);
        }
        g.fillRect(x, y, width, boxHeight);

        //Set variables for the border rectangle.
        g.setFont(font);
        g.setColor(Style.textBoxBorderColor);

        //Prepare the text, add the | to the end if the timing is correct.
        textObj.setText(text);
        if (selected) {
            if (((int)ticks) % 2 == 0) {
                textObj.setText(text + "|");
            }
            g.drawRect(x, y, width, boxHeight);
        }

        //Draw the text.
        textObj.draw(g, x + 4, y, width - 8);

        //Draw the red border if failed.
        if (failed) {
            g.setColor(Style.textBoxErrorColor);
            g.drawRect(x, y, width, boxHeight);
        }

        //Increment the counter for the cursor.
        ticks += Style.textBoxFlashSpeed;

        //Increment the animation.
        failMessagePos += Style.exponentialTweenRound(failMessagePos, (failed) ? (boxHeight / 2 + 25) : 0,
                Style.textBoxMessageMoveSpeed);

        return boxHeight + Math.max(failMessagePos - boxHeight / 2 + 10, 0);
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        selected = false;
        if (General.clickedInside(x, y, width, height, e)) {
            pressed = true;
            failed = false;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        if (General.clickedInside(x, y, width, height, e) && pressed) {
            selected = true;
        }
        pressed = false;
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (selected) {

            //Add characters if selected.
            if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && text.length() > 0) {
                text = text.substring(0, text.length() - 1);
            } else {
                if (e.getKeyChar() != KeyEvent.VK_TAB && text.length() < maxLength) {
                    text += e.getKeyChar();
                }
            }

            //Allow tabbing between boxes.
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
}
