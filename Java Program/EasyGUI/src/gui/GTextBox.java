package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Robert on 4/12/17.
 * <p>
 * This class handles all the stuffs for the text boxes. Ya.
 */
public class GTextBox extends GWrapper implements GUIComponent, GAnimation {

    /**
     * Whether a failed input has been passed in.
     */
    private boolean failed = false;

    /**
     * The position of the failed message.
     */
    private int failMessagePos = 0;

    /**
     * The description/failed message text.
     */
    private GText message = new GText("", Style.textBoxFail);

    /**
     * The text box.
     */
    private JTextField textField;

    /**
     * The height of the text box.
     */
    private int boxHeight;

    /**
     * The last string that was typed into the text box.
     */
    private String lastString = "";

    /**
     * The goal height used in the animation.
     */
    private int goalHeight = boxHeight;

    /**
     * The current height used in the animation.
     */
    private int currentHeight = boxHeight;

    /**
     * The amount of padding on the left and right side of the text.
     */
    private final int textPadding = 10;

    /**
     * Whether to show the description or not.
     */
    private boolean showDetails = false;

    /**
     * The description.
     */
    private String description = "";

    /**
     * Whether the text box is in focus or not.
     */
    private boolean inFocus = false;

    /**
     * Create a super awesome text box!
     *
     * @param height The height of the text box.
     * @param text   The starting text in the box. Leave this blank for the most part.
     * @author Robert
     */
    public GTextBox(final int height, final String text) {
        super(height, new JTextField());
        boxHeight = height;
        textField = (JTextField) getComponent();
        Font font = Style.defaultFont;
        textField.setFont(font);
        textField.setText(text);

        goalHeight = boxHeight + Math.max(failMessagePos - boxHeight + textPadding, 0) + Style.textBoxFail.getSize();
        currentHeight = boxHeight + Math.max(failMessagePos - boxHeight + textPadding, 0) + Style.textBoxFail.getSize();
    }

    /**
     * Creates a text box with a description that shows when the text box is in focus.
     *
     * @param height      The height of the text box.
     * @param text        The text in the text box.
     * @param description The description for when the text box is in focus.
     * @author Robert
     */
    public GTextBox(final int height, final String text, final String description) {
        this(height, text);
        this.description = description;
    }

    /**
     * This method sets the value of the text box.
     *
     * @param value The value to set the text box to.
     * @author Robert
     */
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
