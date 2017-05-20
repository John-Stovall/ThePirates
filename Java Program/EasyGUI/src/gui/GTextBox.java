package gui;

import control.General;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Robert on 4/12/17.
 *
 * This class handles all the stuffs for the text boxes. Ya.
 */
public class GTextBox extends GWrapper implements GUIComponent, GAnimation {

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

    private JTextField textField;

    private int boxHeight = 36;

    private String lastString = "";

    /**
     * Create a super awesome text box!
     *
     * @param height The height of the text box.
     * @param text The starting text in the box. Leave this blank for the most part.
     * @author Robert
     */
    public GTextBox(final int height, final String text) {
        super(height, new JTextField());
        textField = (JTextField) getComponent();
        this.font = new Font("Helvetica", Font.PLAIN, height - 6);
        textField.setFont(font);
        textField.setText(text);
        this.text = text;
        textObj = new GText(text, font);
    }

    /**
     * This method returns whatever text is in the text box.
     *
     * @return The typed text.
     * @author Robert
     */
    public String getText() {
        return textField.getText();
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
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {

        //Draw the failed message text under everything.
        g.setColor(Style.textBoxErrorColor);
        g.setFont(Style.textBoxFail);
        g.drawString(failedMessage, x, y + failMessagePos + boxHeight / 2);

        super.draw(g, x, y, width);

        //Draw the background rectangle.
//        if (pressed || selected) {
//            g.setColor(Style.textBoxSecondaryColor);
//        } else {
//            g.setColor(Style.textBoxColor);
//        }
//        g.fillRect(x, y, width, boxHeight);

        //Set variables for the border rectangle.
        g.setFont(font);
        g.setColor(Style.textBoxBorderColor);

        //Prepare the text, add the | to the end if the timing is correct.
//        textObj.setText(text);
//        if (selected) {
//            if (((int)ticks) % 2 == 0) {
//                textObj.setText(text + "|");
//            }
//            g.drawRect(x, y, width, boxHeight);
//        }

        //Draw the text.
//        texttextObj.draw(g, x + 4, y, width - 8);
//
//        //Draw the red border if failed.
//        if (failed) {
//            g.setColor(Style.textBoxErrorColor);
//            g.drawRect(x, y, width, boxHeight);
//        }

        //Increment the counter for the cursor.
        //ticks += Style.textBoxFlashSpeed;

        return boxHeight + Math.max(failMessagePos - boxHeight / 2 + 10, 0);
    }

    @Override
    public void updateAnimations() {
        if (!lastString.equals(getText())) {
            lastString = getText();
            failed = false;
        }

        //Increment the animation.
        failMessagePos += Style.exponentialTweenRound(failMessagePos, (failed) ? (boxHeight / 2 + 25) : 0,
                Style.textBoxMessageMoveSpeed);

        if (failMessagePos < 4) {
            failedMessage = "";
        }
    }
}
