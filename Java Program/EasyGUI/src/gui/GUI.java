package gui;

import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Robert on 4/11/17.
 * <p>
 * This is the main class that does all the rendering.
 */
public final class GUI extends JFrame implements MouseWheelListener, MouseListener, KeyListener {

    /**
     * The window itself.
     */
    public static final GUI window = new GUI();

    /**
     * All of the pages to use.
     */
    private static final ArrayList<GUIPage> pages = new ArrayList<>();

    /**
     * All of the current loaded GUIComponents.
     */
    private static final ArrayList<GUIComponent> components = new ArrayList<>();

    /**
     * All of the GUIComponents that use the MouseListener.
     */
    private static final ArrayList<GMouseListener> mouseComponents = new ArrayList<>();

    /**
     * All of the GUIComponents that use the KeyListener.
     */
    private static final ArrayList<GKeyListener> keyComponents = new ArrayList<>();

    /**
     * All of the GUIComponents that have an animation.
     */
    private static final ArrayList<GAnimation> animatedComponents = new ArrayList<>();

    /**
     * The max width of the window's contents before it starts adding padding.
     */
    private static final int maxWidth = 720;

    /**
     * The padding added to the sides of all components.
     */
    private static final int sidePadding = 24;

    /**
     * The horizontal offset of components. Used in the slide-over animation.
     */
    static int horizontalOffset = 0;

    /**
     * The vertical offset controlled by scrolling.
     */
    private static int scrollOffset;

    /**
     * The drawing panel that everything is drawn to.
     */
    private static DrawPanel panel;

    /**
     * The currently loaded page.
     */
    private static GUIPage currentPage;

    /**
     * The height of the currently drawn page.
     */
    private static int pageHeight;

    /**
     * Changes how fast you can scroll the page.
     */
    private static final double scrollSpeedMultiplier = 4.0;

    /**
     * Whether to show the menu bar or not.
     */
    private static boolean showMenu = false;

    /**
     *
     */
    private static ArrayList<GPopUp> popupQueue = new ArrayList<>();

    /**
     * The popup menu.
     */
    private static GPopUp popup;

    /**
     * Starts the GUI with some basic settings.
     *
     * @author Robert
     */
    private GUI() {
        super();
        panel = new DrawPanel();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setFocusTraversalKeysEnabled(false);
        setSize(new Dimension(1280, 720));
        setMinimumSize(new Dimension(320, 640));
        setVisible(true);
        setTitle("Hello World!");
        setLocationRelativeTo(null);
        add(panel, BorderLayout.CENTER);
        panel.addMouseWheelListener(this);
        panel.addMouseListener(this);
        addKeyListener(this);
        panel.setLayout(null);
        panel.repaint();

        Timer clock = new Timer(Style.frameRate, e -> {
            for (GAnimation c : animatedComponents) {
                c.updateAnimations();
            }
            if (popup != null) {
                popup.updateAnimations();
            }
            panel.repaint();
        });

        clock.start();
    }

    /**
     * Shows a popup menu. This method also temporarily disables JTextBoxes because they mess
     * with mouseListeners.
     *
     * @param p The Popup to show.
     * @author Robert
     */
    public static void showPopUp(GPopUp p) {
        if (popup == null) {
            popup = p;
            for (GUIComponent c : components) {
                if (c instanceof JPanel) {
                    panel.remove((JPanel) c);
                    ((JPanel) c).revalidate();
                }
            }
            for (GUIComponent c : popup.getComponents().get(0)) {
                if (c instanceof JPanel) {
                    panel.add((JPanel) c);
                    ((JPanel) c).revalidate();
                }
            }
        } else {
            popupQueue.add(p);
        }
    }

    /**
     * This method enables text boxes after a popup is destroyed.
     * @author Robert
     */
    static void enableTextBoxes() {
        for (GUIComponent c : components) {
            if (c instanceof JPanel) {
                panel.add((JPanel) c);
                ((JPanel) c).revalidate();
            }
        }
        for (GUIComponent c : popup.getComponents().get(0)) {
            if (c instanceof JPanel) {
                panel.remove((JPanel) c);
                ((JPanel) c).revalidate();
            }
        }
    }

    /**
     * Removes the popup from memory.
     * @author Robert
     */
    static void destroyPopUp() {
        enableTextBoxes();
        popup = null;
        if (popupQueue.size() != 0) {
            showPopUp(popupQueue.get(0));
            popupQueue.remove(0);
        }
    }

    /**
     * Gets the current popup.
     *
     * @return The popup.
     * @author Robert
     */
    public static GPopUp getPopUp() {
        return popup;
    }

    /**
     * Add a GUIPage to the list of pages.
     *
     * @param page The page to add.
     * @author Robert
     */
    public void addPage(final GUIPage page) {
        pages.add(page);
    }

    /**
     * Returned the list of currently loaded components.
     *
     * @return The ArrayList of components
     * @author Robert
     */
    ArrayList<GUIComponent> getItems() {
        return components;
    }

    /**
     * Gets the inner width of the window.
     *
     * @return The width of the panel
     * @author Robert
     */
    static int getWindowWidth() {
        return panel.getWidth();
    }

    /**
     * Gets the inner width of the window.
     *
     * @return The width of the panel
     * @author Robert
     */
    static int getWindowHeight() {
        return panel.getHeight();
    }

    /**
     * This method adds a GUIComponent to the current page.
     *
     * @param c The GUIComponent to add.
     * @author Robert
     */
    public void add(final GUIComponent c) {
        components.add(c);
        if (c instanceof JPanel && popup == null) {
            panel.add((JPanel) c);
            ((JPanel) c).revalidate();
        }
        panel.setVisible(true);
    }

    /**
     * Refreshes the contents of the current page.
     */
    public void refresh() {
        gotoPage(currentPage);
    }

    /**
     * Allows this page to show the menu bar.
     */
    public void showMenu() {
        showMenu = true;
        add(GUIPage.menu);
    }

    /**
     * If you don't have access to the particular page object you can use
     * the pages's name to find the correct page to go to. A little slow but works.
     *
     * @param page The NAME of the page to go to.
     * @author Robert
     */
    public void gotoPage(final String page) {
        for (GUIPage p : pages) {
            if (p.getName().equals(page)) {
                gotoPage(p);
                break;
            }
        }
    }

    /**
     * Remove all objects from the page and call the new Page object's build method
     * to reassemble the page.
     *
     * @param page The page to build.
     * @author Robert
     */
    public void gotoPage(final GUIPage page) {
        showMenu = false;
        scrollOffset = 0;
        panel.removeAll();
        for (GUIComponent c : components) {
            if (c instanceof JPanel) {
                panel.remove((JPanel) c);
                ((JPanel) c).revalidate();
            }
        }
        components.clear();
        mouseComponents.clear();
        keyComponents.clear();
        animatedComponents.clear();
        page.refresh();
        page.build();
        panel.revalidate();
        currentPage = page;
        constructLists(components);
    }

    /**
     * This loops through all of the GUIComponents and
     * through any sub lists like GDivider and GMenuBar.
     * It gets all of the GUIComponents that use listeners and adds them to their own lists.
     *
     * @param list The GUI components to loop through.
     * @author Robert
     */
    private void constructLists(ArrayList<GUIComponent> list) {
        for (GUIComponent c : list) {
            if (c instanceof GMouseListener) {
                mouseComponents.add((GMouseListener) c);
            }
            if (c instanceof GKeyListener) {
                keyComponents.add((GKeyListener) c);
            }
            if (c instanceof GSubList) {
                for (ArrayList<GUIComponent> lists : ((GSubList) c).getComponents()) {
                    constructLists(lists);
                }
            }
            if (c instanceof GAnimation) {
                animatedComponents.add((GAnimation) c);
            }
        }
    }

    /**
     * Gets the title of the currently loaded page.
     *
     * @return The title of the page.
     * @author Robert
     */
    static String getPageTitle() {
        return currentPage.getName();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (popup == null) {
            if (pageHeight > panel.getHeight()) {
                scrollOffset -= e.getUnitsToScroll() * scrollSpeedMultiplier;
                if (scrollOffset > 0) scrollOffset = 0;
                if (scrollOffset < -pageHeight + panel.getHeight()) scrollOffset = -pageHeight + panel.getHeight();
            } else if (scrollOffset != 0) {
                scrollOffset = 0;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (popup != null) {
            popup.mousePressed(e);
        } else {
            for (GMouseListener c : mouseComponents) {
                if (c.mousePressed(e)) break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (popup != null) {
            popup.mouseReleased(e);
        } else {
            for (GMouseListener c : mouseComponents) {
                if (c.mouseReleased(e)) break;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (popup != null) {
            popup.keyTyped(e);
        } else {
            for (GKeyListener c : keyComponents) {
                c.keyTyped(e);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * This class handles all of the drawing.
     *
     * @author Robert
     */
    private class DrawPanel extends JPanel {

        /**
         * @author Robert
         */
        private DrawPanel() {
            this.setOpaque(true);
        }

        @Override
        public void paintComponent(Graphics theGraphics) {
            super.paintComponent(theGraphics);

            Graphics2D g = (Graphics2D) theGraphics;
            setDoubleBuffered(true);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int x = horizontalOffset + ((getWidth() > maxWidth + sidePadding) ? (getWidth() - maxWidth) / 2 : sidePadding / 2);
            int y = scrollOffset;

            // Loop through the list of currently loaded components and call their draw methods.
            for (int i = 0; i < components.size(); i++) { //DO NOT CHANGE THIS TO A FOR EACH LOOP!!
                GUIComponent c = components.get(i);
                if (!(c instanceof GMenuBar)) {
                    g.setColor(Color.black);
                    int width = (getWidth() > maxWidth + sidePadding) ? maxWidth : getWidth() - sidePadding;
                    y += c.draw(g, x, y, width);
                }
            }
            if (showMenu && popup != null) {
                g = (Graphics2D) theGraphics;
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                GUIPage.menu.draw(theGraphics, 0, 0, 0);
            }
            if (popup != null) {
                popup.draw(theGraphics, (getWidth() > (maxWidth / 1.5) + sidePadding) ? (int)(maxWidth / 1.5) : getWidth() - sidePadding);
            }
            pageHeight = y - scrollOffset;
        }

        @Override
        public void paint(Graphics theGraphics) {
            super.paint(theGraphics);
            if (showMenu && popup == null) {
                Graphics2D g = (Graphics2D) theGraphics;
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                GUIPage.menu.draw(theGraphics, 0, 0, 0);
            }
        }
    }
}
