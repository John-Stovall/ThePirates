package gui;

import com.sun.prism.paint.Gradient;
import main.User;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Created by Robert on 4/14/17.
 *
 * This class is by far the messiest class out of all of them and
 * this handles ALL of the logic and stuff for the menu bar.
 *
 * I'll clean it up eventually.
 */
public class GMenuBar implements GUIComponent, GMouseListener, GSubList {

    /** The height of the menu bar. */
    private int height;

    /** Whether the left button has been properly pressed. */
    private boolean pageSelected = false;

    /** Whether the right button has been pressed. */
    private boolean accountPressed = false;

    /** Whether the left button has been pressed. */
    private boolean pagePressed = false;

    /** Wheather the right button has been properly pressed. */
    private boolean accountSelected = false;

    /** It's the width of something, I don't remember. */
    private int width;

    /** The width of the slide over menus. */
    private int dropdownWidth;

    /** The padding slide over menus have off the screen. */
    private int tabPadding = 8;

    /** The width, in pixels, of the username. */
    private int nameWidth;

    /** The total height of the left side bar. */
    private int pageTotalHeight;

    /** The total height of the right side bar. */
    private int accountTotalHeight;

    /** How fast the menus scroll. Lower numbers are faster and exponential! */
    private double scrollSpeed = 7.0;
    
    private static final Color mainColor = Color.decode("#43A047");
    
    private static final Color secondaryColor = Color.decode("#2E7D32");

    private static final Color textColor = Color.white;

    private static final Color sideColor = Color.decode("#2E7D32");

    /** The GUIComponents in the left menu. */
    ArrayList<GUIComponent> pageComponents = new ArrayList<>();

    /** The GUIComponents in the right menu. */
    ArrayList<GUIComponent> accountComponents = new ArrayList<>();

    /**
     * Create a menu bar with some height.
     *
     * @param height The height.
     */
    public GMenuBar(final int height) {
        this.height = height;
    }

    /**
     * Adds a component to the left menu bar.
     *
     * @param c The component to add.
     */
    public void addPage(GUIComponent c) {
        if (c instanceof GButton) {
            ((GButton) c).setActive(false);
        }
        pageComponents.add(c);
    }

    /**
     * Adds a component to the right menu bar.
     *
     * @param c The component to add.
     */
    public void addAccount(GUIComponent c) {
        if (c instanceof GButton) {
            ((GButton) c).setActive(false);
        }
        accountComponents.add(c);
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {
        dropdownWidth = Math.min((int) Math.round(GUI.getWindowWidth() * 0.8), 512);
        this.width = width;

        Graphics2D g2d = ((Graphics2D) g);

        //Draw the main rectangle.
        g.setColor(mainColor);
        g.fillRect(0, 0, GUI.getWindowWidth(), height);

        //Draw the little rectangle.
        g.setColor(mainColor.darker());
        g.fillRect(0, height - 2, GUI.getWindowWidth(), 2);

        //Draw drop shadow.
        GradientPaint shadow = new GradientPaint(0, 0, Color.BLACK, 0, 55, new Color(0, 0, 0, 0));
        g2d.setPaint(shadow);
        g2d.fillRect(0, height, GUI.getWindowWidth(), 50);

        //Reset g2d.
        g2d = ((Graphics2D) g);

        //Draw the page bar.
        g.setColor(sideColor);
        g.fillRect(GUI.horizontalOffset - dropdownWidth - tabPadding, height, dropdownWidth, GUI.window.getHeight());

        g.setColor(secondaryColor);
        int y2 = height;
        if (pageSelected || pagePressed) {
            g.fillRect(0, 0, height, height);
        }
        for (GUIComponent c : pageComponents) {
            y2 += c.draw(g, GUI.horizontalOffset - dropdownWidth - tabPadding, y2, dropdownWidth);
        }
        g.setColor(textColor);
        for (int i = 0; i < 3; i++) {
            g.fillRect(height / 8, (int)(height / 7.0 * (((i + 1) * 2) - 1)),
                    (height / 8) * 6, height / 7);
        }
        pageTotalHeight = y2 - height;

        //Draw the account bar.
        g.setColor(sideColor);
        int newX = GUI.getWindowWidth() - dropdownWidth;
        g.fillRect(newX + dropdownWidth + GUI.horizontalOffset + tabPadding, height, dropdownWidth, GUI.window.getHeight());

        g.setColor(secondaryColor);
        int y3 = height;
        if (accountSelected || accountPressed) {
            g.fillRect(GUI.getWindowWidth() - height - nameWidth - 16, 0, height + nameWidth + 16, height);
        }
        for (GUIComponent c : accountComponents) {
            y3 += c.draw(g, newX + dropdownWidth + GUI.horizontalOffset + tabPadding, y3, dropdownWidth);
        }
        g.setColor(textColor);

        Ellipse2D circle = new Ellipse2D.Double(GUI.getWindowWidth() - height * 0.875, height / 4 / 2, height * 0.75, height * 0.75);
        g2d.fill(circle);
        accountTotalHeight = y3 - height;

        g.setFont(new Font("Helvetica", Font.PLAIN, 26));

        //Draw name
        String text = User.getLoadedUser().getName();
        int length = g.getFontMetrics().stringWidth(text);
        nameWidth = length - 4;

        g.drawString(text, GUI.getWindowWidth() - height - length - 4, height - 10);

        //Draw Page title if there is room.
        String roomName = GUI.getPageTitle();
        int nameLength = g.getFontMetrics().stringWidth(roomName);

        g.drawString(roomName, height + 4, height - 10);

        //Process slide over animation
        if (pageSelected) {
            GUI.horizontalOffset += Math.ceil((dropdownWidth - GUI.horizontalOffset) / scrollSpeed);
        } else if (accountSelected) {
            GUI.horizontalOffset += Math.floor((-dropdownWidth - GUI.horizontalOffset) / scrollSpeed);
        } else {
            GUI.horizontalOffset += Math.ceil(Math.abs((-GUI.horizontalOffset) / scrollSpeed)) * (Math.signum((-GUI.horizontalOffset) / scrollSpeed));
        }

        return height;
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        boolean result = false;
        if (e.getX() > 0 && e.getX() < height && e.getY() > 0 && e.getY() < height) {
            pagePressed = true;
            result = true;
        }
        if (e.getX() > GUI.getWindowWidth() - height - nameWidth - 16 && e.getX() < GUI.getWindowWidth() && e.getY() > 0 && e.getY() < height) {
            accountPressed = true;
            result = true;
        }
        return result;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        if ((e.getX() > 0 && e.getX() < height && e.getY() > 0 && e.getY() < height && pagePressed) &&
        !(e.getX() > 0 && e.getX() < dropdownWidth && e.getY() > height && e.getY() < height + pageTotalHeight)) {
            pageSelected = !pageSelected;
        } else {
            pageSelected = false;
        }
        for (GUIComponent c : pageComponents) {
            if (c instanceof GButton) {
                ((GButton) c).setActive(pageSelected);
            }
        }

        if ((e.getX() > GUI.getWindowWidth() - height - nameWidth - 16 && e.getX() < GUI.getWindowWidth() && e.getY() > 0 && e.getY() < height && accountPressed) &&
                !(e.getX() > GUI.getWindowWidth() - dropdownWidth && e.getX() < GUI.getWindowWidth() && e.getY() > height && e.getY() < height + accountTotalHeight)) {
            accountSelected = !accountSelected;
        } else {
            accountSelected = false;
        }

        for (GUIComponent c : accountComponents) {
            if (c instanceof GButton) {
                ((GButton) c).setActive(accountSelected);
            }
        }

        pagePressed = false;
        accountPressed = false;

        return pageSelected || accountSelected;
    }

    @Override
    public ArrayList<ArrayList<GUIComponent>> getComponents() {
        ArrayList<ArrayList<GUIComponent>> list = new ArrayList<>();
        list.add(pageComponents);
        list.add(accountComponents);
        return list;
    }
}
