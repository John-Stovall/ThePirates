package gui;

import control.General;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by Robert on 4/12/17.
 * <p>
 * This class handles a logic and stuff for buttons.
 */
public class GButton implements GUIComponent, GMouseListener, GAnimation {

    /**
     * The height of the button. Used to get mouse clicks.
     */
    private int height;

    /**
     * The x position of the button. Used to get mouse clicks.
     */
    private int x;

    /**
     * The y position of the button. Used to get mouse clicks.
     */
    private int y;

    /**
     * The width of the button. Used to get mouse clicks.
     */
    private int width;

    /**
     * The 'standby' color of the button.
     */
    private Color color = Style.primaryButtonColor;

    /**
     * The color the button changes to when you click it.
     */
    private Color hover = Style.secondaryButtonColor;

    /**
     * Whether the button is pressed or not.
     */
    private boolean pressed = false;

    /**
     * The font of the text on the button.
     */
    private Font font;

    /**
     * The text on the button.
     */
    private String text;

    /**
     * Whether button can be clicked or not.
     */
    private boolean isActive = true;

    /**
     * The amount of padding to be added to the side of the button.
     */
    private int padding = 0;

    /**
     * The image to be drawn on this button.
     */
    private Image icon;

    /**
     * The progress through the hover animation.
     */
    private int hoverAnimation = 0;

    /**
     * Create a button!
     *
     * @param height The height of the button.
     * @param text   The text on the button.
     * @author Robert
     */
    public GButton(final int height, final String text) {
        this.height = height;
        this.font = new Font("Helvetica", Font.PLAIN, height - 6);
        this.text = text;
    }

    /**
     * Create a somewhat special button!
     *
     * @param height  The height of the button.
     * @param text    The text on the button.
     * @param padding The amount of padding to be added to the left and right side of the button.
     * @author Robert
     */
    public GButton(final int height, final String text, final int padding) {
        this.height = height;
        this.font = new Font("Helvetica", Font.PLAIN, height - 6);
        this.text = text;
        this.padding = padding;
    }

    /**
     * Create a mystical button!
     *
     * @param height The height of the button.
     * @param text   The text on the button.
     * @param font   The font of the button.
     * @author Robert
     */
    public GButton(final int height, final String text, final Font font) {
        this.height = height;
        this.font = font;
        this.text = text;
    }

    /**
     * Create a mystical button!
     *
     * @param height The height of the button.
     * @param text   The text on the button.
     * @param font   The font of the button.
     * @author Robert
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
     * @param height  The height of the button.
     * @param text    The text on the button.
     * @param font    The font of the button.
     * @param padding The amount of padding to be added to the left and right side of the button.
     * @author Robert
     */
    public GButton(final int height, final String text, final Font font, final int padding) {
        this.height = height;
        this.font = font;
        this.text = text;
        this.padding = padding;
    }

    /**
     * Create a the GREATEST BUTTON THE WORLD HAS EVER SEEN!!!
     *
     * @param height  The height of the button.
     * @param text    The text on the button.
     * @param font    The font of the button.
     * @param padding The amount of padding to be added to the left and right side of the button.
     * @author Robert
     */
    public GButton(final int height, final String text,
                   final Font font, final int padding, final Image icon) {
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
     * @author Robert
     */
    void setActive(boolean state) {
        isActive = state;
    }

    @Override
    public void updateAnimations() {
        //Increment the animation.
        hoverAnimation += Style.exponentialTweenRound(hoverAnimation, (pressed) ? width : 0, Style.buttonMoveSpeed);
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        //Save variables for later.
        this.x = x;
        this.y = y;
        this.width = width;

        Graphics2D g2d = (Graphics2D) g;
        RoundRectangle2D background = new RoundRectangle2D.Double(x + padding / 2, y, width - padding, height, 16, 16);
        RoundRectangle2D animation = new RoundRectangle2D.Double(x + width / 2 - hoverAnimation / 2, y, hoverAnimation, height, 16, 16);

        //Draw the background rectangle.
        g.setColor(color);
        //g.fillRect(x + padding / 2, y, width - padding, height);
        g2d.fill(background);
        g.setColor(hover);
        //g.fillRect(x + width / 2 - hoverAnimation / 2, y, hoverAnimation, height);
        g2d.fill(animation);

        //Prepare variables to draw the text and icon.
        g.setColor(Style.buttonTextColor);
        g.setFont(font);
        int textWidth = g.getFontMetrics().stringWidth(text);
        int textHeight = g.getFontMetrics().getHeight();

        //Draw the text and icon in the proper orientation.
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
    public boolean mousePressed(MouseEvent e) {
        if (General.clickedInside(x + padding / 2, y, width - padding, height, e) && isActive) {
            pressed = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        boolean result = false;
        if (General.clickedInside(x + padding / 2, y, width - padding, height, e) && pressed) {
            clickAction();
            result = true;
        }
        pressed = false;
        return result;
    }

    /**
     * When you create a button override this method to add
     * custom functionality to certain buttons.
     *
     * @author Robert
     */
    public void clickAction() {
    }
}
