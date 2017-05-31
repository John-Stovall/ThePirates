package gui;

import control.General;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Robert on 4/12/17.
 *
 * This class handles all the stuffs for the text boxes. Ya.
 */
public class GTextBox extends GWrapper implements GUIComponent, GAnimation {

    /** The font of the text in the text box. */
    private Font font;

    /** Whether a failed input has been passed in. */
    private boolean failed = false;

    /** The position of the failed message. */
    private int failMessagePos = 0;

    private GText message = new GText("", Style.textBoxFail);

    private JTextField textField;

    private int boxHeight;

    private String lastString = "";

    private int goalHeight = boxHeight;

    private int currentHeight = boxHeight;

    private final int textPadding = 10;

    private boolean showDetails = false;

    private String description = "";

    private boolean inFocus = false;

    /**
     * Create a super awesome text box!
     *
     * @param height The height of the text box.
     * @param text The starting text in the box. Leave this blank for the most part.
     * @author Robert
     */
    public GTextBox(final int height, final String text) {
        super(height, new JTextField());
        boxHeight = height;
        textField = (JTextField) getComponent();
        this.font = Style.defaultFont;
        textField.setFont(font);
        textField.setText(text);

        goalHeight = boxHeight + Math.max(failMessagePos - boxHeight + textPadding, 0) + Style.textBoxFail.getSize();
        currentHeight = boxHeight + Math.max(failMessagePos - boxHeight + textPadding, 0) + Style.textBoxFail.getSize();
    }

    public GTextBox(final int height, final String text, final String description) {
        this(height, text);
        this.description = description;
    }

    public void setValue(String value) {
        textField.setText(value);
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
        this.message.setText(message);
        this.message.setColor(Color.red);
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {

        if (!inFocus) {
            if (textField.isFocusOwner()) {
                textField.setText("");
            }
        }
        inFocus = textField.isFocusOwner();

        if (!failed && textField.isFocusOwner() && !description.equals("")) {
            showDetails = true;
            message.setText(description);
            message.setColor(Color.black);
        } else {
            showDetails = false;
        }

        int textHeight = message.draw(g, x + 16, y + failMessagePos, width - 32);

        super.draw(g, x, y, width);

        goalHeight = boxHeight + Math.max(failMessagePos - boxHeight + textPadding, 0) + textHeight;

        return currentHeight;
    }

    @Override
    public void updateAnimations() {

        currentHeight += Style.exponentialTweenRound(currentHeight, goalHeight, Style.textBoxMessageMoveSpeed / 2);

        if (!lastString.equals(getText())) {
            lastString = getText();
            failed = false;
        }

        //Increment the animation.
        int goalPosition = 0;
        if (failed || showDetails) {
            goalPosition = boxHeight + textPadding;
        }


        failMessagePos += Style.exponentialTweenRound(failMessagePos, goalPosition, Style.textBoxMessageMoveSpeed);

        if (failMessagePos < (boxHeight + textPadding) / 2 && !failed && !showDetails) {
            message.setText("");
        }
    }
}
