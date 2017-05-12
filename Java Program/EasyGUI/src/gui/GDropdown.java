package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Robert on 5/11/17.
 *
 * This class creates a simple drop down menu that can be used to select specific things.
 */
public class GDropdown implements GUIComponent, GMouseListener {

    /** The options in the menu. */
    private ArrayList<String> options;

    /** The currently selected option. */
    private String selection;

    /** Whether the dropdown menu is open. */
    private boolean open = false;

    /** Whether you pressed on the dropdown menu. */
    private boolean pressed = false;

    /** The height of each cell. */
    private int height = 26;

    /** Obvious variables are obvious. */
    private int x;

    /** The y position of the dropdown menu. */
    private int y;

    /** The width of the dropdown menu. */
    private int width;

    /** Potential padding to the left and right of the button. */
    private int padding = 0;

    /** Progress through the animation. */
    private double animation = 0.0;

    /** The font used by this component. */
    private static final Font defaultFont = new Font("Helvetica", Font.PLAIN, 20);

    /** The primary color used by many different things. */
    private static final Color mainColor = Color.decode("#2E7D32");

    /** The secondary color used by many different things. */
    private static final Color secondaryColor = Color.decode("#388E3C");

    /** Another color. */
    private static final Color anotherColor = Color.decode("#1B5E20");

    /** The padding around the sides of the dropdown portion of the menu. */
    private int innerPadding = 16;

    /**
     * Create a super awesome dropdown menu.
     *
     * @param options The individual elements of the menu.
     */
    public GDropdown(ArrayList<String> options) {
        this.options = options;
        selection = options.get(0);
    }

    public GDropdown(String[] options) {
        this.options = new ArrayList<String>(Arrays.asList(options));
        selection = this.options.get(0);
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        g.setFont(defaultFont);
        this.x = x;
        this.y = y;
        this.width = width;
        int h = height;

        if (open) {
            animation += (1 - animation) / 3.0;
        } else {
            animation += (-animation) / 3.0;
        }

        for (int i = 0; i < options.size(); i++) {
            String s = options.get(i);
            if (s.equals(selection)) {
                g.setColor(secondaryColor.brighter());
            } else {
                g.setColor(secondaryColor);
            }
            int yPos = (int) (y + (height * (i + 1)) * animation);
            h += height * animation;

            g.fillRect(x + innerPadding, yPos, width - innerPadding * 2, height);
            g.setColor(Color.white);
            g.drawString(s, x + innerPadding + 4, yPos + height - 4);
            g.setColor(anotherColor);
            g.drawRect(x + innerPadding, yPos, width - innerPadding * 2, height);
        }

        g.setColor(anotherColor);
        g.drawRect(x, y, width, height);
        g.setColor(mainColor);
        g.fillRect(x, y, width, height);

        g.setColor(Color.white);
        g.drawString(selection, x + 4, y + height - 6);
        if (!open) {
            g.drawString("v", x + width - 18, y + height - 8);
        } else {
            g.drawString("^", x + width - 18, y + height - 3);
        }


        return h;
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        if (!open && e.getX() > x + padding / 2 && e.getX() < x + width - padding / 2 && e.getY() > y && e.getY() < y + height) {
            pressed = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        boolean result = false;
        if (!open && e.getX() > x + padding / 2 && e.getX() < x + width - padding / 2 && e.getY() > y && e.getY() < y + height && pressed) {
            open = true;
            result = true;
        } else if (open) {
            if (e.getX() > x + padding / 2 && e.getX() < x + width - padding / 2) {
                if (e.getY() > y + height && e.getY() < y + (height * (options.size() + 1))) {
                    selection = options.get((int) Math.floor((e.getY() - y - height) / height));
                }
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
     */
    public String getSelection() {
        return selection;
    }

    /**
     * Sets the currently selected option to something.
     *
     * @param selection What to set the selection to.
     */
    public void setSelection(final String selection) {
        this.selection = selection;
    }
}
