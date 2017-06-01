package gui;

import control.General;
import user.UserManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Robert on 4/14/17.
 * <p>
 * This class is by far the messiest class out of all of them and
 * this handles ALL of the logic and stuff for the menu bar.
 */
public class GMenuBar implements GUIComponent, GMouseListener, GSubList, GAnimation {

    /**
     * The height of the menu bar.
     */
    private int height;

    /**
     * Whether the left button has been properly pressed.
     */
    private boolean pageSelected = false;

    /**
     * Whether the right button has been pressed.
     */
    private boolean accountPressed = false;

    /**
     * Whether the left button has been pressed.
     */
    private boolean pagePressed = false;

    /**
     * Whether the right button has been properly pressed.
     */
    private boolean accountSelected = false;

    /**
     * The width of the slide over menus.
     */
    private int dropdownWidth;

    /**
     * The width, in pixels, of the username.
     */
    private int nameWidth;

    /**
     * The total height of the left side bar.
     */
    private int pageTotalHeight;

    /**
     * The total height of the right side bar.
     */
    private int accountTotalHeight;

    /**
     * The GUIComponents in the left menu.
     */
    private ArrayList<GUIComponent> pageComponents = new ArrayList<>();

    /**
     * The GUIComponents in the right menu.
     */
    private ArrayList<GUIComponent> accountComponents = new ArrayList<>();

    /**
     * The user icon in the right corner.
     */
    private BufferedImage icon;

    /**
     * Create a menu bar with some height.
     *
     * @param height The height.
     * @author Robert
     */
    GMenuBar(final int height) {
        this.height = height;

        try {
            icon = ImageIO.read(new File("img/userIcon1.png"));
        } catch (Exception ex) {
            icon = null;
        }
    }

    /**
     * Adds a component to the left menu bar.
     *
     * @param c The component to add.
     * @author Robert
     */
    void addPage(GUIComponent c) {
        if (c instanceof GButton) {
            ((GButton) c).setActive(false);
        }
        pageComponents.add(c);
    }

    /**
     * Adds a component to the right menu bar.
     *
     * @param c The component to add.
     * @author Robert
     */
    void addAccount(GUIComponent c) {
        if (c instanceof GButton) {
            ((GButton) c).setActive(false);
        }
        accountComponents.add(c);
    }

    @Override
    public int draw(Graphics g, int x, int y, int width) {

        //Save some variables.
        dropdownWidth = Math.min((int) Math.round(GUI.getWindowWidth() * 0.8), 512);
        Graphics2D g2d = ((Graphics2D) g);

        //Draw the main rectangle.
        g.setColor(Style.menuColor);
        g.fillRect(0, 0, GUI.getWindowWidth(), height);

        //Draw the little rectangle at the bottom.
        g.setColor(Style.menuColor.darker());
        g.fillRect(0, height - 2, GUI.getWindowWidth(), 2);

        //Draw drop shadow using g2d.
        GradientPaint shadow = new GradientPaint(0, 0, Color.BLACK, 0, 55, new Color(0, 0, 0, 0));
        g2d.setPaint(shadow);
        g2d.fillRect(0, height, GUI.getWindowWidth(), 50);

        //Reset g2d.
        g2d = ((Graphics2D) g);

        //Draw the page bar.
        g.setColor(Style.menuSideBarColor);

        int tabPadding = 8;
        g.fillRect(GUI.horizontalOffset - dropdownWidth - tabPadding, height, dropdownWidth, GUI.window.getHeight());

        //Draw the right sidebar.
        int y2 = height;
        g.setColor(Style.menuSideBarColor);
        if (pageSelected || pagePressed) {
            g.fillRect(0, 0, height, height);
        }
        for (GUIComponent c : pageComponents) {
            y2 += c.draw(g, GUI.horizontalOffset - dropdownWidth - tabPadding, y2, dropdownWidth);
        }

        //Draw the bars icon.
        g.setColor(Style.menuTextColor);
        for (int i = 0; i < 3; i++) {
            g.fillRect(height / 8, (int) (height / 7.0 * (((i + 1) * 2) - 1)),
                    (height / 8) * 6, height / 7);
        }
        pageTotalHeight = y2 - height;

        //Draw the left sidebar.
        int newX = GUI.getWindowWidth() - dropdownWidth;
        int y3 = height;
        g.setColor(Style.menuSideBarColor);
        g.fillRect(newX + dropdownWidth + GUI.horizontalOffset + tabPadding, height, dropdownWidth, GUI.window.getHeight());
        g.setColor(Style.menuSideBarColor);
        if (accountSelected || accountPressed) {
            g.fillRect(GUI.getWindowWidth() - height - nameWidth - 16, 0, height + nameWidth + 16, height);
        }
        for (GUIComponent c : accountComponents) {
            y3 += c.draw(g, newX + dropdownWidth + GUI.horizontalOffset + tabPadding, y3, dropdownWidth);
        }
        g.setColor(Style.menuTextColor);
        accountTotalHeight = y3 - height;

        //Draw the circle in the corner.
        g2d.drawImage(icon, (int) (GUI.getWindowWidth() - height * 0.875), height / 4 / 2, (int) (height * 0.75), (int) (height * 0.75), null);

        //Draw user's name.
        g.setFont(new Font("Helvetica", Font.PLAIN, 26));
        String text = UserManager.getLoadedUser().getName();
        int length = g.getFontMetrics().stringWidth(text);
        nameWidth = length - 4;
        g.drawString(text, GUI.getWindowWidth() - height - length - 4, height - 10);

        //Draw the page title.
        String roomName = GUI.getPageTitle();
        while (g.getFontMetrics().stringWidth(roomName) > GUI.getWindowWidth() - height - height - length - 32) {
            roomName = roomName.substring(0, roomName.length() - 1);
        }
        g.drawString(roomName, height + 4, height - 10);

        return 0;
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        boolean result = false;
        if (General.clickedInside(0, 0, height, height, e)) {
            pagePressed = true;
            result = true;
        }
        if (General.clickedInside(GUI.getWindowWidth() - height - nameWidth - 16, 0,
                GUI.getWindowWidth(), height, e)) {
            accountPressed = true;
            result = true;
        }

        return result;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        pageSelected = General.clickedInside(0, 0, height, height, e) && pagePressed
                && !General.clickedInside(0, height, dropdownWidth, pageTotalHeight, e) && !pageSelected;

        for (GUIComponent c : pageComponents) {
            if (c instanceof GButton) {
                ((GButton) c).setActive(pageSelected);
            }
        }

        accountSelected = (General.clickedInside(GUI.getWindowWidth() - height - nameWidth - 16, 0, GUI.getWindowWidth(), height, e)
                && accountPressed) && !General.clickedInside(GUI.getWindowWidth() - dropdownWidth, height, dropdownWidth, accountTotalHeight, e)
                && !accountSelected;

        for (GUIComponent c : accountComponents) {
            if (c instanceof GButton) {
                ((GButton) c).setActive(accountSelected);
            }
        }

        pagePressed = false;
        accountPressed = false;

        return false;
    }

    @Override
    public ArrayList<ArrayList<GUIComponent>> getComponents() {
        ArrayList<ArrayList<GUIComponent>> list = new ArrayList<>();
        list.add(pageComponents);
        list.add(accountComponents);
        return list;
    }

    @Override
    public void updateAnimations() {
        int goalPosition = 0;
        if (pageSelected) {
            goalPosition = dropdownWidth;
        } else if (accountSelected) {
            goalPosition = -dropdownWidth;
        }
        GUI.horizontalOffset += Style.exponentialTweenRound(GUI.horizontalOffset, goalPosition, Style.sidebarSlideSpeed);
    }
}
