package gui;

import control.General;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Robert on 5/11/17.
 * <p>
 * This class creates a simple drop down menu that can be used to select specific things.
 */
public class GDropdown implements GUIComponent, GMouseListener, GAnimation {

    /**
     * The options in the menu.
     */
    private ArrayList<String> options;

    /**
     * The currently selected option.
     */
    private String selection;

    /**
     * Whether the dropdown menu is open.
     */
    private boolean open = false;

    /**
     * Whether you pressed on the dropdown menu.
     */
    private boolean pressed = false;

    /**
     * The height of each cell.
     */
    private int height = 40;

    /**
     * Obvious variables are obvious.
     */
    private int x;

    /**
     * The y position of the dropdown menu.
     */
    private int y;

    /**
     * The width of the dropdown menu.
     */
    private int width;

    /**
     * Potential padding to the left and right of the button.
     */
    private int padding = 0;

    /**
     * Progress through the animation.
     */
    private double animation = 0.0;

    /**
     * Creates a Dropdown menu from a standard array string.
     *
     * @param options The individual elements of the menu.
     * @author Robert
     */
    public GDropdown(String[] options) {
        this.options = new ArrayList<>(Arrays.asList(options));
        selection = this.options.get(0);
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {

        //Save some variables.
        this.x = x;
        this.y = y;
        this.width = width;
        int h = height;
        g.setFont(Style.defaultFont);

        int yPos;
        int innerPadding = 16;

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Style.secondaryDropdownColor);
        RoundRectangle2D holder = new RoundRectangle2D.Double(x + innerPadding, y + 1, width - innerPadding * 2, height * (options.size() + 1) * animation - 3, 64, 64);
        g2d.fill(holder);

        //Draw all the elements in the list.
        for (int i = 0; i < options.size(); i++) {
            String s = options.get(i);
            yPos = (int) (y + (height * (i + 1)) * animation);

            if (s.equals(selection)) {
                g.setColor(Style.secondaryDropdownColor.brighter());
                RoundRectangle2D select = new RoundRectangle2D.Double(x + innerPadding, yPos, width - innerPadding * 2, height, 16, 16);
                g2d.fill(select);
            } else {
                g.setColor(Style.secondaryDropdownColor);
            }

            h += height * animation;

            // The padding around the sides of the dropdown portion of the menu.
            int textWidth = g.getFontMetrics().stringWidth(s);
            int textHeight = g.getFontMetrics().getHeight();
            g.setColor(Color.white);
            g.drawString(s, x + width / 2 - textWidth / 2, yPos + height / 2 + textHeight / 2 - 4);
        }


        //Draw the top item.
        RoundRectangle2D main = new RoundRectangle2D.Double(x, y, width, height, 40, 40);
        RoundRectangle2D hover = new RoundRectangle2D.Double(x + width / 2 - (width * animation) / 2, y, width * animation, height, 40, 40);

        g.setColor(Style.primaryDropdownColor);
        g2d.fill(main);
        g.setColor(Style.primaryDropdownColor.darker());
        g2d.fill(hover);
        g.setColor(Style.dropdownBorderColor);

        //Draw the icon.
        g.setColor(Color.white);
        int textWidth = g.getFontMetrics().stringWidth(selection);
        int textHeight = g.getFontMetrics().getHeight();
        g.drawString(selection, x + width / 2 - textWidth / 2, y + height / 2 + textHeight / 2 - 4);
        return h;
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        if (General.clickedInside(x + padding / 2, y, width - padding / 2, height, e) && !open) {
            pressed = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        boolean result = false;
        if (General.clickedInside(x + padding / 2, y, width - padding / 2, height, e) && !open && pressed) {
            open = true;
            result = true;
        } else if (open) {
            if (General.clickedInside(x + padding / 2, y + height,
                    width - padding, (height * options.size()), e)) {
                selection = options.get((int) Math.floor((e.getY() - y - height) / height));
                clickAction();
            }
            result = false;
            open = false;
        }
        pressed = false;
        return result;
    }

    /**
     * Get the currently selected option in the drop down menu.
     *
     * @return The currently selected option.
     * @author Robert
     */
    public String getSelection() {
        return selection;
    }

    /**
     * Sets the currently selected option to something.
     *
     * @param selection What to set the selection to.
     * @author Robert
     */
    public void setSelection(final String selection) {
        this.selection = selection;
    }

    @Override
    public void updateAnimations() {
        animation += Style.exponentialTween(animation, (open) ? 1 : 0, Style.dropdownMoveSpeed);
    }

    /**
     * This method is for updating pages with the selected value.
     */
    public void clickAction() {
    }
}
